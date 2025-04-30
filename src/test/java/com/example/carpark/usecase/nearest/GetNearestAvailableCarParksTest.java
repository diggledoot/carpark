/* (C)2025 */
package com.example.carpark.usecase.nearest;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import com.example.carpark.data.availability.CarParkAvailabilityRepository;
import com.example.carpark.data.info.CarParkInfoRepository;
import com.example.carpark.exception.CarParkException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
class GetNearestAvailableCarParksTest {
	@InjectMocks
	private GetNearestAvailableCarParks getNearestAvailableCarParks;

	@Mock
	private CarParkAvailabilityRepository carParkAvailabilityRepository;

	@Mock
	private CarParkInfoRepository carParkInfoRepository;

	private static final Double MOCK_LATITUDE = 1.37326;
	private static final Double MOCK_LONGITUDE = 103.897;

	@Test
	void testInvalidInputs() {
		// Arrange
		Integer mockPage = 1;
		Integer mockPerPage = 10;

		// Act
		Executable executable = () -> getNearestAvailableCarParks.execute(null, MOCK_LONGITUDE, mockPage, mockPerPage);

		// Assert
		CarParkException exception = Assertions.assertThrows(CarParkException.class, executable);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
	}

	@Test
	void testPaginationCalculation() {
		// Arrange
		Integer mockPage = 3;
		Integer mockPerPage = 5;

		// Act
		getNearestAvailableCarParks.execute(MOCK_LATITUDE, MOCK_LONGITUDE, mockPage, mockPerPage);

		// Assert
		ArgumentCaptor<Integer> pageCaptor = ArgumentCaptor.forClass(Integer.class);
		ArgumentCaptor<Integer> perPageCaptor = ArgumentCaptor.forClass(Integer.class);

		verify(carParkInfoRepository).nearestCarPark(eq(MOCK_LATITUDE), eq(MOCK_LONGITUDE), pageCaptor.capture(),
				perPageCaptor.capture());

		Assertions.assertEquals(10, pageCaptor.getValue());
	}
}
