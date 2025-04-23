/* (C)2025 */
package com.example.carpark.usecase.data.ingestion;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.carpark.data.availability.CarParkAvailabilityRepository;
import com.example.carpark.data.availability.dto.AvailabilityResponse;
import com.example.carpark.domain.entity.CarParkAvailability;
import com.example.carpark.externalservice.sggov.SgGovService;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetCarParkAvailabilityTest {

	@InjectMocks
	private GetCarParkAvailability getCarParkAvailability;

	@Mock
	private SgGovService sgGovService;

	@Mock
	private CarParkAvailabilityRepository carParkAvailabilityRepository;

	@Test
	void testCarParkAvailabilityTransform() {
		// Arrange
		LocalDateTime mockLocalDatetime = LocalDateTime.now();
		AvailabilityResponse.CarParkInfo mockCarParkInfo = new AvailabilityResponse.CarParkInfo(100, 50);
		AvailabilityResponse.CarParkData mockCarParkData = new AvailabilityResponse.CarParkData(
				List.of(mockCarParkInfo), "HG30", mockLocalDatetime);
		AvailabilityResponse.Item mockItem = new AvailabilityResponse.Item(List.of(mockCarParkData));
		AvailabilityResponse mockAvailabilityResponse = new AvailabilityResponse(List.of(mockItem));
		when(sgGovService.getCarParkAvailability()).thenReturn(mockAvailabilityResponse);

		CarParkAvailability mockCarParkAvailability = CarParkAvailability.builder().carParkNumber("HG30")
				.updateDatetime(mockLocalDatetime).totalLots(100).lotsAvailable(50).build();

		// Act
		getCarParkAvailability.execute();

		// Assert
		ArgumentCaptor<CarParkAvailability> carParkAvailabilityArgumentCaptor = ArgumentCaptor
				.forClass(CarParkAvailability.class);
		verify(carParkAvailabilityRepository).upsert(carParkAvailabilityArgumentCaptor.capture());

		Assertions.assertEquals(mockCarParkAvailability.getCarParkNumber(),
				carParkAvailabilityArgumentCaptor.getValue().getCarParkNumber());
		Assertions.assertEquals(mockCarParkAvailability.getUpdateDatetime(),
				carParkAvailabilityArgumentCaptor.getValue().getUpdateDatetime());
		Assertions.assertEquals(mockCarParkAvailability.getTotalLots(),
				carParkAvailabilityArgumentCaptor.getValue().getTotalLots());
		Assertions.assertEquals(mockCarParkAvailability.getLotsAvailable(),
				carParkAvailabilityArgumentCaptor.getValue().getLotsAvailable());
	}
}
