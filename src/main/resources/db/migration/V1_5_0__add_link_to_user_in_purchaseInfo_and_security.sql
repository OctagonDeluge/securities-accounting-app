alter table security
add column client_id bigint references client(id);

alter table purchase_info
add column client_id bigint references client(id);
