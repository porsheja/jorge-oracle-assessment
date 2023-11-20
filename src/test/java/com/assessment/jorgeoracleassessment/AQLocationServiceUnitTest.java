package com.assessment.jorgeoracleassessment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.nullable;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.assessment.jorgeoracleassessment.models.input.InputCoordinates;
import com.assessment.jorgeoracleassessment.models.input.InputLocation;
import com.assessment.jorgeoracleassessment.models.input.InputMeasure;
import com.assessment.jorgeoracleassessment.models.input.InputMeta;
import com.assessment.jorgeoracleassessment.models.input.InputResponse;
import com.assessment.jorgeoracleassessment.models.output.OutputResponse;
import com.assessment.jorgeoracleassessment.repository.OpenAQClient;
import com.assessment.jorgeoracleassessment.service.AQLocationService;
import com.assessment.jorgeoracleassessment.service.AQLocationServiceImp;

/**
 * Unit tests for the src/main/java/com/assessment/jorgeoracleassessment/service/AQLocationServiceImp.java
 * service.
 * 
 * @author Jorge Gonzalez
 */
@SpringBootTest
@ContextConfiguration(classes = { AQLocationServiceImp.class })
@ExtendWith(SpringExtension.class)
public class AQLocationServiceUnitTest {
    @Autowired
    private AQLocationService service;

    @MockBean
    private OpenAQClient openAQClient;

    /**
     * Test when we set the air quality parameter and country code to the
     * AQLocationService's getMeasurementsByCountry method, then we get a 
     * valid OutputResponse structure.
     */
    @Test
    public void givenCountryAndAirQualityParameter_whenGettingTheMeasurements_thenRetrieveAnAppropiateResponse() {
        String test_parameter = "pm25",
                test_countryCode = "mx";

        // Mocked response of the REST API Client.
        InputResponse mockedResponse = new InputResponse(
                new InputMeta(1, 100, 1),
                List.of(new InputLocation(
                        List.of(new InputMeasure(Math.random() * 100, test_parameter)),
                        new InputCoordinates("109", "-31"))));

        // Mock of the call to the REST API Client.
        Mockito.when(openAQClient.getLocations(anyString(), anyString(), nullable(String.class), nullable(String.class), anyInt()))
                .thenReturn(mockedResponse);

        OutputResponse response = service.getMeasurementsByCountry(test_parameter, test_countryCode);

        // Assert that the response is not null.
        assertNotNull(response);
        // Assert thet our response data set size is equal to the counting
        // of locations the mocked response has.
        assertEquals(response.dataSet().size(), mockedResponse.results().size());
        // Assert thet the latitude of the first output response row is equal
        // to the latitude of the first location the mocked response has.
        assertEquals(response.dataSet().get(0).latitude(), mockedResponse.results().get(0).coordinates().latitude());
        // Assert thet the longitude of the first output response row is equal
        // to the longitude of the first location the mocked response has.
        assertEquals(response.dataSet().get(0).longitude(), mockedResponse.results().get(0).coordinates().longitude());
        // Assert thet the value of the first output response row is equal to
        // the latest value of the first parameter of the first location the
        // mocked response has.
        assertEquals(response.dataSet().get(0).value(),
                mockedResponse.results().get(0).parameters().get(0).lastValue());
    }

    /**
     * Test when we set the air quality parameter, a longitude, a latitude 
     * and a radius to the AQLocationService's getMeasurementsByCoordinatesAndRadius 
     * method, then we get a valid OutputResponse structure.
     */
    @Test
    public void givenLatitudeLongitudeRadiusAndAirQualityParameter_whenGettingTheMeasurements_thenRetrieveAnAppropiateResponse() {
        String test_parameter = "pm25",
            test_latitude = "109.1",
            test_longitude = "-30.9";
        int test_radius = (int) (Math.random() * 25e3);

        // Mocked response of the REST API Client.
        InputResponse mockedResponse = new InputResponse(
                new InputMeta(1, 100, 1),
                List.of(new InputLocation(
                        List.of(new InputMeasure(Math.random() * 100, test_parameter)),
                        new InputCoordinates("109", "-31"))));

        // Mock of the call to the REST API Client.
        Mockito.when(openAQClient.getLocations(anyString(), nullable(String.class), anyString(), anyString(), anyInt()))
                .thenReturn(mockedResponse);

        OutputResponse response = service.getMeasurementsByCoordinatesAndRadius(test_parameter, test_latitude, test_longitude, test_radius);

        // Assert that the response is not null.
        assertNotNull(response);
        // Assert thet our response data set size is equal to the counting
        // of locations the mocked response has.
        assertEquals(response.dataSet().size(), mockedResponse.results().size());
        // Assert thet the latitude of the first output response row is equal
        // to the latitude of the first location the mocked response has.
        assertEquals(response.dataSet().get(0).latitude(), mockedResponse.results().get(0).coordinates().latitude());
        // Assert thet the longitude of the first output response row is equal
        // to the longitude of the first location the mocked response has.
        assertEquals(response.dataSet().get(0).longitude(), mockedResponse.results().get(0).coordinates().longitude());
        // Assert thet the value of the first output response row is equal to
        // the latest value of the first parameter of the first location the
        // mocked response has.
        assertEquals(response.dataSet().get(0).value(),
                mockedResponse.results().get(0).parameters().get(0).lastValue());
    }
}