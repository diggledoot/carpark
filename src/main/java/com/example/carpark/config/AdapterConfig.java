/* (C)2025 */
package com.example.carpark.config;

import com.example.carpark.data.availability.CarParkAvailabilityRepository;
import com.example.carpark.data.availability.CarParkAvailabilityRepositoryImpl;
import com.example.carpark.data.availability.mapper.CarParkAvailabilityMapper;
import com.example.carpark.data.info.CarParkInfoRepository;
import com.example.carpark.data.info.CarParkInfoRepositoryImpl;
import com.example.carpark.data.info.mapper.CarParkInfoRowMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class AdapterConfig {

	@Bean
	public CarParkInfoRepository carParkInfoRepository(JdbcTemplate jdbcTemplate) {
		return new CarParkInfoRepositoryImpl(jdbcTemplate, new CarParkInfoRowMapper());
	}

	@Bean
	public CarParkAvailabilityRepository carParkAvailabilityRepository(JdbcTemplate jdbcTemplate) {
		return new CarParkAvailabilityRepositoryImpl(jdbcTemplate, new CarParkAvailabilityMapper());
	}
}
