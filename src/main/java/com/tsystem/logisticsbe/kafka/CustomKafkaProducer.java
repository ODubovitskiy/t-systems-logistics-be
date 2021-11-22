package com.tsystem.logisticsbe.kafka;

import com.tsystem.logisticsbe.dto.DriverDTO;
import com.tsystem.logisticsbe.dto.TransportOrderDTO;
import com.tsystem.logisticsbe.dto.TruckDTO;
import com.tsystem.logisticsbe.kafka.api.ICustomKafkaProducer;
import com.tsystem.logisticsbe.kafka.serializer.DriverDTOSerializer;
import com.tsystem.logisticsbe.kafka.serializer.TruckDTOSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class CustomKafkaProducer implements ICustomKafkaProducer {

    private final Properties properties;

    public CustomKafkaProducer() {
        properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    }

    @Override
    public void send(DriverDTO driverDTO) {
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, DriverDTOSerializer.class.getName());
        KafkaProducer<String, DriverDTO> producer = new KafkaProducer<>(properties);
        producer.send(new ProducerRecord<>("driver-topic", String.valueOf(driverDTO.getId()), driverDTO));

        producer.flush();
        producer.close();
    }


    @Override
    public void send(TruckDTO truckDTO) {
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, TruckDTOSerializer.class.getName());
        KafkaProducer<String, TruckDTO> producer = new KafkaProducer<>(properties);
        producer.send(new ProducerRecord<>("truck-topic", String.valueOf(truckDTO.getId()), truckDTO));

        producer.flush();
        producer.close();
    }

    @Override
    public void send(TransportOrderDTO transportOrderDTO) {

    }
}
