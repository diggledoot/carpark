package com.example.carpark.domain.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class CarParkAvailability {
    String carParkNumber;
    LocalDateTime updateDatetime;
    int totalLots;
    int lotsAvailable;
}
