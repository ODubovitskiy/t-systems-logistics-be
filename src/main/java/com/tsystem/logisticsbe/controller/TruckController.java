package com.tsystem.logisticsbe.controller;

import com.tsystem.logisticsbe.dto.TruckDTO;
import com.tsystem.logisticsbe.exception.CityNotFoundException;
import com.tsystem.logisticsbe.exception.TruckNotFoundException;
import com.tsystem.logisticsbe.exception.WrongInputDataException;
import com.tsystem.logisticsbe.service.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/trucks")
public class TruckController {

    private static final String GET_ALL = "";
    private static final String GET_BY_ID = "/{id}";
    private static final String CREATE = "";
    private static final String UPDATE = "/{id}";
    private static final String DELETE = "/{id}";

    private final TruckService truckService;

    @Autowired
    public TruckController(TruckService truckService) {
        this.truckService = truckService;
    }

    @PostMapping(CREATE)
    public ResponseEntity<Long> create(@RequestBody TruckDTO truckDTO) {
        try {
            return ResponseEntity.ok(truckService.create(truckDTO));
        } catch (CityNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (WrongInputDataException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @GetMapping(GET_ALL)
    public ResponseEntity<List<TruckDTO>> getAll() {
        return ResponseEntity.ok(truckService.getAll());
    }

    @GetMapping(GET_BY_ID)
    public ResponseEntity<TruckDTO> getById(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(truckService.getByID(id));
        } catch (TruckNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @PutMapping(UPDATE)
    public ResponseEntity<TruckDTO> update(@PathVariable("id") Long id, @RequestBody TruckDTO truckDTO) {
        try {
            return ResponseEntity.ok(truckService.update(id, truckDTO));
        } catch (WrongInputDataException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @DeleteMapping(DELETE)
    public ResponseEntity<Long> delete(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(truckService.delete(id));
        } catch (TruckNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }
}
