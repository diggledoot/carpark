/* (C)2025 */
package com.example.carpark.data.availability;

import com.example.carpark.data.availability.mapper.CarParkAvailabilityMapper;
import com.example.carpark.domain.entity.CarParkAvailability;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;

@RequiredArgsConstructor
public class CarParkAvailabilityRepositoryImpl implements CarParkAvailabilityRepository {
	private final JdbcTemplate jdbcTemplate;
	private final CarParkAvailabilityMapper carParkAvailabilityMapper;

	@Override
	public void upsert(CarParkAvailability carParkAvailability) {
		String sql = """
				 INSERT INTO car_park_availability (carpark_number, update_datetime, total_lots, lots_available)
				 VALUES (?, ?, ?, ?)
				 ON CONFLICT (carpark_number)
				 DO UPDATE SET
				     update_datetime = EXCLUDED.update_datetime,
				     total_lots = EXCLUDED.total_lots,
				     lots_available = EXCLUDED.lots_available
				""";
		jdbcTemplate.update(sql, carParkAvailability.getCarParkNumber(), carParkAvailability.getUpdateDatetime(),
				carParkAvailability.getTotalLots(), carParkAvailability.getLotsAvailable());
	}

	@Override
	public List<CarParkAvailability> getAvailableCarParks(String carParkNumbers) {
		String sql = """
				SELECT carpark_number, update_datetime, total_lots, lots_available
				FROM car_park_availability
				WHERE carpark_number IN (%s)
				AND lots_available < total_lots
				AND lots_available > 0
				""".formatted(carParkNumbers);
		return jdbcTemplate.query(sql, carParkAvailabilityMapper);
	}
}
