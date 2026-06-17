CREATE TABLE payments (
    id UUID PRIMARY KEY,
    merchant_id VARCHAR(60) NOT NULL,
    order_id VARCHAR(80) NOT NULL,
    customer_id VARCHAR(80) NOT NULL,
    amount NUMERIC(19, 2) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    status VARCHAR(30) NOT NULL,
    failure_reason VARCHAR(250),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    UNIQUE (merchant_id, order_id)
);
