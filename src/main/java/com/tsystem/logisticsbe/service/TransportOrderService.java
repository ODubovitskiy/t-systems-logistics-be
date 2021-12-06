package com.tsystem.logisticsbe.service;

import com.tsystem.logisticsbe.dto.DriverDTO;
import com.tsystem.logisticsbe.dto.PreOrderDTO;
import com.tsystem.logisticsbe.dto.TransportOrderDTO;
import com.tsystem.logisticsbe.dto.TruckDTO;
import com.tsystem.logisticsbe.entity.*;
import com.tsystem.logisticsbe.entity.domain.DriverStatus;
import com.tsystem.logisticsbe.entity.domain.LoadingType;
import com.tsystem.logisticsbe.entity.domain.ShipmentStatus;
import com.tsystem.logisticsbe.entity.domain.TransportOrderStatus;
import com.tsystem.logisticsbe.exception.ApiException;
import com.tsystem.logisticsbe.mapper.CityMapper;
import com.tsystem.logisticsbe.mapper.DriverMapper;
import com.tsystem.logisticsbe.mapper.TransportOrderMapper;
import com.tsystem.logisticsbe.mapper.WayPointMapper;
import com.tsystem.logisticsbe.repository.*;
import com.tsystem.logisticsbe.service.api.ITransportOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TransportOrderService implements ITransportOrderService {

    private final TransportOrderRepository transportOrderRepository;
    private final TransportOrderMapper transportOrderMapper;
    private final TruckService truckService;
    private final CityRepository cityRepository;
    private final DriverRepository driverRepository;
    private final CityMapper cityMapper;
    private final DriverMapper driverMapper;
    private final TruckRepository truckRepository;
    private final ShipmentRepository shipmentRepository;
    private final WayPointRepository wayPointRepository;
    private final WayPointMapper wayPointMapper;
    //    private final CustomKafkaProducer customKafkaProducer;

    @Override
    public List<TransportOrderDTO> getAll() {
        List<TransportOrderDTO> orderDTOList = transportOrderMapper.mapToDtoList(transportOrderRepository.findAll());
        for (TransportOrderDTO transportOrderDTO : orderDTOList) {
//            customKafkaProducer.send(transportOrderDTO);
        }
        return orderDTOList;
    }

    @Transactional
    @Override
    public TransportOrder create(TransportOrder order) {

        String orderNumber = "ORD-".concat(
                UUID.randomUUID()
                        .toString()
                        .substring(0, 5)
                        .replaceAll("-", ""));

        order.setNumber(orderNumber);
        order.setStatus(TransportOrderStatus.IN_PROGRESS);

        Truck truck = handleTruckForOrder(order);
        order.setTruck(truck);

        Set<Driver> drivers = handleDriversForOrder(order, truck);
        order.setDrivers(drivers);

        List<WayPoint> wayPoints = handleWaypointsForOrder(order);
        order.setWayPoints(wayPoints);

        TransportOrder transportOrder = transportOrderRepository.saveAndFlush(order);
        wayPoints.forEach(wayPoint -> wayPoint.setOrder(transportOrder));
        drivers.forEach(driver -> driver.setTransportOrder(transportOrder));

        List<TransportOrderDTO> orderDTOList = transportOrderMapper.mapToDtoList(transportOrderRepository.findAll());
        for (TransportOrderDTO transportOrderDTO : orderDTOList) {
//            customKafkaProducer.send(transportOrderDTO);
        }

        return transportOrder;
    }

    private List<WayPoint> handleWaypointsForOrder(TransportOrder order) {
        List<WayPoint> wayPoints = new ArrayList<>();
        for (WayPoint wayPoint : order.getWayPoints()) {
            City city = cityRepository.findById(wayPoint.getCity().getId())
                    .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, String.format("There is no city %s",
                            wayPoint.getCity().getCity())));
            wayPoint.setCity(city);
            Shipment shipment = shipmentRepository.findById(wayPoint.getShipment().getId())
                    .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, String.format("There is no shipment %s",
                            wayPoint.getShipment().getName())));
            shipment.setStatus(ShipmentStatus.IN_PROGRESS);
            wayPoint.setShipment(shipment);
            wayPoints.add(wayPoint);
        }
        return wayPoints;
    }

    private Set<Driver> handleDriversForOrder(TransportOrder order, Truck truck) {
        Set<Driver> drivers = new HashSet<>();
        if (order.getDrivers().size() > truck.getDriverShiftSize())
            throw new ApiException(HttpStatus.BAD_REQUEST, String.format("Quantity of drivers cannot be mo than %s",
                    truck.getDriverShiftSize()));
        int counter = 0;

        for (Driver dr : order.getDrivers()) {
            Optional<Driver> driverOptional = driverRepository.findById(dr.getId());
            if (!driverOptional.isPresent())
                throw new ApiException(HttpStatus.BAD_REQUEST, String.format("There is no driver %s", dr.getName()));
            Driver driver = driverOptional.get();
            if (!Objects.equals(truck.getCurrentCity(), driver.getCity()))
                throw new ApiException(HttpStatus.BAD_REQUEST, "Drivers and the truck are located in different cities");

            if (counter == 0)
                driver.setStatus(DriverStatus.DRIVING);
            else
                driver.setStatus(DriverStatus.ON_SHIFT);
            driver.setTruck(truck);
            drivers.add(driver);

            counter++;
        }
        return drivers;
    }

    private Truck handleTruckForOrder(TransportOrder order) {
        Truck truck = truckRepository.findById(order.getTruck().getId())
                .orElseThrow(()
                        -> new ApiException(HttpStatus.NOT_FOUND, String.format("There is no truck %s", order.getTruck().getModel())));
        truck.setAvailable(false);
        return truck;
    }

    /**
     * Returns orderDeliveryDetailsDTO containing drivers and trucks available to deliver shipments through stated waypoints.
     *
     * @param preOrderDTO
     * @param wayPoints
     * @return OrderDeliveryDetailsDTO
     */
    @Override
    public PreOrderDTO getPreOrder(PreOrderDTO preOrderDTO, List<WayPoint> wayPoints) {

        // TODO: 29.10.2021 Optimize route.
        List<City> route = new ArrayList<>();
        List<City> citiesToStart = new ArrayList<>();
        Integer routeLength = 0;
        Integer totalWeight = 0;

        for (WayPoint wayPoint : wayPoints) {
            Long cityId = wayPoint.getCity().getId();
            City city = cityRepository.findById(cityId)
                    .orElseThrow(()
                            -> new ApiException(HttpStatus.NOT_FOUND, String.format("There is no city with id = %s", cityId)));
            route.add(city);
            // TODO: 29.10.2021 Suppose we can start from any possible "loading-city" by now.
            if (wayPoint.getType() == LoadingType.LOADING)
                citiesToStart.add(city);
            routeLength += city.getDistance();
            totalWeight += wayPoint.getShipment().getWeight() / 2;
        }

        Set<TruckDTO> trucksForOrder = truckService.getTrucksForOrder(citiesToStart, totalWeight);
        Set<DriverDTO> driversForOrder = new HashSet<>();

        for (TruckDTO truckDTO : trucksForOrder) {
            Integer travelTime = (int) (routeLength / truckDTO.getAverageSpeed());
            City city = cityMapper.mapToEntity(truckDTO.getCityDTO());
            HashSet<Driver> drivers = driverRepository.getDriversForOrder(travelTime, city);
            driversForOrder.addAll(driverMapper.mapToDtoSet(drivers));
        }

        if (driversForOrder.isEmpty())
            throw new ApiException(HttpStatus.BAD_REQUEST, "There are no drivers available to deliver your shipments");
        preOrderDTO.setTrucks(trucksForOrder);
        preOrderDTO.setDrivers(driversForOrder);

        return preOrderDTO;
    }

    @Override
    public TransportOrderDTO getById(Long id) {
        TransportOrder transportOrder = transportOrderRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, String.format("There is no order with id = %s", id)));
        return transportOrderMapper.mapToDTO(transportOrder);
    }

    @Override
    @Transactional
    public TransportOrderDTO update(TransportOrder transportOrder, Long id) {
        TransportOrder orderToUpdate = transportOrderRepository
                .findById(id)
                .orElseThrow(()
                        -> new ApiException(HttpStatus.NOT_FOUND, String.format("There is no order with id = %s", id)));
        transportOrderMapper.updateEntity(transportOrder, orderToUpdate);

        List<WayPoint> wayPoints = new ArrayList<>(orderToUpdate.getWayPoints());
        for (WayPoint item : wayPoints) {
            WayPoint wayPoint = wayPointRepository.findById(item.getId())
                    .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "There is no such waypoint on the route"));
            wayPointMapper.updateEntity(item, wayPoint);
            wayPoint.setOrder(orderToUpdate);
            wayPointRepository.save(wayPoint);
        }

        if (isCompleted(orderToUpdate)) {
            orderToUpdate.setStatus(TransportOrderStatus.DONE);
            Truck truck = orderToUpdate.getTruck();
            truck.setAvailable(true);
            truckRepository.save(truck);
            Set<Driver> drivers = new HashSet<>(orderToUpdate.getDrivers());
            for (Driver driver : drivers) {
                driver.setStatus(DriverStatus.REST);
                driverRepository.save(driver);
            }
        } else {
            driverRepository.saveAll(orderToUpdate.getDrivers());
        }

        transportOrderRepository.saveAndFlush(orderToUpdate);
        return transportOrderMapper.mapToDTO(orderToUpdate);
    }

    private boolean isCompleted(TransportOrder orderToUpdate) {
        boolean isCompleted = true;
        for (WayPoint wayPoint : orderToUpdate.getWayPoints()) {
            if (!Objects.equals(wayPoint.getShipment().getStatus(), ShipmentStatus.DELIVERED)) {
                isCompleted = false;
                break;
            }
        }
        return isCompleted;
    }
}
