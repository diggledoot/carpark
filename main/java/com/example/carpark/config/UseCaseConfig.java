package com.example.carpark.config;

import com.example.carpark.usecase.data.ingestion.GetCarParkAvailability;
import com.example.carpark.usecase.data.ingestion.IngestCarParkInfoCsv;
import com.example.carpark.data.availability.CarParkAvailabilityRepository;
import com.example.carpark.data.info.CarParkInfoRepository;
import com.example.carpark.externalservice.sggov.SgGovService;
import com.example.carpark.usecase.nearest.GetNearestAvailableCarParks;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public IngestCarParkInfoCsv ingestCarParkInfoCsv(
            CarParkInfoRepository carParkInfoRepository
    ) {
        return new IngestCarParkInfoCsv(carParkInfoRepository);
    }

    @Bean
    public GetCarParkAvailability getCarParkAvailability(
            CarParkAvailabilityRepository carParkAvailabilityRepository,
            SgGovService sgGovService
    ) {
        return new GetCarParkAvailability(carParkAvailabilityRepository, sgGovService);
    }

    @Bean
    public GetNearestAvailableCarParks getNearestAvailableCarParks(
            CarParkAvailabilityRepository carParkAvailabilityRepository,
            CarParkInfoRepository carParkInfoRepository
    ) {
        return new GetNearestAvailableCarParks(carParkAvailabilityRepository, carParkInfoRepository);
    }
}
