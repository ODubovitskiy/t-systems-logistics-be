package com.tsystem.logisticsbe.service.api;

import com.tsystem.logisticsbe.entity.Manager;


public interface IManagerService {
    Manager findManagerByAppUserId(Long id);
}
