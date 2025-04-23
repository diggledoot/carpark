/* (C)2025 */
package com.example.carpark.usecase.data.ingestion;

import com.example.carpark.data.info.CarParkInfoRepository;
import com.example.carpark.domain.constant.CoordinateSystem;
import com.example.carpark.domain.entity.CarParkInfo;
import com.example.carpark.exception.CarParkException;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.locationtech.proj4j.*;

@RequiredArgsConstructor
public class IngestCarParkInfoCsv {
	private final CarParkInfoRepository carParkInfoRepository;
	private static final String CSV_FILE_PATH = "data/HDBCarparkInformation.csv";

	public void execute() {
		List<CarParkInfo> carParkInfos = readFromCsvFile();
		List<CarParkInfo> coordinateConvertedCarParkInfos = convertSVY21ToWGS84(carParkInfos);
		coordinateConvertedCarParkInfos.parallelStream().forEach(carParkInfoRepository::ingest);
	}

	private List<CarParkInfo> convertSVY21ToWGS84(List<CarParkInfo> carParkInfos) {
		// Create coordinate reference systems
		CRSFactory crsFactory = new CRSFactory();
		CoordinateReferenceSystem WGS84 = crsFactory.createFromName(CoordinateSystem.WGS84.getEpsg());
		CoordinateReferenceSystem SVY21 = crsFactory.createFromName(CoordinateSystem.SVY21.getEpsg());

		// Create coordinate transform
		CoordinateTransformFactory coordinateTransformFactory = new CoordinateTransformFactory();
		CoordinateTransform svy21ToWgs84 = coordinateTransformFactory.createTransform(SVY21, WGS84);

		return carParkInfos.parallelStream().map(carParkInfo -> {
			ProjCoordinate result = new ProjCoordinate();
			svy21ToWgs84.transform(new ProjCoordinate(carParkInfo.getLongitude(), carParkInfo.getLatitude()), result);
			return carParkInfo.withLongitude(result.x).withLatitude(result.y);
		}).toList();
	}

	private List<CarParkInfo> readFromCsvFile() {
		try (Reader reader = Files.newBufferedReader(Path.of(CSV_FILE_PATH))) {
			try (CSVReader csvReader = new CSVReader(reader)) {
				return csvReader.readAll().stream().skip(1).map(this::mapToCarParkInfo).toList();
			} catch (IOException | CsvException e) {
				throw new CarParkException(e.getMessage(), e.getCause());
			}
		} catch (IOException e) {
			throw new CarParkException(e.getMessage(), e.getCause());
		}
	}

	private CarParkInfo mapToCarParkInfo(String[] fields) {
		CarParkInfo carParkInfo = new CarParkInfo();

		carParkInfo.setCarParkNo(fields[0]);
		carParkInfo.setAddress(fields[1]);
		carParkInfo.setLongitude(Double.parseDouble(fields[2]));
		carParkInfo.setLatitude(Double.parseDouble(fields[3]));

		return carParkInfo;
	}
}
