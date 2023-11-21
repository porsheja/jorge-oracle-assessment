package com.assessment.jorgeoracleassessment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.assessment.jorgeoracleassessment.models.output.OutputResponse;
import com.assessment.jorgeoracleassessment.service.AQLocationService;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * Controller.
 * 
 * @author Jorge Gonzalez
 */
@RestController
@Validated
public class AQController {
    @Autowired
    private AQLocationService service;

    /**
     * Method mapped to the "/measurements" path when it has "parameter",
     * "countryCode" as query parameters. It calls the AQLocationService
     * service to retrieve meditions of the air quality given an AQ parameter
     * and a country in a specific structure.
     *
     * @param parameter   Air quality parameter
     * @param countryCode ISO 3166-1 country code.
     * @return OutputResponse Object which represents the service's answer for the
     *         fronend (it's later transformed to JSON).
     */
    @GetMapping(value = "/measurements", params = { "parameter",
            "countryCode" }, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public OutputResponse getMeasurementsByAQParameterAndCountryCode(
            @RequestParam("parameter") @NotBlank String parameter,
            @RequestParam("countryCode") String countryCode) {
        return service.getMeasurementsByCountry(parameter, countryCode);
    }

    /**
     * Method mapped to the "/measurements" path when it has "latitude",
     * "longitude", "radius" as query parameters. It calls the AQLocationService
     * service to retrieve meditions of the air quality given an AQ parameter,
     * coordinates and a radius for the coordinates in a specific structure.
     *
     * @param parameter Air quality parameter
     * @param latitude  decimal-degree latitude.
     * @param longitude decimal-degree longitude.
     * @param radius    Radius of the previously setted coordinates in meters.
     * @return OutputResponse Object which represents the service's answer for the
     *         fronend (it's later transformed to JSON).
     */
    @GetMapping(value = "/measurements", params = { "parameter", "latitude", "longitude",
            "radius" }, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public OutputResponse getMeasurementsByAQParameterCoordinatesAndRadius(
            @RequestParam("parameter") @NotBlank String parameter,
            @RequestParam("latitude") @NotBlank(message = "latitude must not be empty") @Pattern(message = "latitude format is invalid", regexp = "^-?\\d{1,2}\\.?\\d{0,8}") String latitude,
            @RequestParam("longitude") @NotBlank(message = "longitude must not be empty") @Pattern(message = "longitude format is invalid", regexp = "^-?1?\\d{1,2}\\.?\\d{0,8}") String longitude,
            @RequestParam("radius") @Min(value = 1, message = "radius must be bigger than 0") @Max(value = 25000, message = "radius must be smaller or equal than 2500") int radius) {
        return service.getMeasurementsByCoordinatesAndRadius(parameter, latitude, longitude, radius);
    }

    /**
     * Method which handles ConstraintViolationException to return its 
     * messages a a JSON list.
     * 
     * @param cvex Exception thrown if a validation is not fulfilled.
     * @return ResponseEntitty with the list of constraint violation exception 
     * messages (HTTP code 422).
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<String>> constraintViolationExceptionHandler(ConstraintViolationException cvex) {
        return new ResponseEntity<>(cvex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .toList(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    /**
     * Method which handles ValidationException to return its 
     * messages a a JSON list.
     * 
     * @param vex Exception thrown if a validation is not fulfilled.
     * @return ResponseEntitty with the validation exception message 
     * (HTTP code 422).
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> validationExceptionHandler(ValidationException vex) {
        return new ResponseEntity<>(vex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
