# Car Park Availability API

# To Run
1. Have Docker Desktop installed.
2. Run `docker compose compose.yaml up`
3. Go to `http://localhost:8080/swagger-ui/index.html`
4. Run car park info ingestion from CSV. [Link](http://localhost:8080/swagger-ui/index.html#/Data%20Ingestion%20Controller/ingestCarParkInfo) to swagger endpoint.
5. Run car park availability from provided endpoint. [Link](http://localhost:8080/swagger-ui/index.html#/Data%20Ingestion%20Controller/pullCarParkAvailability) to swagger endpoint.
6. To get nearest available car park. Go [here](http://localhost:8080/swagger-ui/index.html#/Car%20Park%20Controller/nearest).

# Limitations of the API
1. You have to be in Singapore.
2. Manual ingestion of the car park availability data.
3. The CSV file data will be outdated as new car parks are built or torn down.

# Design Decisions

---

## At Which Point To Ingest CSV Data?
Initially, I wanted to use Python to ingest the CSV data since the data is static.

However, I went with a pure Java approach to keep things simple.

For the matter of when to run it, I thought to run a small task at start up.

I didn't go through with it because:

1. The CSV file might fail to load and stop the service from loading.
2. It would waste time to run the CSV ingestion on each startup.

I chose to create an endpoint to ingest the CSV data manually.

---

## Coordinate Conversion
This was fun.

Initially, I went with an endpoint approach by sending the Eastings and Northings to the conversion endpoint on `onemap`.

When I tried to carry out concurrent calls, I got rate limited.

Then I added buffer, I started getting `BAD GATEWAY` responses.

Then I added the option to retry the request via the `spring-retry` dependency.

This worked.

However, due to the friction put in place to be polite to `onemap`, it took half an hour and more to carry out coordinate conversion for all of the car park info records.

I went with an offline library approach. I added `proj4j`, a coordinate transformation library to convert between coordinate systems. [Link](https://trac.osgeo.org/proj4j/)

The ingestion time went from half an hour and more to less than 2 minutes (it felt that way).


