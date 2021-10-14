package com.tsystem.logisticsbe.repository;

import com.tsystem.logisticsbe.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DriverRepository extends JpaRepository<Driver, Long> {
}
