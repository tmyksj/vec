create table user
(
    id                     varchar(36)  not null,
    email                  varchar(255) not null,
    password_encrypted     varchar(255) not null,
    is_account_expired     boolean      not null,
    is_account_locked      boolean      not null,
    is_credentials_expired boolean      not null,
    is_enabled             boolean      not null,
    has_role_admin         boolean      not null,
    has_role_consumer      boolean      not null,
    created_date           timestamp(6) not null,
    last_modified_date     timestamp(6) not null,
    version                bigint       not null,
    primary key (id),
    unique key (email)
) engine = InnoDB
  default charset utf8mb4;

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

create table cart
(
    id                 varchar(36)  not null,
    user_id            varchar(36)  not null,
    created_date       timestamp(6) not null,
    last_modified_date timestamp(6) not null,
    version            bigint       not null,
    primary key (id),
    unique key (user_id),
    foreign key (user_id) references user (id) on delete cascade on update cascade
) engine = InnoDB
  default charset utf8mb4;

create table cart_product
(
    id                 varchar(36)  not null,
    cart_id            varchar(36)  not null,
    product_id         varchar(36)  not null,
    added_date         timestamp(6) not null,
    created_date       timestamp(6) not null,
    last_modified_date timestamp(6) not null,
    version            bigint       not null,
    primary key (id),
    foreign key (cart_id) references cart (id) on delete cascade on update cascade,
    foreign key (product_id) references product (id) on delete cascade on update cascade
) engine = InnoDB
  default charset utf8mb4;

create table privacy_policy
(
    id                 varchar(36)  not null,
    body               text         not null,
    applied_date       timestamp(6) not null,
    created_date       timestamp(6) not null,
    last_modified_date timestamp(6) not null,
    version            bigint       not null,
    primary key (id)
) engine = InnoDB
  default charset utf8mb4;

create table terms_of_service
(
    id                 varchar(36)  not null,
    body               text         not null,
    applied_date       timestamp(6) not null,
    created_date       timestamp(6) not null,
    last_modified_date timestamp(6) not null,
    version            bigint       not null,
    primary key (id)
) engine = InnoDB
  default charset utf8mb4;
