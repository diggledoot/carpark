# Car Park Availability API

# To Run
1. Have Java 17 installed.
2. Have Maven installed.
3. Have Docker Desktop installed.
4. Download the project as a zip file.
5. Have `run.sh` and `cleanup.sh` be executable. e.g. `chmod +x ./run.sh ./cleanup.sh`
6. Execute `run.sh`.
7. Go to `http://localhost:8080/swagger-ui/index.html`
8. Run car park info ingestion from CSV. [Link](http://localhost:8080/swagger-ui/index.html#/Data%20Ingestion%20Controller/ingestCarParkInfo) to swagger endpoint.
9. Run car park availability from provided endpoint. [Link](http://localhost:8080/swagger-ui/index.html#/Data%20Ingestion%20Controller/pullCarParkAvailability) to swagger endpoint.
10. To get nearest available car park. Go [here](http://localhost:8080/swagger-ui/index.html#/Car%20Park%20Controller/nearest).
11. When finished, execute `cleanup.sh`.

# Limitations of the API
1. You have to be in Singapore for it to make sense.
2. Manual ingestion of the car park availability data.
3. The CSV file data will be outdated as new car parks are built or torn down.
4. For the API to work properly, you need to execute in the order of ingest csv -> pull car park availability data -> query nearest endpoint.

# Design Decisions

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

---

## Calculating Distances On A Sphere

With data ingestion out of the way, I went to build the endpoint for getting the nearest car park that is available.

I'll be honest here. I used GPT to generate the SQL formula for the geodistance calculation, Haversnine. Manual testing seems to show it working well.

Anyway, I ran into the issue of combining nearest car parks and the car park availability data.

I could have solved this by using JOIN queries for nearest car parks and car park availability.

But for the sake of maintenance, I have both queries separate.

Hence, the code for getting the nearest available car park can seem a little confusing.
