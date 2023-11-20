package com.assessment.jorgeoracleassessment.service;

import com.assessment.jorgeoracleassessment.models.output.OutputResponse;

/**
 * Interface for the src/main/java/com/assessment/jorgeoracleassessment/service/AQLocationServiceImp.java
 * implementation.
 * 
 * @author Jorge Gonzalez
 */
public interface AQLocationService {

    /**
     * @param countryCode 
     * @param parameter
     * @return OutputResponse The desired output for the frontend.
     */
    public OutputResponse getMeasurementsByCountry(String parameter, String countryCode);

    /**
     * @param parameter Air quality parameter
     * @param latitude decimal-degree latitude.
     * @param longitude decimal-degree longitude.
     * @param radius Radius of the previously setted coordinates in meters.
     * @return OutputResponse The desired output for the frontend.
     */
    public OutputResponse getMeasurementsByCoordinatesAndRadius(String parameter, String latitude, String longitude, int radius);
}
