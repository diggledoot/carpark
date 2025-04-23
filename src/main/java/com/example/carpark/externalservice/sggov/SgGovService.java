/* (C)2025 */
package com.example.carpark.externalservice.sggov;

import com.example.carpark.data.availability.dto.AvailabilityResponse;

public interface SgGovService {
	AvailabilityResponse getCarParkAvailability();
}
