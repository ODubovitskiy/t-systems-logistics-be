package com.tsystem.logisticsbe.repository;

import com.tsystem.logisticsbe.entity.TransportOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransportOrderRepository extends JpaRepository<TransportOrder, Long> {
}
