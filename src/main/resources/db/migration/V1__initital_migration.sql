CREATE TABLE users
(
    id       BIGINT AUTO_INCREMENT,
    name     VARCHAR(255) NOT NULL,
    email    VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,

    CONSTRAINT pk_users PRIMARY KEY (id)
);

CREATE TABLE addresses
(
    id       BIGINT AUTO_INCREMENT,
    street   VARCHAR(255) NOT NULL,
    city     VARCHAR(255) NOT NULL,
    state    VARCHAR(255) NOT NULL,
    postcode VARCHAR(255) NOT NULL,
    user_id  BIGINT       NOT NULL,

    CONSTRAINT pk_addresses PRIMARY KEY (id),

    CONSTRAINT fk_addresses_user
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE CASCADE
);

CREATE TABLE profiles
(
    id             BIGINT,
    bio            TEXT,
    phone_number   VARCHAR(15),
    date_of_birth  DATE,
    loyalty_points INT UNSIGNED DEFAULT 0,

    CONSTRAINT pk_profiles PRIMARY KEY (id),

    CONSTRAINT fk_profiles_user
        FOREIGN KEY (id)
            REFERENCES users (id)
            ON DELETE CASCADE
);

CREATE TABLE categories
(
    id   TINYINT AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,

    CONSTRAINT pk_categories PRIMARY KEY (id)
);

CREATE TABLE products
(
    id          BIGINT AUTO_INCREMENT,
    name        VARCHAR(255)   NOT NULL,
    price       DECIMAL(10, 2) NOT NULL,
    description TEXT           NOT NULL,
    category_id TINYINT,

    CONSTRAINT pk_products PRIMARY KEY (id),

    CONSTRAINT fk_products_category
        FOREIGN KEY (category_id)
            REFERENCES categories (id)
            ON DELETE RESTRICT
);

CREATE TABLE wishlist
(
    product_id BIGINT NOT NULL,
    user_id    BIGINT NOT NULL,

    CONSTRAINT pk_wishlist PRIMARY KEY (product_id, user_id),

    CONSTRAINT fk_wishlist_product
        FOREIGN KEY (product_id)
            REFERENCES products (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_wishlist_user
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE CASCADE
);

CREATE TABLE carts
(
    id           BINARY(16) NOT NULL DEFAULT (UUID_TO_BIN(UUID())),
    date_created DATE       NOT NULL DEFAULT (CURDATE()),

    CONSTRAINT pk_carts PRIMARY KEY (id)
);

CREATE TABLE cart_items
(
    id         BIGINT AUTO_INCREMENT,
    cart_id    BINARY(16)    NOT NULL,
    product_id BIGINT        NOT NULL,
    quantity   INT DEFAULT 1 NOT NULL,

    CONSTRAINT pk_cart_items PRIMARY KEY (id),

    CONSTRAINT uk_cart_product UNIQUE (cart_id, product_id),

    CONSTRAINT fk_cart_items_cart
        FOREIGN KEY (cart_id)
            REFERENCES carts (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_cart_items_product
        FOREIGN KEY (product_id)
            REFERENCES products (id)
            ON DELETE CASCADE
);