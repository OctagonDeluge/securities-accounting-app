create table portfolio (
                           id bigserial primary key,
                           name varchar(30),
                           profit decimal
);

create table security (
                      id bigserial primary key,
                      secid varchar(40),
                      profit decimal,
                      name varchar(60),
                      type varchar(20),
                      exchange varchar(20),
                      currency varchar(20),
                      portfolio_id bigint references portfolio(id)
);