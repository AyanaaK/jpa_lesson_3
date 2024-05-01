drop table if exists city, users;

create table city (
                      city_id serial8,
                      name varchar(30) not null,
                      index varchar(30) not null,
                      primary key (city_id)
);

create table users (
    user_id serial8,
    login varchar(30) not null,
    name varchar(30) not null,
    primary key (user_id),
    unique (login),
    city_id int8,
    foreign key (city_id) references city(city_id)
);

insert into city(name, index)
values ('Алматы',    050000),
       ('Астана',    010000),
       ('Шымкент',   160000),
       ('Актау',     130000),
       ('Актобе',    030000),
       ('Атырау',    060000),
       ('Караганда', 100000),
       ('Кокшетау',  020000),
       ('Костанай',  110000),
       ('Кызылорда', 120000)

