package com.tsystem.logisticsbe.controller;

import com.tsystem.logisticsbe.controller.api.ITruckController;
import com.tsystem.logisticsbe.dto.TruckDTO;
import com.tsystem.logisticsbe.entity.Truck;
import com.tsystem.logisticsbe.mapper.TruckMapper;
import com.tsystem.logisticsbe.service.api.ITruckService;
import com.tsystem.logisticsbe.service.TruckService;
import com.tsystem.logisticsbe.util.validateion.TruckValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class TruckController implements ITruckController {

    private final ITruckService truckService;
    private final TruckMapper truckMapper;

    @Autowired
    public TruckController(TruckService truckService, TruckMapper truckMapper) {
        this.truckService = truckService;
        this.truckMapper = truckMapper;
    }

    public Long create(@RequestBody TruckDTO truckDTO) {
        TruckValidation.verifyData(truckDTO);
        Truck truck = truckMapper.mapToEntity(truckDTO);
        return truckService.create(truck);
    }

    public List<TruckDTO> getAll() {
        return truckService.getAll();
    }

    public TruckDTO getById(@PathVariable("id") Long id) {
        return truckService.getByID(id);
    }

    public Long update(@PathVariable("id") Long id, @RequestBody TruckDTO truckDTO) {
        TruckValidation.verifyData(truckDTO);
        Truck truck = truckMapper.mapToEntity(truckDTO);
        return truckService.update(id, truck);
    }

    public Long delete(@PathVariable("id") Long id) {
        return truckService.delete(id);
    }

    @Override
    public List<TruckDTO> getAvailableTrucks() {
        return truckService.getAvailableTrucks();
    }
}
