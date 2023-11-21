package com.assessment.jorgeoracleassessment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.assessment.jorgeoracleassessment.models.output.OutputResponse;
import com.assessment.jorgeoracleassessment.service.AQLocationService;

/**
 * Controller.
 * 
 * @author Jorge Gonzalez
 */
@RestController
public class AQController {
    @Autowired
    private AQLocationService service;

    /**
     * Method mapped to the "/measurements" path when it has "parameter", 
     * "countryCode" as query parameters. It calls the AQLocationService 
     * service to retrieve meditions of the air quality given an AQ parameter
     * and a country in a specific structure.
     *
     * @param parameter Air quality parameter
     * @param countryCode ISO 3166-1 country code.
     * @return OutputResponse Object which represents the service's answer for the fronend (it's later transformed to JSON).
     */
    @GetMapping(value = "/measurements", params = {"parameter", "countryCode"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public OutputResponse getMeasurementsByAQParameterAndCountryCode(@RequestParam("parameter") String parameter, @RequestParam("countryCode") String countryCode) {
        return service.getMeasurementsByCountry(parameter, countryCode);
    }

    /**
     * Method mapped to the "/measurements" path when it has "latitude", 
     * "longitude", "radius" as query parameters. It calls the AQLocationService 
     * service to retrieve meditions of the air quality given an AQ parameter,
     * coordinates and a radius for the coordinates in a specific structure.
     *
     * @param parameter Air quality parameter
     * @param latitude decimal-degree latitude.
     * @param longitude decimal-degree longitude.
     * @param radius Radius of the previously setted coordinates in meters.
     * @return OutputResponse Object which represents the service's answer for the fronend (it's later transformed to JSON).
     */
    @GetMapping(value = "/measurements", params = {"parameter", "latitude", "longitude", "radius"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public OutputResponse getMeasurementsByAQParameterCoordinatesAndRadius(@RequestParam("parameter") String parameter, @RequestParam("latitude") String latitude, @RequestParam("longitude") String longitude, @RequestParam("radius") int radius) {
        return service.getMeasurementsByCoordinatesAndRadius(parameter, latitude, longitude, radius);
    }
}
