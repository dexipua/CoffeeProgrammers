create table subjects
(
    id int generated by default as identity,
    name varchar(255),
    teacher_id int,
    primary key(id)
)