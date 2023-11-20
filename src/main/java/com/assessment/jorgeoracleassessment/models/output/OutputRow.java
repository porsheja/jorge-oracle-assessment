package com.assessment.jorgeoracleassessment.models.output;

/**
 * Representation of each row for the data set returned to frontend in 
 * order to build the heat map.
 * 
 * @author Jorge Gonzalez
 */
public record OutputRow(String latitude, String longitude, double value) {}
