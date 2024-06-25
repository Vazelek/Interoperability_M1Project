create table if not exists product (
    id identity,
    name varchar(100) not null,
    stock int not null,
    price numeric not null
);