CREATE TABLE IF NOT EXISTS orders (
    id            BIGSERIAL PRIMARY KEY,
    customer_name VARCHAR(100)   NOT NULL,
    product       VARCHAR(100)   NOT NULL,
    quantity      INT            NOT NULL,
    status        VARCHAR(20)    NOT NULL DEFAULT 'PENDING',
    total         DECIMAL(10,2),
    created_at    TIMESTAMP      DEFAULT CURRENT_TIMESTAMP
);
