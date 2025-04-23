/* (C)2025 */
package com.example.carpark.domain.entity;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CarParkAvailability {
	String carParkNumber;
	LocalDateTime updateDatetime;
	int totalLots;
	int lotsAvailable;
}
