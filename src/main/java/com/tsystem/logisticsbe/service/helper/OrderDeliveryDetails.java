package com.tsystem.logisticsbe.service.helper;

import com.tsystem.logisticsbe.dto.DriverDTO;
import com.tsystem.logisticsbe.dto.ShipmentDTO;
import com.tsystem.logisticsbe.dto.TruckDTO;
import com.tsystem.logisticsbe.dto.WayPointDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderDeliveryDetails {

    private final double TRUCK_SPEED = 100;
    private List<ShipmentDTO> shipments;
    private int travelTime;

    private int totalWeight;
    private List<DriverDTO> drivers;
    private List<TruckDTO> trucks;
    private List<WayPointDTO> wayPoints;

    public OrderDeliveryDetails(List<WayPointDTO> wayPoints, List<ShipmentDTO> shipments) {
        this.shipments = shipments;
        this.wayPoints = wayPoints;
    }

    public int getTotalWeight() {
        return shipments
                .stream()
                .mapToInt(ShipmentDTO::getWeight)
                .sum();
    }

    public int getTravelTime() {
        assert wayPoints != null;
        int routeLength = wayPoints.stream().
                mapToInt(waypoint -> waypoint.getCity().getDistance())
                .sum();

        return (int) Math.round(routeLength / TRUCK_SPEED);
    }

    public void setDrivers(List<DriverDTO> driversAvailable) {
        this.drivers = driversAvailable;
    }

    public void setTrucks(List<TruckDTO> trucksAvailable) {
        this.trucks = trucksAvailable;
    }

    public void setWayPoints(List<WayPointDTO> wayPoints) {
        this.wayPoints = wayPoints;
    }


}
