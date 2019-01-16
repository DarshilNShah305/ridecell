
create table parking_system
(
	id BIGINT auto_increment PRIMARY KEY,
    lattitude DECIMAL(8,6),
    longitude DECIMAL(8,6),
    is_reserved boolean,
    vehicle_type DECIMAL(1),
    charges DECIMAL(9,3)
);

create table parking_reservation
(
	id BIGINT auto_increment PRIMARY KEY,
    parking_id BIGINT,
    vehicle_num VARCHAR(20),
    from_time datetime,
    to_time datetime,
    user_id BIGINT,
    amount DECIMAL(12,3),
FOREIGN KEY fk_parking_id (parking_id)
REFERENCES parking_system(id)
);


create table user
(
	user_id varchar(2),
    phone_no VARCHAR(12)
);
