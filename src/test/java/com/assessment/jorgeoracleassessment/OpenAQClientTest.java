package com.assessment.jorgeoracleassessment;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.assessment.jorgeoracleassessment.models.input.InputParameters;
import com.assessment.jorgeoracleassessment.models.input.InputResponse;
import com.assessment.jorgeoracleassessment.repository.OpenAQClient;
import com.assessment.jorgeoracleassessment.repository.OpenAQClientImpl;
import com.assessment.jorgeoracleassessment.service.SpringBootTest;

/**
 * Integration tests for the src/main/java/com/assessment/jorgeoracleassessment/repository/OpenAQClientImpl.java
 * REST API client.
 * 
 * @author Jorge Gonzalez
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { OpenAQClientImpl.class })
public class OpenAQClientTest {
    @Autowired
    private OpenAQClient client;

    /**
     * Test that if we set the air quality and country code to the getLocations 
     * method of the REST API client, then we get a response.
     */
    @Test
    public void givenCountryAndAirQualityParameter_whenGettingTheMeasurements_thenRetrieveAResponse() {
        String test_parameter = "pm25";

        InputResponse response = client.getLocations(test_parameter, "MX", null, null, -1);

        assertNotNull(response);
        assertTrue(response.meta().found() >= 1);
        assertNotNull(response.results());
        assertTrue(response.results().size() >= 1);

        // In theory we should also test we got only one result in each location.
        /*assertNotNull(response.results().get(0).parameters());
        assertEquals(response.results().get(0).parameters().size(), 1);*/
        // and that result parameter should be equal to test_parameter.
        /*assertEquals(response.results().get(0).parameters().get(0).parameter(), test_parameter);*/
    }
    
    /**
     * Test that if we set the air quality, a latitude, a longitude and a 
     * radius to the getLocations method of the REST API client, then we get
     * a response.
     */
    @Test
    public void givenLatitudeLongitudeRadiusAndAirQualityParameter_whenGettingTheMeasurements_thenRetrieveAnAppropiateResponse() {
        String test_parameter = "pm25";

        InputResponse response = client.getLocations(test_parameter, null, "31.68668689", "-106.42717484", 10000);

        assertNotNull(response);
        assertTrue(response.meta().found() >= 1);
        assertNotNull(response.results().get(0).parameters().get(0));
        assertTrue(response.results().size() >= 1);

        // In theory we should also test we got only one result in each location.
        /*assertNotNull(response.results().get(0).parameters());
        assertEquals(response.results().get(0).parameters().size(), 1);*/
        // and that result parameter should be equal to test_parameter.
        /*assertEquals(response.results().get(0).parameters().get(0).parameter(), test_parameter);*/
    }

    /**
     * Test that if we set the air quality and country code to the getDisplayName 
     * method of the REST API client, then we get the display name.
     */
    @Test
    public void givenAirQualityParameter_whenGettingDisplayName_thenRetrieveAString() {
        String test_parameter = "pm25";

        InputParameters response = client.getParametersList();

        // Test that the response is not null.
        assertNotNull(response);
        // Test that the list contains test_parameter parameter.
        assertTrue(response.results().stream().anyMatch(item -> test_parameter.equals(item.name())));
    }
}
