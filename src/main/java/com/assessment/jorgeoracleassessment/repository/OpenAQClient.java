package com.assessment.jorgeoracleassessment.repository;

import com.assessment.jorgeoracleassessment.models.input.InputParameters;
import com.assessment.jorgeoracleassessment.models.input.InputResponse;

/**
 * Interface for the src/main/java/com/assessment/jorgeoracleassessment/repository/OpenAQClientImpl.java
 * implementation.
 * 
 * @author Jorge Gonzalez
 */
public interface OpenAQClient {
    /**
     * @param parameter Air quality parameter
     * @param countryCode ISO 3166-1 country code.
     * @param latitude decimal-degree latitude.
     * @param longitude decimal-degree longitude.
     * @param radius Radius of the previously setted coordinates in meters.
     * @return InputResponse which is a representantion of the API response
     * with the fields we require only.
     */
    public InputResponse getLocations(String parameter, String countryCode, String latitude, String longitude, int radius);

    /**
     * @return List of parameters.
     */
    public InputParameters getParametersList();
}
