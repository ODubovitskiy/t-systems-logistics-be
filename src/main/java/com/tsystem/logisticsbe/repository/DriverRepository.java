package com.tsystem.logisticsbe.repository;


import com.tsystem.logisticsbe.entity.Driver;
import com.tsystem.logisticsbe.entity.Truck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface DriverRepository extends JpaRepository<Driver, Long> {

    List<Driver> findByIsDeletedNull();

    // TODO: 17.10.2021 Probably  the start point should be considered
    @Query("SELECT d FROM  Driver d " +
            "WHERE d.status=com.tsystem.logisticsbe.entity.domain.DriverStatus.REST " +
            "AND d.isDeleted is null  " +
            "GROUP BY d.id " +
            "HAVING sum( d.hoursWorked + :travelTime ) <= 176")
    Optional<List<Driver>> getDriversForOrder(Integer travelTime);

    List<Driver> getAllByTruckId(Long id);
}
