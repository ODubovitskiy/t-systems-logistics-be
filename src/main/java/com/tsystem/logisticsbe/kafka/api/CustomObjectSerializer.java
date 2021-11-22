package com.tsystem.logisticsbe.kafka.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsystem.logisticsbe.exception.ApiException;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.http.HttpStatus;

public interface CustomObjectSerializer<T> extends Serializer<T> {

    default byte[] serialize(String s, T t) {
        byte[] bytes = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            bytes = objectMapper.writeValueAsString(t).getBytes();
        } catch (Exception e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while serializing driver object");
        }
        return bytes;
    }
}
