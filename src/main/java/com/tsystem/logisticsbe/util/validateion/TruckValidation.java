package com.tsystem.logisticsbe.util.validateion;

import com.tsystem.logisticsbe.dto.TruckDTO;
import com.tsystem.logisticsbe.exception.IllegalRequestDataException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TruckValidation {

    public void verifyData(TruckDTO truckDTO) {
        Integer loadCapacity = truckDTO.getLoadCapacity();
        if (loadCapacity < 0 | loadCapacity > 50)
            throw new IllegalRequestDataException("Load capacity must be from 0 to 50 tons");
        if (!truckDTO.getRegNumber().matches("^[A-Za-z]{2}[0-9]{5}$"))
            throw new IllegalRequestDataException("Registration number must meet format requirement, ex. 'AA12345'");
    }
}
