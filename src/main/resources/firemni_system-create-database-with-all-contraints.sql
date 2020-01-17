create schema `database` collate latin1_swedish_ci;

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

create table workTypes
(
    name linestring not null,
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

create table logins
(
    name linestring not null,
    password linestring not null,
    id int auto_increment primary key
);

create table managers
(
	hire_date date null,
	first_name linestring not null,
	last_name linestring not null,
    login linestring not null,
    password linestring not null,
	address linestring not null,
	city linestring not null,
	telephone linestring not null,
	team_id int null,

	id int auto_increment
		primary key,
	constraint managers_teams_id_fk
		foreign key (team_id) references teams (id)
);

create table validators
(
	hire_date date null,
	first_name linestring not null,
	last_name linestring not null,
    login linestring not null,
    password linestring not null,
	address linestring not null,
	city linestring not null,
	telephone linestring not null,
	team_id int null,
	post_id int null,
	contractors int null,
	id int auto_increment
		primary key,
	constraint validators_posts_id_fk
		foreign key (post_id) references posts (id),
	constraint validators_teams_id_fk
		foreign key (team_id) references teams (id)
);

create table contractors
(
	hire_date date null,
	first_name linestring not null,
	last_name linestring not null,
    login linestring not null,
    password linestring not null,
	address linestring not null,
	city linestring not null,
	telephone linestring not null,
	team_id int null,
	validator_id int null,
	swimlane_id int null,
	id int auto_increment
		primary key,
	constraint contractors_swimlanes_id_fk
		foreign key (swimlane_id) references swimlanes (id),
	constraint contractors_teams_id_fk
		foreign key (team_id) references teams (id),
	constraint contractors_validators_id_fk
		foreign key (validator_id) references validators (id)
);


create table domains
(
    due_date date null,
    validator_id int null,
    contractor_id int null,
    name linestring not null,
    info linestring null,
    id int auto_increment primary key,
    constraint domains_contractor_id_fk
        foreign key (contractor_id) references contractors (id),
    constraint domains_validator_id_fk
        foreign key (validator_id) references validators (id)
);

create table works
(
    workType_id int not null,
    info linestring not null,
    hoursWorked double not null,
    contractor_id int not null,
    domain_id int null,
    id int auto_increment primary key,
    constraint works_contractor_id_fk
        foreign key (contractor_id) references contractors (id),
    constraint works_domain_id_fk
        foreign key (domain_id) references domains (id),
    constraint works_worktype_id_fk
        foreign key (workType_id) references workTypes (id)
);

