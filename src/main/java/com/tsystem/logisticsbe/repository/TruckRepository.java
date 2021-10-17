package com.tsystem.logisticsbe.repository;

import com.tsystem.logisticsbe.entity.Truck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TruckRepository extends JpaRepository<Truck, Long> {

    List<Truck> findByIsDeletedNull();

    @Query("SELECT t FROM Truck t WHERE t.isDeleted IS NULL " +
            "AND t.status = com.tsystem.logisticsbe.entity.domain.TruckStatus.WORKING AND t.isAvailable = TRUE")
    Optional<List<Truck>> getTrucksAvailable();

    @Query("SELECT t FROM Truck t WHERE t.isAvailable= TRUE " +
            "AND t.isDeleted is null  " +
            "AND t.status = 'WORKING'  " +
            "AND t.loadCapacity >= :weight")
    Optional<List<Truck>> getTrucksForOrder(int weight);
}
