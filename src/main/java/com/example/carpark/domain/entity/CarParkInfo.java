/* (C)2025 */
package com.example.carpark.domain.entity;

import lombok.*;

@Data
@With
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarParkInfo {
	String carParkNo;
	String address;
	double longitude;
	double latitude;
}
