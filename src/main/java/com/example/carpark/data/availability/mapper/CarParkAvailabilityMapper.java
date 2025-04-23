/* (C)2025 */
package com.example.carpark.data.availability.mapper;

import com.example.carpark.domain.entity.CarParkAvailability;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import lombok.NonNull;
import org.springframework.jdbc.core.RowMapper;

public class CarParkAvailabilityMapper implements RowMapper<CarParkAvailability> {
	@Override
	public CarParkAvailability mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
		return CarParkAvailability.builder().carParkNumber(rs.getString("carpark_number"))
				.updateDatetime(rs.getObject("update_datetime", LocalDateTime.class)).totalLots(rs.getInt("total_lots"))
				.lotsAvailable(rs.getInt("lots_available")).build();
	}
}
