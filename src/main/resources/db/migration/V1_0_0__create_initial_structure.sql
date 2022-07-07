create table type (
                      id integer primary key,
                      name varchar(30),
                      title varchar(30)
);

insert into type values(1, 'share', 'Акция');
insert into type values(2, 'bond', 'Облигация');

create table payment (
                        id serial primary key,
                        name varchar(30),
                        date date,
                        value double precision
);

create table security (
                      id serial primary key,
                      secid varchar(40),
                      name varchar(60),
                      quantity integer,
                      purchase_price double precision,
                      type_id integer references type(id)
);

create table security_payment (
    security_id integer references security(id),
    payment_id integer references payment(id),
    primary key (security_id, payment_id)
);

create table portfolio (
    id serial primary key
);

create table portfolio_security (
    portfolio_id integer references portfolio(id),
    security_id integer references security(id),
    primary key (portfolio_id, security_id)
);