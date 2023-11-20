package com.assessment.jorgeoracleassessment.models.output;

import java.util.List;

/**
 * Representation each data set returned to frontend in order to build
 * the heat map.
 * 
 * @author Jorge Gonzalez
 */
public record OutputResponse(double min, double max, String parameter,
        String displayName, List<OutputRow> dataSet) {
}
