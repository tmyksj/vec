create table product
(
    id                 varchar(36)  not null,
    name               text         not null,
    description        text         not null,
    amount             bigint       not null,
    stock              bigint       not null,
    created_date       timestamp(6) not null,
    last_modified_date timestamp(6) not null,
    version            bigint       not null,
    primary key (id)
) engine = InnoDB
  default charset utf8mb4;

create table user
(
    id                     varchar(36)  not null,
    email                  varchar(255) not null,
    password_encrypted     varchar(255) not null,
    is_account_expired     boolean      not null,
    is_account_locked      boolean      not null,
    is_credentials_expired boolean      not null,
    is_enabled             boolean      not null,
    has_authority_general  boolean      not null,
    created_date           timestamp(6) not null,
    last_modified_date     timestamp(6) not null,
    version                bigint       not null,
    primary key (id),
    unique key (email)
) engine = InnoDB
  default charset utf8mb4;
