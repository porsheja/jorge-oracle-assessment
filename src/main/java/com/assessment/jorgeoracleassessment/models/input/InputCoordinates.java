package com.assessment.jorgeoracleassessment.models.input;

/**
 * Representation of each location coordinates returned by OpenAQ's 
 * https://api.openaq.org/v2/locations API. Only relevant fields are 
 * declared.
 * 
 * @author Jorge Gonzalez
 * @see https://api.openaq.org/v2/locations
 */
public record InputCoordinates(String latitude, String longitude) {}
