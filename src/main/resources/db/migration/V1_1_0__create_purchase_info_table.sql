create table purchase_info (
    id bigserial primary key,
    purchase_date timestamp,
    price decimal,
    quantity integer,
    currency varchar(20),
    security_id bigint references security(id)
)