package com.tsystem.logisticsbe.repository;

import com.tsystem.logisticsbe.entity.City;
import com.tsystem.logisticsbe.entity.Truck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TruckRepository extends JpaRepository<Truck, Long> {

    List<Truck> findByIsDeletedNull();

    @Query("SELECT t FROM Truck t WHERE t.isDeleted IS NULL " +
            "AND t.status = com.tsystem.logisticsbe.entity.domain.TruckStatus.WORKING AND t.isAvailable = TRUE")
    Optional<List<Truck>> getTrucksAvailable();

    @Query(nativeQuery = true,
            value = "SELECT * FROM truck t WHERE t.is_available= TRUE " +
                    "AND t.is_deleted is null  " +
                    "AND t.status = 'WORKING'  " +
                    "AND t.load_capacity >= :weight " +
                    "AND t.current_city_id IN (:citiesToStart)")
    Optional<Set<Truck>> getTrucksForOrder(@Param("citiesToStart") List<City> citiesToStart, @Param("weight")  Integer weight);


    Optional<Truck> findTruckByIdAndIsDeletedNull(Long id);
}
