package com.tsystem.logisticsbe.service.api;

import com.tsystem.logisticsbe.entity.WayPoint;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IWayPointService {

    @Transactional
    void create(List<WayPoint> route);

    boolean checkWayPoint(Long id);
}
