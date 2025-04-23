/* (C)2025 */
package com.example.carpark.controller;

import com.example.carpark.usecase.data.ingestion.GetCarParkAvailability;
import com.example.carpark.usecase.data.ingestion.IngestCarParkInfoCsv;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ingest")
@Tag(name = "Data Ingestion Controller")
public class DataIngestionController {
	private final IngestCarParkInfoCsv ingestCarParkInfoCsv;
	private final GetCarParkAvailability getCarParkAvailability;

	@GetMapping("/car-park-info")
	@Operation(summary = "Ingest car park info CSV", description = "Ingests car park information from a CSV file into the system.")
	public ResponseEntity<?> ingestCarParkInfo() {
		ingestCarParkInfoCsv.execute();
		return new ResponseEntity<>("Ok! Ingested CSV!", HttpStatus.OK);
	}

	@GetMapping("/car-park-availability")
	@Operation(summary = "Pull car park availability data", description = "Pull car park availability from SG Gov API")
	public ResponseEntity<?> pullCarParkAvailability() {
		getCarParkAvailability.execute();
		return new ResponseEntity<>("Ok! Pulled!", HttpStatus.OK);
	}
}
