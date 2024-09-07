package com.example.carpark.usecase.nearest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@Getter
public class NearestCarPark {
    String address;
    double latitude;
    double longitude;
    @JsonProperty("total_lots")
    int totalLots;
    @JsonProperty("lots_available")
    int lotsAvailable;
}
