package com.assessment.jorgeoracleassessment.repository;

import java.security.InvalidParameterException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.assessment.jorgeoracleassessment.models.input.InputParameters;
import com.assessment.jorgeoracleassessment.models.input.InputResponse;

/**
 * Class that defines all the method which will consume the OpenAQ REST API.
 * 
 * @see https://docs.openaq.org/reference
 * @author Jorge Gonzalez
 */
@Repository
public class OpenAQClientImpl implements OpenAQClient {
    @Value("${page.maxSize}")
    private int maxSize;

    /**
     * Method which calls the OpenAQ REST API to get the locations given an
     * air quality parameter and the country code, or as set of coordinates
     * and a radius.
     * 
     * @see https://docs.openaq.org/reference/locations_get_v2_locations_get
     * @param parameter   Air quality parameter
     * @param countryCode ISO 3166-1 country code.
     * @param latitude    decimal-degree latitude.
     * @param longitude   decimal-degree longitude.
     * @param radius      Radius of the previously setted coordinates in meters.
     * @param page        Result page.
     * @return InputResponse which is a representantion of the API response
     *         with the fields we require only.
     */
    @Override
    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 500))
    public InputResponse getLocations(String parameter, String countryCode, String latitude, String longitude,
            int radius, int page) {
        String url = null;
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("api.openaq.org")
                .path("/v2/locations");

        if (parameter != null && countryCode != null) {
            url = uriBuilder.query("limit={maxSize}&page={page}&parameter={parameter}&country={countryCode}")
                    .buildAndExpand(maxSize, page, parameter, countryCode)
                    .toUriString();
        } else if (parameter != null && latitude != null && longitude != null) {
            url = uriBuilder.query("limit={maxSize}&page={page}&parameter={parameter}&coordinates={latitude},{longitude}&radius={radius}")
                    .buildAndExpand(maxSize, page, parameter, latitude, longitude, radius)
                    .toUriString();
        } else {
            throw new InvalidParameterException("Invalid parameters supplied");
        }

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForObject(url, InputResponse.class);
    }

    /**
     * Method that retrieves all the available air quality parameters and
     * its details. This list is cached.
     * 
     * @see https://docs.openaq.org/reference/parameters_get_v2_parameters_get
     * @return List of parameters.
     */
    @Override
    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 500))
    @Cacheable
    public InputParameters getParametersList() {
        String url = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("api.openaq.org")
                .path("/v2/parameters")
                .build().toUriString();

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForObject(url, InputParameters.class);
    }
}
