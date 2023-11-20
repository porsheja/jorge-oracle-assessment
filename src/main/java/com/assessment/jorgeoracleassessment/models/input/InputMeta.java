package com.assessment.jorgeoracleassessment.models.input;


/**
 * Representation of the response's metadata returned by OpenAQ's 
 * https://api.openaq.org/v2/locations API. Only relevant fields are 
 * declared.
 * 
 * @author Jorge Gonzalez
 * @see https://api.openaq.org/v2/locations
 */
public record InputMeta(int page, int limit, int found) {}
