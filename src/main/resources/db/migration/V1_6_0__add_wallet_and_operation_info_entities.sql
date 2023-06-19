create table wallet (
    id bigserial primary key,
    name varchar(40),
    balance decimal,
    currency varchar(20),
    client_id bigint references client(id)
);

create table operation_info (
    id bigserial primary key,
    operation_type varchar(20),
    secid varchar(40),
    name varchar(60),
    quantity integer,
    operation_cost decimal,
    overall decimal,
    profit decimal,
    currency varchar(20),
    operation_date timestamp,
    wallet_id bigint references wallet(id),
    client_id bigint references client(id)
);

alter table client
add column level varchar(30);