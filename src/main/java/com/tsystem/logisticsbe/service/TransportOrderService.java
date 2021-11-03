package com.tsystem.logisticsbe.service;

import com.tsystem.logisticsbe.dto.DriverDTO;
import com.tsystem.logisticsbe.dto.PreOrderDTO;
import com.tsystem.logisticsbe.dto.TransportOrderDTO;
import com.tsystem.logisticsbe.dto.TruckDTO;
import com.tsystem.logisticsbe.entity.*;
import com.tsystem.logisticsbe.entity.domain.DriverStatus;
import com.tsystem.logisticsbe.entity.domain.LoadingType;
import com.tsystem.logisticsbe.entity.domain.ShipmentStatus;
import com.tsystem.logisticsbe.exception.ApiException;
import com.tsystem.logisticsbe.mapper.CityMapper;
import com.tsystem.logisticsbe.mapper.DriverMapper;
import com.tsystem.logisticsbe.mapper.TransportOrderMapper;
import com.tsystem.logisticsbe.repository.*;
import com.tsystem.logisticsbe.service.api.ITransportOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TransportOrderService implements ITransportOrderService {

    private final TransportOrderRepository transportOrderRepository;
    private final TransportOrderMapper transportOrderMapper;
    private final TruckService truckService;
    private final CityRepository cityRepository;
    private final DriverRepository driverRepository;
    private final CityMapper cityMapper;
    private final DriverMapper driverMapper;
    private final TruckRepository truckRepository;
    private final WayPointRepository wayPointRepository;

    @Autowired
    public TransportOrderService(TransportOrderRepository transportOrderRepository,
                                 TransportOrderMapper transportOrderMapper, TruckService truckService,
                                 CityRepository cityRepository, DriverRepository driverRepository,
                                 CityMapper cityMapper, DriverMapper driverMapper, TruckRepository truckRepository, WayPointRepository wayPointRepository) {
        this.transportOrderRepository = transportOrderRepository;
        this.transportOrderMapper = transportOrderMapper;
        this.truckService = truckService;
        this.cityRepository = cityRepository;
        this.driverRepository = driverRepository;
        this.cityMapper = cityMapper;
        this.driverMapper = driverMapper;
        this.truckRepository = truckRepository;
        this.wayPointRepository = wayPointRepository;
    }

    @Override
    public List<TransportOrderDTO> getAll() {
        return transportOrderMapper.mapToDtoList(transportOrderRepository.findAll());
    }

    @Override
    public TransportOrder create(TransportOrder order, List<WayPoint> route) {
        //Todo Transactional method does a lot of things.






        //Save Order only after all drivers and trucks are checked
        TransportOrder savedOrder = transportOrderRepository.save(order);
        route.forEach(item -> {
            item.setOrder(savedOrder);
        });

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
            Optional<City> cityOptional = cityRepository.findById(cityId);
            if (!cityOptional.isPresent())
                throw new ApiException(HttpStatus.NOT_FOUND, String.format("There is no city with id = %s", cityId));
            City city = cityOptional.get();
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
            Integer travelTime = (int) (routeLength / truckDTO.getAVERAGE_SPEED());
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
}
