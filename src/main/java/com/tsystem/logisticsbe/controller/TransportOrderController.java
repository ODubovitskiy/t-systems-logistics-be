package com.tsystem.logisticsbe.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tsystem.logisticsbe.controller.api.ITransportOrderController;
import com.tsystem.logisticsbe.dto.*;
import com.tsystem.logisticsbe.entity.City;
import com.tsystem.logisticsbe.exception.ApiException;
import com.tsystem.logisticsbe.mapper.CityMapper;
import com.tsystem.logisticsbe.mapper.OrderDeliveryDetailsMapper;
import com.tsystem.logisticsbe.mapper.ShipmentMapper;
import com.tsystem.logisticsbe.repository.CityRepository;
import com.tsystem.logisticsbe.service.TruckService;
import com.tsystem.logisticsbe.service.api.IDriverService;
import com.tsystem.logisticsbe.service.api.ITransportOrderService;
import com.tsystem.logisticsbe.service.api.ITruckService;
import com.tsystem.logisticsbe.service.helper.OrderDeliveryDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
public class TransportOrderController implements ITransportOrderController {

    private final ITransportOrderService transportOrderService;
    private final ITruckService truckService;
    private final IDriverService driverService;
    private final CityRepository cityRepository;
    private final CityMapper cityMapper;
    private final OrderDeliveryDetailsMapper orderDeliveryDetailsMapper;


    @Autowired
    public TransportOrderController(ITransportOrderService transportOrderService,
                                    TruckService truckService, IDriverService driverService, CityRepository cityRepository,
                                    CityMapper cityMapper, OrderDeliveryDetailsMapper orderDeliveryDetailsMapper) {
        this.transportOrderService = transportOrderService;
        this.truckService = truckService;
        this.driverService = driverService;
        this.cityRepository = cityRepository;
        this.cityMapper = cityMapper;
        this.orderDeliveryDetailsMapper = orderDeliveryDetailsMapper;
    }

    @Override
    public List<TransportOrderDTO> getAll() {
        return transportOrderService.getAll();
    }

    @Override
    public Long create(ObjectNode json) {
        return null;
    }

    @Override
    public TransportOrderDTO getById(Long id) {
        return null;
    }

    @Override
    public OrderDeliveryDetailsDTO getOrderDeliveryData(ObjectNode json) {

        List<ShipmentDTO> shipments = getShipments(json);
        List<WayPointDTO> waypoints = getWaypoints(json);

        for (ShipmentDTO shipment : shipments) {
            for (WayPointDTO waypoint : waypoints) {
                if (Objects.equals(shipment.getId(), waypoint.getShipment().getId()))
                    waypoint.setShipment(shipment);
            }
        }

        OrderDeliveryDetails orderDeliveryDetails = new OrderDeliveryDetails(waypoints, shipments);

        //Check trucks available based on the weight
        List<TruckDTO> trucks = truckService.getTrucksForOrder(orderDeliveryDetails.getTotalWeight());

        //Check drivers available
        List<DriverDTO> drivers = driverService.getDriversForOrder(orderDeliveryDetails.getTravelTime());

        List<DriverDTO> driversAvailable = new ArrayList<>();
        List<TruckDTO> trucksAvailable = new ArrayList<>();

        //Match Drivers & Trucks based on city
        for (TruckDTO truck : trucks) {
            for (DriverDTO driver : drivers) {
                if (Objects.equals(driver.getCityDTO(), truck.getCityDTO())) {
                    driversAvailable.add(driver);
                    trucksAvailable.add(truck);
                }
            }
        }

        orderDeliveryDetails.setDrivers(driversAvailable);
        orderDeliveryDetails.setTrucks(trucksAvailable);
        orderDeliveryDetails.setWayPoints(waypoints);

        return orderDeliveryDetailsMapper.mapToDTO(orderDeliveryDetails);
    }

    private List<WayPointDTO> getWaypoints(ObjectNode json) {

        List<WayPointDTO> wayPoints = new ArrayList<>();
        json.get("waypoints").forEach(el -> {
            try {
                WayPointDTO wayPointDTO = new ObjectMapper().treeToValue(el, WayPointDTO.class);
                Long id = wayPointDTO.getCity().getId();
                City city = cityRepository.getById(id);
                wayPointDTO.setCity(cityMapper.mapToDTO(city));
                wayPoints.add(wayPointDTO);
            } catch (JsonProcessingException e) {
                throw new ApiException(500, "The format provided is wrong. Please try again.");
            }
        });

        return wayPoints;
    }

    private List<ShipmentDTO> getShipments(ObjectNode json) {

        List<ShipmentDTO> shipments = new ArrayList<>();
        json.get("shipments").forEach(el -> {
            try {
                ShipmentDTO shipmentDTO = new ObjectMapper().treeToValue(el, ShipmentDTO.class);
                shipments.add(shipmentDTO);
            } catch (JsonProcessingException e) {
                throw new ApiException(500, "The format provided is wrong. Please try again.");
            }
        });
        return shipments;
    }
}
