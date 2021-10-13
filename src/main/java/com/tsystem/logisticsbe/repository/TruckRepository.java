package com.tsystem.logisticsbe.repository;

import com.tsystem.logisticsbe.entity.Truck;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TruckRepository extends JpaRepository<Truck, Long> {

    List<Truck> findAll();
    List<Truck> findByIsDeletedNull();
}
