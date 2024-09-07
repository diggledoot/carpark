package com.example.carpark.data.info;

import com.example.carpark.data.info.mapper.CarParkInfoRowMapper;
import com.example.carpark.domain.constant.EarthRadius;
import com.example.carpark.domain.entity.CarParkInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@RequiredArgsConstructor
public class CarParkInfoRepositoryImpl implements CarParkInfoRepository {
    private final JdbcTemplate jdbcTemplate;
    private final CarParkInfoRowMapper carParkInfoRowMapper;

    @Override
    public void ingest(CarParkInfo carParkInfo) {
        String sql = """
                INSERT INTO car_park_info (car_park_no, address, latitude, longitude)
                VALUES (?, ?, ?, ?)
                ON CONFLICT DO NOTHING
                """;
        jdbcTemplate.update(
                sql,
                carParkInfo.getCarParkNo(),
                carParkInfo.getAddress(),
                carParkInfo.getLatitude(),
                carParkInfo.getLongitude()
        );
    }

    @Override
    public List<CarParkInfo> nearestCarPark(double latitude, double longitude, int offset, int limit) {
        // Application of Haversine formula to calculate distance between two points on a sphere
        // https://en.wikipedia.org/wiki/Haversine_formula
        String sql = """
                SELECT car_park_no, address, latitude, longitude
                FROM car_park_info
                ORDER BY ? * 2 * ASIN(SQRT(
                        POWER(SIN(RADIANS(? - latitude) / 2), 2) +
                        COS(RADIANS(latitude)) * COS(RADIANS(?)) *
                        POWER(SIN(RADIANS(? - longitude) / 2), 2)
                    )) ASC
                LIMIT ?
                OFFSET ?
                """;
        return jdbcTemplate.query(
                sql,
                carParkInfoRowMapper,
                EarthRadius.KILOMETER.getRadius(),
                latitude,
                latitude,
                longitude,
                limit,
                offset
        );
    }
}
