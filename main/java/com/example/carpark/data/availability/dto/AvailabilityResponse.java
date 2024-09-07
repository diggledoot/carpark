package com.example.carpark.data.availability.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

public record AvailabilityResponse(@JsonProperty("items") List<Item> items) {

    public record Item(
            @JsonProperty("carpark_data") List<CarParkData> carParkData) {
    }

    public record CarParkData(
            @JsonProperty("carpark_info") List<CarParkInfo> carParkInfo,
            @JsonProperty("carpark_number") String carParkNumber,
            @JsonProperty("update_datetime") LocalDateTime updateDatetime) {
    }

    public record CarParkInfo(
            @JsonProperty("total_lots") int totalLots,
            @JsonProperty("lots_available") int lotsAvailable) {
    }
}
