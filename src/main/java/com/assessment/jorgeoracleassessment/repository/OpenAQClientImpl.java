package com.assessment.jorgeoracleassessment.repository;

import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.assessment.jorgeoracleassessment.models.input.InputResponse;

/**
 * Class that defines all the method which will consume the OpenAQ REST API.
 * 
 * @see https://docs.openaq.org/reference
 * @author Jorge Gonzalez
 */
@Repository
public class OpenAQClientImpl implements OpenAQClient {
    /**
     * Method which calls the OpenAQ REST API to get the locations given an
     * air quality parameter and the country code, or as set of coordinates
     * and a radius.
     * 
     * @see https://docs.openaq.org/reference/locations_get_v2_locations_get
     * @param parameter Air quality parameter
     * @param countryCode ISO 3166-1 country code.
     * @param latitude decimal-degree latitude.
     * @param longitude decimal-degree longitude.
     * @param radius Radius of the previously setted coordinates in meters.
     * @return InputResponse which is a representantion of the API response
     * with the fields we require only.
     */
    @Override
    public InputResponse getLocations(String parameter, String countryCode, String latitude, String longitude,
            int radius) {
        String url = null;
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("api.openaq.org")
                .path("/v2/locations");

        if (countryCode != null) {
            url = uriBuilder.query("parameter={parameter}&country={countryCode}")
                    .buildAndExpand(parameter, countryCode)
                    .toUriString();
        } else {
            url = uriBuilder.query("parameter={parameter}&coordinates={latitude},{longitude}&radius={radius}")
                    .buildAndExpand(parameter, latitude, longitude, radius)
                    .toUriString();
        }

        RestTemplate restTemplate = new RestTemplate();
        
        return restTemplate.getForObject(url, InputResponse.class);
    }
}
