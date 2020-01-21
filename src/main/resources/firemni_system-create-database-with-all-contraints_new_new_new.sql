create schema `database` collate latin1_swedish_ci;

create table contractors
(
	id int auto_increment
		primary key,
	first_name varchar(255) null,
	last_name varchar(255) null,
	login varchar(255) null,
	password varchar(255) null,
	role varchar(255) null,
	address varchar(255) null,
	city varchar(255) null,
	hire_date date null,
	telephone varchar(255) null,
	validator_id int null,
	swimlane_id int null,
	team_id int null
)
engine=MyISAM;

create index FK3jdpk3iuyexc2mvhgob0e42dt
	on contractors (team_id);

create index FKhn9prdlt6ugfv8hlcfef0ibjc
	on contractors (validator_id);

create index FKrqy460imtme2w82d54njfcqa0
	on contractors (swimlane_id);

create table domains
(
	id int auto_increment
		primary key,
	name varchar(255) null,
	due_date date null,
	info varchar(255) null,
	contractor_id int null,
	validator_id int null
)
engine=MyISAM;

create index FK8t41l0g4kxelu8v4d2ubjjqfc
	on domains (contractor_id);

create index FKlnxyqky6oqhr59ibjqfj1ge54
	on domains (validator_id);

create table feedbacks
(
	id int not null
		primary key,
	domain_id int not null,
	feedback_text varchar(300) null,
	reworks int null,
	quality varchar(20) null,
	constraint feedbacks_domains_id_fk
		foreign key (domain_id) references domains (id)
);

create table logins
(
	id int auto_increment
		primary key,
	name varchar(255) null,
	password varchar(255) null
)
engine=MyISAM;

create table managers
(
	id int auto_increment
		primary key,
	first_name varchar(255) null,
	last_name varchar(255) null,
	login varchar(255) null,
	password varchar(255) null,
	role varchar(255) null,
	address varchar(255) null,
	city varchar(255) null,
	hire_date date null,
	telephone varchar(255) null,
	team_id int null
)
engine=MyISAM;

create index FKi7ou1ny6u13mux7t9bx8lc087
	on managers (team_id);

create table plans
(
	id int auto_increment
		primary key,
	day1 int null,
	day10 int null,
	day11 int null,
	day12 int null,
	day13 int null,
	day14 int null,
	day15 int null,
	day16 int null,
	day17 int null,
	day18 int null,
	day19 int null,
	day2 int null,
	day20 int null,
	day21 int null,
	day22 int null,
	day23 int null,
	day24 int null,
	day25 int null,
	day26 int null,
	day27 int null,
	day28 int null,
	day29 int null,
	day3 int null,
	day30 int null,
	day31 int null,
	day4 int null,
	day5 int null,
	day6 int null,
	day7 int null,
	day8 int null,
	day9 int null,
	month_number int null,
	contractor_id int null
)
engine=MyISAM;

create index FKrij1qp9mc8jnwp3m8ha0q7n2m
	on plans (contractor_id);

create table posts
(
	id int auto_increment
		primary key,
	name varchar(255) null
)
engine=MyISAM;

create table swimlanes
(
	id int auto_increment
		primary key,
	name varchar(255) null
)
engine=MyISAM;

create table teams
(
	id int auto_increment
		primary key,
	name varchar(255) null,
	manager_id int null
)
engine=MyISAM;

create index FK472b402o13p48r4q8bklljsyg
	on teams (manager_id);

create table validators
(
	id int auto_increment
		primary key,
	first_name varchar(255) null,
	last_name varchar(255) null,
	login varchar(255) null,
	password varchar(255) null,
	role varchar(255) null,
	address varchar(255) null,
	city varchar(255) null,
	hire_date date null,
	telephone varchar(255) null,
	post_id int null,
	team_id int null
)
engine=MyISAM;

create index FK4406273t2a9vkk6rf5ko3u29c
	on validators (post_id);

create index FKq91v53orfhgo2a3vjoiv0t5o
	on validators (team_id);

create table validators_contractors
(
	validator_id int not null,
	contractors_id int not null,
	primary key (validator_id, contractors_id),
	constraint UK_fxq4ihjgmcgp0dxban0kuwunx
		unique (contractors_id)
)
engine=MyISAM;

create table work_types
(
	id int auto_increment
		primary key,
	name varchar(255) null
)
engine=MyISAM;

create table works
(
	id int auto_increment
		primary key,
	hours_worked float not null,
	info varchar(255) null,
	work_date date not null,
	contractor_id int not null,
	domain_id int null,
	team_id int null,
	work_type_id int not null
)
engine=MyISAM;

create index FK7vhm8byet8vr7mugm93fkod8p
	on works (domain_id);

create index FKlxi6ustccucu6j979petn9ces
	on works (contractor_id);

create index FKq3ke95afa70chxr6kspl59pp6
	on works (team_id);

create index FKq6fv1dvvc98iyvouvvsvlxx2g
	on works (work_type_id);

