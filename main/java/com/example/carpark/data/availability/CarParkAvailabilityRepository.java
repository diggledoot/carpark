package com.example.carpark.data.availability;

import com.example.carpark.domain.entity.CarParkAvailability;

import java.util.List;

public interface CarParkAvailabilityRepository {
    void upsert(CarParkAvailability carParkAvailability);

    List<CarParkAvailability> getAvailableCarParks(String carParkNumbers);
}
