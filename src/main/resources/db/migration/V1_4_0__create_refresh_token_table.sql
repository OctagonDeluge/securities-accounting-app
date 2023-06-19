create table refresh_token (
    id bigserial primary key,
    token varchar(255),
    expiration_time bigint,
    client_id bigint references client(id)
)