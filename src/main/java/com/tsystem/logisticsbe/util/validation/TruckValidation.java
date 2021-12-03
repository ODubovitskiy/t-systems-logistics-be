package com.tsystem.logisticsbe.util.validation;

import com.tsystem.logisticsbe.dto.TruckDTO;
import com.tsystem.logisticsbe.exception.ApiException;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;

@UtilityClass
public class TruckValidation {

    public void verifyData(TruckDTO truckDTO) {
        Integer loadCapacity = truckDTO.getLoadCapacity();
        if (loadCapacity < 0 | loadCapacity > 50000)
            throw new ApiException(HttpStatus.BAD_REQUEST, "Load capacity must be from 0 to 50000 kg");
        if (!truckDTO.getRegNumber().matches("^[A-Za-z]{2}[0-9]{5}$"))
            throw new ApiException(HttpStatus.BAD_REQUEST, "Registration number must meet format requirement, ex. 'AA12345'");
        if (truckDTO.getDriverShiftSize() > 2)
            throw new ApiException(HttpStatus.BAD_REQUEST, "Driver shift size cannot be more than 2");
    }
}
