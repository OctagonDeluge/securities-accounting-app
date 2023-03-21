create table "user" (
    id bigserial primary key,
    email varchar(50),
    password varchar(255),
    name varchar(50)
);

alter table portfolio
    add column user_id bigint references "user"(id);

