USE versionedHoteldb;

CREATE TABLE IPM_HOTEL (
hotel_id varchar(128), 
hotel_name varchar(128), 
extended_description TEXT(65535),
PRIMARY KEY (hotel_id));

CREATE TABLE IPM_HOTEL_RATE (
hotel_id varchar(128),
season varchar(128),
description varchar(256),
source varchar(128),
PRIMARY KEY (hotel_id, season, description, source),
FOREIGN KEY (hotel_id) REFERENCES IPM_HOTEL(hotel_id));

CREATE TABLE HOTEL_IMAGE (
hotel_id varchar(128), 
title varchar(256),
id bigint ,
source varchar(128),
PRIMARY KEY (id, source),
FOREIGN KEY (hotel_id) REFERENCES IPM_HOTEL(hotel_id));

CREATE TABLE HOTEL_IMAGE_URL (
url varchar(1024),
image_id bigint,
FOREIGN KEY (image_id) REFERENCES HOTEL_IMAGE(id));

CREATE TABLE HOTEL_FEATURE_TYPE (
hotel_id varchar(128),
feature_id bigint,
feature_type varchar(128),
location varchar(128),
source varchar(128),
FOREIGN KEY (hotel_id) REFERENCES IPM_HOTEL(hotel_id));

CREATE TABLE HOTEL_POINT_OF_INTERSET_FEATURE_TYPE (
hotel_id varchar(128),
feature_id bigint,
feature_type varchar(128),
source varchar(128),
FOREIGN KEY (hotel_id) REFERENCES IPM_HOTEL(hotel_id));

CREATE TABLE HOTEL_ROOM (
hotel_id varchar(128),
name varchar(128),
description varchar(3000),
room_id bigint,
PRIMARY KEY (room_id),
FOREIGN KEY (hotel_id) REFERENCES IPM_HOTEL(hotel_id));

CREATE TABLE HOTEL_ROOM_FEATURE_TYPE (
feature_id bigint,
room_id bigint,
feature_type varchar(128),
source varchar(128),
FOREIGN KEY (room_id) REFERENCES HOTEL_ROOM(room_id));

CREATE TABLE DC_HOTEL_ID_MAPPING (
hotel_id varchar(128),
vendorHotelID varchar(20),
source varchar(128),
PRIMARY KEY (vendorHotelID),
FOREIGN KEY (hotel_id) REFERENCES IPM_HOTEL(hotel_id));

CREATE TABLE DC_HOTEL_CATEGORY_MAPPING (
vendorHotelID varchar(20),
vendorCategory varchar(20),
cwcCategory varchar(128),
vendorID varchar(4),
PRIMARY KEY (vendorHotelID),
FOREIGN KEY (vendorHotelID) REFERENCES DC_HOTEL_ID_MAPPING(vendorHotelID));

CREATE TABLE DC_HOTEL_RATE_MAPPING (
vendorHotelID varchar(20),
internalRateDisplay varchar(128),
externalRateDisplay varchar(128),
vendorId varchar(4),
id bigint,
PRIMARY KEY (id),
FOREIGN KEY (vendorHotelID) REFERENCES DC_HOTEL_CATEGORY_MAPPING(vendorHotelID));

CREATE TABLE FEATURE_TYPE(
feature_id bigint,
feature_type varchar(128),
primary key (feature_id));

CREATE TABLE DERBY_FEATURE_ID_MAPPING (
feature_code int,
feature_id bigint,
type_code int,
primary key (feature_id, type_code));



