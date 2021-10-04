package com.tsystem.logisticsbe.repository;

import com.tsystem.logisticsbe.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
}
