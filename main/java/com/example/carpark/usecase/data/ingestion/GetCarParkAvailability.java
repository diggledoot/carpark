package com.example.carpark.usecase.data.ingestion;

import com.example.carpark.domain.entity.CarParkAvailability;
import com.example.carpark.data.availability.CarParkAvailabilityRepository;
import com.example.carpark.data.availability.dto.AvailabilityResponse;
import com.example.carpark.externalservice.sggov.SgGovService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetCarParkAvailability {
    private final CarParkAvailabilityRepository carParkAvailabilityRepository;
    private final SgGovService sgGovService;

    public void execute() {
        AvailabilityResponse response = sgGovService.getCarParkAvailability();
        List<CarParkAvailability> carParkAvailabilityList = response.items().parallelStream()
                .flatMap(item -> item.carParkData().stream())
                .map(this::mapToCarParkInfo)
                .toList();
        carParkAvailabilityList.parallelStream().forEach(carParkAvailabilityRepository::upsert);
    }

    private CarParkAvailability mapToCarParkInfo(AvailabilityResponse.CarParkData carParkData) {
        return CarParkAvailability.builder()
                .carParkNumber(carParkData.carParkNumber())
                .updateDatetime(carParkData.updateDatetime())
                .totalLots(carParkData.carParkInfo().get(0).totalLots())
                .lotsAvailable(carParkData.carParkInfo().get(0).lotsAvailable())
                .build();
    }
}
