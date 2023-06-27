CREATE TABLE product
(
    id          BIGSERIAL PRIMARY KEY,
    sku         varchar(255) UNIQUE NOT NULL,
    name        varchar(255)        NOT NULL,
    description varchar(255)
);