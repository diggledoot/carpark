package com.example.carpark.data.info;

import com.example.carpark.domain.entity.CarParkInfo;

import java.util.List;

public interface CarParkInfoRepository {
    void ingest(CarParkInfo carParkInfo);
    List<CarParkInfo> nearestCarPark(double latitude, double longitude, int offset, int limit);
}
