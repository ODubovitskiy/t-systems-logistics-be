package com.tsystem.logisticsbe.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDTO {

    @JsonProperty("time_stamp")
    String timeStamp;

    @JsonProperty("error_description")
    String errorDescription;

    String error;
}
