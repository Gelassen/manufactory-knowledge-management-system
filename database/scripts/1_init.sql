
CREATE TABLE machines (
    machine_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    manufacturer VARCHAR(255) NOT NULL,
    barcode VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE breakdowns (
    breakdown_id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    failure VARCHAR(5000) NOT NULL,
    solution VARCHAR(5000) NOT NULL,
    date_time BIGINT NOT NULL,
    machine_id INTEGER REFERENCES machines(machine_id) ON DELETE SET NULL
);

CREATE TABLE photofixations (
    id SERIAL PRIMARY KEY,
    photo TEXT NOT NULL,
    breakdown_id INTEGER REFERENCES breakdowns(breakdown_id) ON DELETE SET NULL
);

