DROP TABLE IF EXISTS account CASCADE;
DROP TABLE IF EXISTS task CASCADE;
DROP TABLE IF EXISTS provider CASCADE;
DROP TABLE IF EXISTS account_provider;

CREATE TABLE account
(
    id       bigserial       not null,
    email    varchar(255) not null,
    password varchar(255) not null,
    username varchar(255) not null,
    secret varchar(255),
    tfa_enabled boolean not null,
    primary key (id)
);

CREATE TABLE task
(
    id         bigserial       not null,
    title      varchar(255) not null,
    deadline   timestamp    not null,
    finished   boolean      not null,
    account_id bigint       not null,
    primary key (id),
    foreign key (account_id) references account (id) on delete cascade
);

CREATE TABLE provider
(
    id            serial                                                    not null,
    provider_type varchar(255) CHECK (provider_type IN ('LOCAL', 'GOOGLE')) not null,
    primary key (id)
);

CREATE TABLE account_provider
(
    account_id  bigint not null,
    provider_id int    not null,
    primary key (account_id, provider_id),
    foreign key (account_id) references account (id) on delete cascade,
    foreign key (provider_id) references provider (id) on delete cascade
);

