create table security (
                      id serial primary key,
                      secid varchar(40),
                      name varchar(60),
                      quantity integer,
                      purchase_price double precision,
                      type varchar(20)
);

create table payment (
                         id serial primary key,
                         name varchar(30),
                         payment_date timestamp,
                         value double precision,
                         security_id integer references security(id)
);

create table portfolio (
    id serial primary key,
    name varchar(30)
);

create table portfolio_security (
    id serial primary key,
    portfolio_id integer references portfolio(id),
    security_id integer references security(id)
);