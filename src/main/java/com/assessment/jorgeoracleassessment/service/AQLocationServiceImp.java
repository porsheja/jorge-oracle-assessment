package com.assessment.jorgeoracleassessment.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assessment.jorgeoracleassessment.models.input.InputLocation;
import com.assessment.jorgeoracleassessment.models.input.InputMeasure;
import com.assessment.jorgeoracleassessment.models.input.InputResponse;
import com.assessment.jorgeoracleassessment.models.output.OutputResponse;
import com.assessment.jorgeoracleassessment.models.output.OutputRow;
import com.assessment.jorgeoracleassessment.repository.OpenAQClient;

/**
 * Service which calls the src/main/java/com/assessment/jorgeoracleassessment/repository/OpenAQClient.java
 * REST API Client with the parameters given and processes the results to
 * generate a new data structure to be used by the frontend.
 * 
 * @author Jorge Gonzalez
 */
@Service
public class AQLocationServiceImp implements AQLocationService {
    @Autowired
    private OpenAQClient client;

    /**
     * Method that calls the REST API client to get a list of locations and
     * parameters by the country code and the air quality parameter. Then, 
     * the response is proccessed to filter the registries with the same
     * air quality parameter and converts it to a new data structure.
     * 
     * @param countryCode 
     * @param parameter
     * @return OutputResponse The desired output for the frontend.
     */
    @Override
    public OutputResponse getMeasurementsByCountry(String parameter, String countryCode) {
        // Calling REST API client to get the results.
        InputResponse response = client.getLocations(parameter, countryCode, null, null, -1);

        List<OutputRow> rows = new ArrayList<>();
        // Loop for all the locations to get its coordinates and the latest
        // value for the selected air quality parameter.
        for (InputLocation location : response.results()) {
            for (InputMeasure measure : location.parameters()) {
                // In theory, by passing the air quality parameter, OpenAQ 
                // should give us only one parameter for each location.
                // However, that doesn't happen. So we must filter by
                // ourselves.
                if (measure.parameter().equals(parameter)) {
                    rows.add(new OutputRow(location.coordinates().latitude(), location.coordinates().longitude(),
                            measure.lastValue()));

                    break;
                }
            }            
        }

        // Calling REST API client to get the display represetation of the
        // parameter.
        String displayParameter = "to do";

        return new OutputResponse(0, Double.MAX_VALUE, parameter, displayParameter, rows);
    }

    /**
     * Method that calls the REST API client to get a list of locations and
     * parameters by given latitude and longitude and a radius from that
     * coordinate. Then, the response is proccessed to filter the registries
     * with the same air  quality parameter and converts it to a new data 
     * structure.
     * 
     * @param parameter Air quality parameter
     * @param latitude decimal-degree latitude.
     * @param longitude decimal-degree longitude.
     * @param radius Radius of the previously setted coordinates in meters.
     * @return OutputResponse The desired output for the frontend.
     */
    @Override
    public OutputResponse getMeasurementsByCoordinatesAndRadius(String parameter, String latitude, String longitude,
            int radius) {
        // Calling REST API client to get the results.
        InputResponse response = client.getLocations(parameter, null, latitude, longitude, radius);

        List<OutputRow> rows = new ArrayList<>();
        // Loop for all the locations to get its coordinates and the latest
        // value for the selected air quality parameter.
        for (InputLocation location : response.results()) {
            for (InputMeasure measure : location.parameters()) {
                // In theory, by passing the air quality parameter, OpenAQ 
                // should give us only one parameter for each location.
                // However, that doesn't happen. So we must filter by
                // ourselves.
                if (measure.parameter().equals(parameter)) {
                    rows.add(new OutputRow(location.coordinates().latitude(), location.coordinates().longitude(),
                            measure.lastValue()));

                    break;
                }
            }            
        }

        // Calling REST API client to get the display represetation of the
        // parameter.
        String displayParameter = "to do";

        return new OutputResponse(0, Double.MAX_VALUE, parameter, displayParameter, rows);
    }
}