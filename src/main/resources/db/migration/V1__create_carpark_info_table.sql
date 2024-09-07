create table car_park_info (
    car_park_no varchar(4) primary key,
    address text not null,
    longitude double precision not null,
    latitude double precision not null
);