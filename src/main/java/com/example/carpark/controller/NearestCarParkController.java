/* (C)2025 */
package com.example.carpark.controller;

import com.example.carpark.usecase.nearest.GetNearestAvailableCarParks;
import com.example.carpark.usecase.nearest.dto.NearestCarPark;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carparks")
@RequiredArgsConstructor
@Tag(name = "Car Park Controller")
public class NearestCarParkController {
	private final GetNearestAvailableCarParks getNearestAvailableCarParks;

	@GetMapping("nearest")
	@Operation(summary = "Get nearest car park by latitude and longitude", description = "Get nearest car park by latitude and longitude")
	public ResponseEntity<?> nearest(@RequestParam(required = false) @Schema(defaultValue = "1.37326") Double latitude,
			@RequestParam(required = false) @Schema(defaultValue = "103.897") Double longitude,
			@RequestParam(required = false) @Schema(defaultValue = "1") Integer page,
			@RequestParam(required = false) @Schema(defaultValue = "3") Integer perPage) {
		List<NearestCarPark> result = getNearestAvailableCarParks.execute(latitude, longitude, page, perPage);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
