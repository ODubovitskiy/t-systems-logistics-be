package com.tsystem.logisticsbe.controller;

import com.tsystem.logisticsbe.dto.TruckDTO;
import com.tsystem.logisticsbe.service.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok(truckService.create(truckDTO));
    }

    @GetMapping(GET_ALL)
    public ResponseEntity<List<TruckDTO>> getAll() {
        return ResponseEntity.ok(truckService.getAll());
    }

    @GetMapping(GET_BY_ID)
    public ResponseEntity<TruckDTO> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(truckService.getByID(id));
    }

    @PutMapping(UPDATE)
    public ResponseEntity<TruckDTO> update(@PathVariable("id") Long id, @RequestBody TruckDTO truckDTO) {
        return ResponseEntity.ok(truckService.update(id, truckDTO));
    }

    @DeleteMapping(DELETE)
    public ResponseEntity<Long> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(truckService.delete(id));
    }
}
