package com.example.carpark.usecase.nearest;

import com.example.carpark.data.availability.CarParkAvailabilityRepository;
import com.example.carpark.data.info.CarParkInfoRepository;
import com.example.carpark.domain.entity.CarParkAvailability;
import com.example.carpark.domain.entity.CarParkInfo;
import com.example.carpark.exception.CarParkException;
import com.example.carpark.usecase.nearest.dto.NearestCarPark;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class GetNearestAvailableCarParks {
    private final CarParkAvailabilityRepository carParkAvailabilityRepository;
    private final CarParkInfoRepository carParkInfoRepository;

    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_PER_PAGE = 10;

    public List<NearestCarPark> execute(Double latitude, Double longitude, Integer rawPage, Integer rawPerPage) {
        validateCoordinateInputs(latitude, longitude);

        // Handle pagination calculation
        int perPage = Optional.ofNullable(rawPerPage).orElse(DEFAULT_PER_PAGE);
        int page = (Optional.ofNullable(rawPage).orElse(DEFAULT_PAGE) - 1) * perPage;

        List<CarParkInfo> nearestCarParks = carParkInfoRepository.nearestCarPark(latitude, longitude, page, perPage);

        List<CarParkAvailability> availableCarParks =
                carParkAvailabilityRepository
                        .getAvailableCarParks(generateNearestCarParkNumbersString(nearestCarParks));

        List<CarParkInfo> availableNearestCarParks = getNearestAvailableCarParks(availableCarParks, nearestCarParks);

        //Build response
        return availableNearestCarParks.stream()
                .map(carParkInfo -> {
                    CarParkAvailability carParkAvailability =
                            getCarParkAvailabilityByNearestCarParkInfo(availableCarParks, carParkInfo);
                    return buildNearestCarPark(carParkAvailability, carParkInfo);
                })
                .toList();
    }

    private void validateCoordinateInputs(Double latitude, Double longitude) {
        if (latitude == null) {
            throw new CarParkException("latitude cannot be null", HttpStatus.BAD_REQUEST);

        }
        if (longitude == null) {
            throw new CarParkException("longitude cannot be null", HttpStatus.BAD_REQUEST);
        }
    }

    private List<CarParkInfo> getNearestAvailableCarParks(
            List<CarParkAvailability> availableCarParks,
            List<CarParkInfo> nearestCarParks
    ) {
        // Convert available car park numbers to set for quick diff check
        Set<String> availableCarParkNumbers = availableCarParks.stream()
                .map(CarParkAvailability::getCarParkNumber)
                .collect(Collectors.toSet());

        // Get available nearest car parks
        return nearestCarParks.stream()
                .filter(carPark -> availableCarParkNumbers.contains(carPark.getCarParkNo()))
                .toList();
    }

    private NearestCarPark buildNearestCarPark(CarParkAvailability carParkAvailability, CarParkInfo carParkInfo) {
        return NearestCarPark.builder()
                .address(carParkInfo.getAddress())
                .latitude(carParkInfo.getLatitude())
                .longitude(carParkInfo.getLongitude())
                .totalLots(carParkAvailability.getTotalLots())
                .lotsAvailable(carParkAvailability.getLotsAvailable())
                .build();
    }

    private CarParkAvailability getCarParkAvailabilityByNearestCarParkInfo(
            List<CarParkAvailability> availableCarParks,
            CarParkInfo carParkInfo
    ) {
        return availableCarParks.stream()
                .filter(carParkAvailability -> carParkAvailability.getCarParkNumber()
                        .contentEquals(carParkInfo.getCarParkNo()))
                .toList()
                .get(0);
    }

    private String generateNearestCarParkNumbersString(List<CarParkInfo> carParkInfos) {
        return carParkInfos.stream()
                .map(carParkInfo -> String.format("'%s'", carParkInfo.getCarParkNo()))
                .collect(Collectors.joining(","));
    }
}
