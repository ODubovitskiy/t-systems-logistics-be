package com.tsystem.logisticsbe.repository;

import com.tsystem.logisticsbe.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {

    @Query("select s FROM Shipment s where s.status=com.tsystem.logisticsbe.entity.domain.ShipmentStatus.PREPARED")
    List<Shipment> getShipmentsPrepared();
}
