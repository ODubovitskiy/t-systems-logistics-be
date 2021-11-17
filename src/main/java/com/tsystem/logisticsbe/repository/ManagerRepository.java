package com.tsystem.logisticsbe.repository;

import com.tsystem.logisticsbe.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager, Long> {

    Optional<Manager> findManagerByAppUserId(Long aLong);
}
