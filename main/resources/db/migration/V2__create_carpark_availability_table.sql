create table car_park_availability (
    carpark_number varchar(4) primary key,
    update_datetime timestamp without time zone,
    total_lots int,
    lots_available int
);
