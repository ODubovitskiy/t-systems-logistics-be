package com.tsystem.logisticsbe.service;

import com.tsystem.logisticsbe.entity.Manager;
import com.tsystem.logisticsbe.exception.ApiException;
import com.tsystem.logisticsbe.repository.ManagerRepository;
import com.tsystem.logisticsbe.service.api.IManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ManagerService implements IManagerService {

    private final ManagerRepository managerRepository;

    @Autowired
    public ManagerService(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    @Override
    public Manager findManagerByAppUserId(Long id) {
        return managerRepository.findManagerByAppUserId(id).orElseThrow(
                () -> new ApiException(HttpStatus.NOT_FOUND, "Manager doesn't exist"));
    }
}
