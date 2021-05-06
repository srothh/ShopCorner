CREATE TYPE IF NOT EXISTS permissions_type AS ENUM('admin','employee');

CREATE TABLE IF NOT EXISTS address
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    street VARCHAR(255) NOT NULL,
    postal_code BIGINT NOT NULL,
    house_number VARCHAR(64) NOT NULL,
    stair_number BIGINT DEFAULT NULL,
    door_number VARCHAR(64) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS customer
(
id BIGINT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(255) NOT NULL,
login_name VARCHAR(128) NOT NULL,
password VARCHAR(128) NOT NULL,
email VARCHAR(255)NOT NULL,
address_id BIGINT NOT NULL,
CONSTRAINT address_fk FOREIGN KEY (address_id) REFERENCES address(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS operator
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    login_name VARCHAR(128) NOT NULL,
    password VARCHAR(128) NOT NULL,
    email VARCHAR(255)NOT NULL,
    permissions permissions_type DEFAULT NOT NULL
);


CREATE TABLE IF NOT EXISTS tax
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    percentage BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS category
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS product
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    amount BIGINT NOT NULL,
    category_id BIGINT DEFAULT NULL,
    tax_id BIGINT NOT NULL,
    CONSTRAINT category_fk FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE SET NULL,
    CONSTRAINT tax_fk FOREIGN KEY (tax_id) REFERENCES tax(id)
);

CREATE TABLE IF NOT EXISTS promotion
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    discount BIGINT NOT NULL,
    expiration_date DATE NOT NULL,
    code VARCHAR(64) NOT NULL,
    UNIQUE(code)
);

CREATE TABLE IF NOT EXISTS invoice
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    date DATE NOT NULL,
    amount BIGINT NOT NULL,
    promotion_id BIGINT NULL,
    CONSTRAINT promotion_fk FOREIGN KEY (promotion_id) REFERENCES promotion(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS invoice_item
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    invoice_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    CONSTRAINT invoice_item_fk FOREIGN KEY (invoice_id) REFERENCES invoice(id) ON DELETE SET NULL,
    CONSTRAINT product_fk FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS cancellation_invoice
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    invoice_id BIGINT NOT NULL,
    CONSTRAINT cancellation_invoice_fk FOREIGN KEY (invoice_id) REFERENCES invoice(id)
);


CREATE TABLE IF NOT EXISTS customer_order
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_number UUID NOT NULL,
    invoice_id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    CONSTRAINT order_invoice_fk FOREIGN KEY (invoice_id) REFERENCES invoice(id),
    CONSTRAINT customer_fk FOREIGN KEY (customer_id) REFERENCES customer(id)
);
