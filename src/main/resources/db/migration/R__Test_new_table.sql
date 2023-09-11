DROP TABLE IF EXISTS public.person;

create table person (
    id bigint primary key,
    name varchar(100)
);

insert into person(id, name) values(1, 'gokhan')