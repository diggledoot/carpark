/* (C)2025 */
package com.example.carpark.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.With;

@Data
@With
@AllArgsConstructor
@Builder
public class CarParkInfo {
	String carParkNo;
	String address;
	double longitude;
	double latitude;

	public CarParkInfo() {
	}
}
