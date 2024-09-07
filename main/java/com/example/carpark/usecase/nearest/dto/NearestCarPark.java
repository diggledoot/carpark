package com.example.carpark.usecase.nearest.dto;

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
    int totalLots;
    int lotsAvailable;
}
