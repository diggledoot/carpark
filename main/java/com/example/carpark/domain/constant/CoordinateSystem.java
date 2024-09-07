package com.example.carpark.domain.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CoordinateSystem {
    SVY21("epsg:3414"),
    WGS84("epsg:4326");

    private final String epsg;
}
