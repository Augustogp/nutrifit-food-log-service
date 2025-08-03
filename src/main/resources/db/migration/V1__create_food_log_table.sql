CREATE TABLE IF NOT EXISTS food_log (
    id BINARY(16) PRIMARY KEY,
    user_id BINARY(16) NOT NULL,
    date DATE NOT NULL,
    CONSTRAINT uq_user_date UNIQUE (user_id, date)
);