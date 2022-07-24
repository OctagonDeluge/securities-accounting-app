create table portfolio (
                           id bigserial primary key,
                           name varchar(30)
);

create table security (
                      id bigserial primary key,
                      secid varchar(40),
                      name varchar(60),
                      quantity integer,
                      purchase_price double precision,
                      type varchar(20),
                      exchange varchar(20),
                      portfolio_id integer references portfolio(id)
);