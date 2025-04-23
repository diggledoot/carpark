/* (C)2025 */
package com.example.carpark.domain.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EarthRadius {
	KILOMETER(6371);

	private final int radius;
}
