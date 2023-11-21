package com.assessment.jorgeoracleassessment.models.input;

/**
 * Representation of each air quality parameter returned by
 * OpenAQ's https://api.openaq.org/v2/locations API. Only 
 * relevant fields are declared.
 * 
 * @author Jorge Gonzalez
 * @see https://api.openaq.org/v2/parameters
 */
public record InputParameter(String name, String displayName, String description, String preferredUnit) {}
