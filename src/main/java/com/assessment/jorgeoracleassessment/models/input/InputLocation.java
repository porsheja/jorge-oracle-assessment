package com.assessment.jorgeoracleassessment.models.input;

import java.util.List;

/**
 * Representation of each location returned by OpenAQ's 
 * https://api.openaq.org/v2/locations API. Only relevant fields are 
 * declared.
 * 
 * @author Jorge Gonzalez
 * @see https://api.openaq.org/v2/locations
 */
public record InputLocation(List<InputMeasure> parameters,
        InputCoordinates coordinates) {
}
