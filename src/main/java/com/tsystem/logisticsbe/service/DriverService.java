package com.tsystem.logisticsbe.service;

import com.tsystem.logisticsbe.dto.DriverDTO;
import com.tsystem.logisticsbe.entity.Driver;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverService implements IDriverService{
    @Override
    public String create(Driver driver) {
        return null;
    }

    @Override
    public List<DriverDTO> getAll() {
        return null;
    }

    @Override
    public DriverDTO getById(Long id) {
        return null;
    }

    @Override
    public Long update(Long id, Driver driver) {
        return null;
    }

    @Override
    public Long delete(Long id) {
        return null;
    }
}
