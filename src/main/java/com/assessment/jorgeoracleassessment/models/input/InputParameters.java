package com.assessment.jorgeoracleassessment.models.input;

import java.util.List;

/**
 * Representation of list of air quality parameter returned
 * by OpenAQ's https://api.openaq.org/v2/locations API. Only 
 * relevant fields are declared.
 * 
 * @author Jorge Gonzalez
 * @see https://api.openaq.org/v2/parameters
 */
public record InputParameters(List<InputParameter> results) {}
