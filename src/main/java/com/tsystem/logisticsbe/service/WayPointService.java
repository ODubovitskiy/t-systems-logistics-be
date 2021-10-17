package com.tsystem.logisticsbe.service;

import com.tsystem.logisticsbe.entity.WayPoint;
import com.tsystem.logisticsbe.repository.WayPointRepository;
import com.tsystem.logisticsbe.service.api.IWayPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WayPointService implements IWayPointService {

    private final WayPointRepository wayPointRepository;

    @Autowired
    public WayPointService(WayPointRepository wayPointRepository) {
        this.wayPointRepository = wayPointRepository;
    }

    @Override
    public void create(List<WayPoint> route) {
        wayPointRepository.saveAll(route);
    }

    @Override
    public boolean checkWayPoint(Long id) {
        return wayPointRepository.existsById(id);
    }
}
