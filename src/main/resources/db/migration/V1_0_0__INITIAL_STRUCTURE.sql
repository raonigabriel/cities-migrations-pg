CREATE TABLE IF NOT EXISTS states (
	id char(2) PRIMARY KEY,
	acronym char(2) NOT NULL UNIQUE,
	state_name varchar(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS cities (
	id char(7) PRIMARY KEY,
	state_id char(2) NOT NULL,
	city_name varchar(50) NOT NULL,
    CONSTRAINT fk_cities_states FOREIGN KEY(state_id) REFERENCES states(id)
);
