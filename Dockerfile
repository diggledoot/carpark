FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/*.jar /app/app.jar

COPY data/HDBCarparkInformation.csv /app/data/HDBCarparkInformation.csv

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
