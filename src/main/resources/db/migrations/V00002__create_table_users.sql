create table users
(
    id         int generated by default as identity,
    username   varchar(255) not null,
    first_name varchar(255) not null,
    last_name  varchar(255) not null,
    email      varchar(255) not null,
    password   varchar(255) not null,
    role       varchar(255) not null,
    primary key (id)
);