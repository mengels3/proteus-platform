CREATE TABLE SYSTEMPARAMETER(
	sp_name text primary key,
	sp_value text not null
);

CREATE TABLE WELL(
	w_id serial primary key,
	w_name text not null,
	w_lat float not null,
	w_long float not null,
	w_altitude float,
	w_maxdepth float,
	w_diameter float
);
CREATE TABLE MEASUREMENT(
	m_id serial primary key,
	m_timestamp timestamp not null,
	m_wellid bigint not null,
	m_water_level float not null,
	m_phvalue float,
	m_temperature float
);
ALTER TABLE MEASUREMENT ADD CONSTRAINT ref_well_id FOREIGN KEY (m_wellid) REFERENCES WELL (w_id);

CREATE INDEX MEASUREMENT_TIMESTAMP ON MEASUREMENT 
(
    m_timestamp
);

/* Insert initial data */
INSERT INTO Systemparameter(sp_name,sp_value) VALUES ('dbversion','1.0');
