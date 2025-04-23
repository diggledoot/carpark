/* (C)2025 */
package com.example.carpark.data.info.mapper;

import com.example.carpark.domain.entity.CarParkInfo;
import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.NonNull;
import org.springframework.jdbc.core.RowMapper;

public class CarParkInfoRowMapper implements RowMapper<CarParkInfo> {
	@Override
	public CarParkInfo mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
		return CarParkInfo.builder().carParkNo(rs.getString("car_park_no")).address(rs.getString("address"))
				.latitude(rs.getDouble("latitude")).longitude(rs.getDouble("longitude")).build();
	}
}
