

create table portfolio (
                           id bigserial primary key,
                           name varchar(30),
                           total_cost decimal
);

create table security (
                      id bigserial primary key,
                      secid varchar(40),
                      name varchar(60),
                      total_cost decimal,
                      profit decimal,
                      quantity integer,
                      type varchar(20),
                      exchange varchar(20),
                      currency varchar(20),
                      portfolio_id bigint references portfolio(id)
);