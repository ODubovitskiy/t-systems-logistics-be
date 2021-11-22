package com.tsystem.logisticsbe.kafka.serializer;

import com.tsystem.logisticsbe.dto.DriverDTO;
import com.tsystem.logisticsbe.kafka.api.CustomObjectSerializer;

public class DriverDTOSerializer implements CustomObjectSerializer<DriverDTO> {

    public byte[] serialize(String s, DriverDTO driverDTO) {
        return CustomObjectSerializer.super.serialize(s, driverDTO);
    }
}
