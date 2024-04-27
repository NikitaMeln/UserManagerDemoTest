package com.test.example_23.demotest.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericErrorResponse {
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime timestamp;
    @NotNull
    private Integer status;
    @NotNull
    private String error = "Error string";
    @Nullable
    private List<String> errorMessages;
    @Nullable
    private Map<String, String> fieldErrors;
    @NotNull
    private String path = "Request path";
}
