package com.tsystem.logisticsbe.kafka.serializer;

import com.tsystem.logisticsbe.dto.TruckDTO;
import com.tsystem.logisticsbe.kafka.api.CustomObjectSerializer;

public class TruckDTOSerializer implements CustomObjectSerializer<TruckDTO> {

    public byte[] serialize(String s, TruckDTO truckDTO) {
        return CustomObjectSerializer.super.serialize(s, truckDTO);
    }
}
