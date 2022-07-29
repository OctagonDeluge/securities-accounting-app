create table portfolio (
                           id bigserial primary key,
                           name varchar(30),
                           profit double precision
);

create table security (
                      id bigserial primary key,
                      secid varchar(40),
                      profit double precision,
                      name varchar(60),
                      type varchar(20),
                      exchange varchar(20),
                      portfolio_id bigint references portfolio(id)
);