create schema if not exists police_scanner;

--drop table police_scanner.county_feed;
--drop table police_scanner.county;
--drop table police_scanner.state;
--drop table police_scanner.country;
drop table police_scanner.county_feed;

create table if not exists police_scanner.country (
    id bigint PRIMARY KEY generated always as identity,
    name varchar(100),
    code varchar(10),
    reference_id bigint UNIQUE
);

create table if not exists police_scanner.state (
    id bigint PRIMARY KEY generated always as identity,
    name varchar(100),
    code varchar(10),
    reference_id bigint UNIQUE,
    country_id bigint REFERENCES police_scanner.country (id)
);

create table if not exists police_scanner.county (
    id bigint PRIMARY KEY generated always as identity,
    name varchar(100),
    type varchar(100),
    reference_id bigint UNIQUE,
    state_id bigint REFERENCES police_scanner.state (id)
);

create table if not exists police_scanner.county_feed (
    id bigint PRIMARY KEY generated always as identity,
    name varchar(100),
    genre varchar(100),
    mouth varchar(300),
    mouth_token varchar(300),
    relay_host varchar(300),
    relay_port varchar(300),
    bitrate int,
    reference_id bigint UNIQUE,
    county_id bigint REFERENCES police_scanner.county (id)
);
