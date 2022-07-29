create table purchase_info (
    id bigserial primary key,
    purchase_date timestamp,
    price double precision,
    quantity integer,
    security_id bigint references security(id)
)