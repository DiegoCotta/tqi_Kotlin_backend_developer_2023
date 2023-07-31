CREATE TABLE coupon
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    code            VARCHAR(255)          NOT NULL,
    discount_type   INT                   NOT NULL,
    discount_value  DECIMAL(19, 2)        NOT NULL,
    expiration_date date                  NOT NULL,
    CONSTRAINT pk_coupon PRIMARY KEY (id)
);

CREATE TABLE category
(
    id   BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255)          NOT NULL,
    CONSTRAINT pk_category PRIMARY KEY (id)
);

CREATE TABLE product
(
    id             BIGINT AUTO_INCREMENT NOT NULL,
    name           VARCHAR(255)          NOT NULL,
    measuring_unit INT                   NOT NULL,
    price          DECIMAL(19, 2)        NOT NULL,
    category_id    BIGINT                NULL,
    CONSTRAINT pk_product PRIMARY KEY (id)
);

CREATE TABLE cart
(
    id                        BINARY(16)     NOT NULL,
    total_price               DECIMAL(19, 2) NULL,
    coupon_id                 BIGINT         NULL,
    payment_type              INT            NULL,
    total_price_with_discount DECIMAL(19, 2) NULL,
    date                      date           NULL,
    CONSTRAINT pk_cart PRIMARY KEY (id)
);

CREATE TABLE cart_product
(
    quantity   DECIMAL(19, 2) NOT NULL,
    price      DECIMAL(19, 2) NULL,
    cart_id    BINARY(16)   NOT NULL,
    product_id BIGINT         NOT NULL,
    CONSTRAINT pk_cart_product PRIMARY KEY (cart_id, product_id)
);

CREATE TABLE tab_user
(
    id_user  INT AUTO_INCREMENT NOT NULL,
    name     VARCHAR(50)        NOT NULL,
    username VARCHAR(20)        NOT NULL,
    password VARCHAR(100)       NOT NULL,
    CONSTRAINT pk_tab_user PRIMARY KEY (id_user)
);

CREATE TABLE tab_user_roles
(
    user_id INT          NOT NULL,
    role_id VARCHAR(255) NULL
);

ALTER TABLE tab_user_roles
    ADD CONSTRAINT fk_tab_user_roles_on_user FOREIGN KEY (user_id) REFERENCES tab_user (id_user);

ALTER TABLE category
    ADD CONSTRAINT uc_category_name UNIQUE (name);

ALTER TABLE product
    ADD CONSTRAINT FK_PRODUCT_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES category (id);

ALTER TABLE cart
    ADD CONSTRAINT FK_CART_ON_COUPON FOREIGN KEY (coupon_id) REFERENCES coupon (id);

ALTER TABLE cart_product
    ADD CONSTRAINT FK_CART_PRODUCT_ON_CARTID FOREIGN KEY (cart_id) REFERENCES cart (id);

ALTER TABLE cart_product
    ADD CONSTRAINT FK_CART_PRODUCT_ON_PRODUCTID FOREIGN KEY (product_id) REFERENCES product (id);