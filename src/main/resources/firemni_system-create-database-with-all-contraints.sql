create schema `database` collate latin1_swedish_ci;

create table posts
(
	name text not null,
	id int auto_increment
		primary key
);

create table swimlanes
(
	name text not null,
	id int auto_increment
		primary key
);

create table work_types
(
    id int auto_increment
        primary key,
    name text not null
);


create table teams
(
    id int auto_increment
        primary key,
	name text not null,
	manager_id int null
);

create table logins
(
    name text not null,
    password text not null,
    id int auto_increment primary key
);

create table managers
(
    id int auto_increment
        primary key,
	first_name text not null,
	last_name text not null,
    login text not null,
    password text not null,
	address text not null,
	city text not null,
    hire_date date null,
	telephone text not null,
	team_id int null,
	constraint managers_teams_id_fk
		foreign key (team_id) references teams (id)
);

create table validators
(
    id int auto_increment
        primary key,
	first_name text not null,
	last_name text not null,
    login text not null,
    password text not null,
	address text not null,
	city text not null,
    hire_date date null,
	telephone text not null,
	team_id int null,
	post_id int null,
	constraint validators_posts_id_fk
		foreign key (post_id) references posts (id),
	constraint validators_teams_id_fk
		foreign key (team_id) references teams (id)
);

create table contractors
(
    id int auto_increment
        primary key,
	first_name text not null,
	last_name text not null,
    login text not null,
    password text not null,
	address text not null,
	city text not null,
    hire_date date null,
	telephone text not null,
	team_id int null,
	validator_id int null,
	swimlane_id int null,

	constraint contractors_swimlanes_id_fk
		foreign key (swimlane_id) references swimlanes (id),
	constraint contractors_teams_id_fk
		foreign key (team_id) references teams (id),
	constraint contractors_validators_id_fk
		foreign key (validator_id) references validators (id)
);


create table domains
(
    id int auto_increment primary key,
    name text not null,
    due_date date null,
    info text null,
    validator_id int null,
    contractor_id int null,
    constraint domains_contractor_id_fk
        foreign key (contractor_id) references contractors (id),
    constraint domains_validator_id_fk
        foreign key (validator_id) references validators (id)
);

create table works
(
    team_id int null,
    work_date date not null,
    workType_id int not null,
    info text not null,
    hoursWorked float not null,
    contractor_id int not null,
    domain_id int null,
    id int auto_increment primary key,
    constraint works_contractor_id_fk
        foreign key (contractor_id) references contractors (id),
    constraint works_domain_id_fk
        foreign key (domain_id) references domains (id),
    constraint works_worktype_id_fk
        foreign key (workType_id) references work_types (id),
    constraint works_team_id_fk
        foreign key (team_id) references teams (id)
);
