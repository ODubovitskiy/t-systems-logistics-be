package com.tsystem.logisticsbe.controller;

import com.tsystem.logisticsbe.controller.api.ICityController;
import com.tsystem.logisticsbe.dto.CityDTO;
import com.tsystem.logisticsbe.factory.CityFactory;
import com.tsystem.logisticsbe.service.ICityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CityController implements ICityController {

    private final ICityService cityService;
    private final CityFactory cityFactory;

    @Autowired
    public CityController(ICityService cityService, CityFactory cityFactory) {
        this.cityService = cityService;
        this.cityFactory = cityFactory;
    }

    @Override
    public List<CityDTO> getAll() {
        return cityFactory.createDefaultCityDTOList(cityService.getAll());
    }
}
