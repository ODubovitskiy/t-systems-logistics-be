package com.tsystem.logisticsbe.kafka.api;

import com.tsystem.logisticsbe.dto.DriverDTO;
import com.tsystem.logisticsbe.dto.TransportOrderDTO;
import com.tsystem.logisticsbe.dto.TruckDTO;

public interface ICustomKafkaProducer {

    void send(DriverDTO driverDTO);

    void send(TruckDTO truckDTO);

    void send(TransportOrderDTO transportOrderDTO);

}
