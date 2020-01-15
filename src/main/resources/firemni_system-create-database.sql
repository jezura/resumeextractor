create schema `database`;

create table managers
(
	hire_date date null,
	first_name linestring not null,
	last_name linestring not null,
	address linestring not null,
	city linestring not null,
	telephone linestring not null,
	team_id int null,
	id int auto_increment
		primary key
);

create table validators
(
	hire_date date null,
	first_name linestring not null,
	last_name linestring not null,
	address linestring not null,
	city linestring not null,
	telephone linestring not null,
	team_id int null,
	post int null,
	contractors int null,
	id int auto_increment
		primary key
);

create table contractors
(
	hire_date date null,
	first_name linestring not null,
	last_name linestring not null,
	address linestring not null,
	city linestring not null,
	telephone linestring not null,
	team_id int null,
	validator_id int null,
	swimlane_id int null,
	id int auto_increment
		primary key
);

create table teams
(
	name linestring not null,
	manager_id int null,
	id int auto_increment
		primary key
);

create table posts
(
	name linestring not null,
	id int auto_increment
		primary key
);

create table swimlanes
(
	name linestring not null,
	id int auto_increment
		primary key
);


INSERT INTO contractors (hire_date, first_name, last_name,address, city, telephone)
VALUES (2000-11-22, 123412, 123412,123412, 123412, 1111111);