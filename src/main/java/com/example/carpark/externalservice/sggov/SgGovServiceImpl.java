/* (C)2025 */
package com.example.carpark.externalservice.sggov;

import com.example.carpark.data.availability.dto.AvailabilityResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestClient;

@RequiredArgsConstructor
public class SgGovServiceImpl implements SgGovService {
	private final RestClient restClient;
	private final String baseUrl;

	private static final String CAR_PARK_AVAILABILITY_ENDPOINT = "/v1/transport/carpark-availability";

	@Override
	public AvailabilityResponse getCarParkAvailability() {
		return restClient.get().uri(
				uriBuilder -> uriBuilder.scheme("https").host(baseUrl).path(CAR_PARK_AVAILABILITY_ENDPOINT).build())
				.retrieve().body(AvailabilityResponse.class);
	}
}
