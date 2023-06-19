create table client (
    id bigserial primary key,
    email varchar(50),
    password varchar(255),
    name varchar(50)
);

alter table portfolio
    add column client_id bigint references client(id);

