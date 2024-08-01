create table purchase_info (
    id bigserial primary key,
    price decimal,
    quantity integer,
    security_id bigint references security(id)
)