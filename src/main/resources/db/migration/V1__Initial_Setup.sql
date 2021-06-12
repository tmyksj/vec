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
