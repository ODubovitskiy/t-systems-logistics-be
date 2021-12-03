package com.tsystem.logisticsbe.repository;

import com.tsystem.logisticsbe.entity.City;
import com.tsystem.logisticsbe.entity.Driver;
import com.tsystem.logisticsbe.entity.Truck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface DriverRepository extends JpaRepository<Driver, Long> {

    List<Driver> findByIsDeletedNull();

    // TODO: 17.10.2021 Probably the start point should be considered
    @Query("SELECT d FROM  Driver d " +
            "WHERE d.status=com.tsystem.logisticsbe.entity.domain.DriverStatus.REST " +
            "AND d.isDeleted is null  " +
            "AND d.city = :city  " +
            "GROUP BY d.id " +
            "HAVING sum( d.hoursWorked + :travelTime ) <= 176")
    HashSet<Driver> getDriversForOrder(Integer travelTime, City city);

    List<Driver> getAllByTruckId(Long id);

    Optional<Driver> getDriverByPersonalNumberAndIsDeletedNull(String number);

    Set<Driver> getDriversByTruck(Truck truck);

    Set<Driver> findDriversByCityId(Long cityId);

    Optional<Driver> findDriverByIdAndIsDeletedNull(Long id);

    Optional<Driver> getDriverByAppUserId(Long id);

}
