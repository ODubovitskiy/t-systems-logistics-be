package com.tsystem.logisticsbe.controller;

import com.tsystem.logisticsbe.dto.TruckDTO;
import com.tsystem.logisticsbe.service.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.tsystem.logisticsbe.util.validateion.TruckValidation.verifyData;

@RestController
@RequestMapping("api")
public class TruckController {

    private static final String GET_ALL = "/trucks";
    private static final String GET_BY_ID = "/trucks/{id}";
    private static final String CREATE = "/trucks";
    private static final String UPDATE = "/trucks/{id}";
    private static final String DELETE = "/trucks/{id}";

    private final TruckService truckService;

    @Autowired
    public TruckController(TruckService truckService) {
        this.truckService = truckService;
    }

    @PostMapping(CREATE)
    public Long create(@RequestBody TruckDTO truckDTO) {
        verifyData(truckDTO);
        return truckService.create(truckDTO);
    }

    @GetMapping(GET_ALL)
    public List<TruckDTO> getAll() {
        return truckService.getAll();
    }

    @GetMapping(GET_BY_ID)
    public ResponseEntity<TruckDTO> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(truckService.getByID(id));
    }

    @PutMapping(UPDATE)
    public TruckDTO update(@PathVariable("id") Long id, @RequestBody TruckDTO truckDTO) {
        verifyData(truckDTO);
        return truckService.update(id, truckDTO);
    }

    @DeleteMapping(DELETE)
    public Long delete(@PathVariable("id") Long id) {
        return truckService.delete(id);
    }
}
