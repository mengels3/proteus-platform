CREATE TABLE SYSTEMPARAMETER(
	sp_name VARCHAR (255) primary key,
	sp_value text not null
);

CREATE TABLE WELL(
	w_id UUID primary key,
	w_device_id VARCHAR (255) not null,
	w_name VARCHAR (255) not null,
	w_lat decimal(19,2) not null,
	w_long decimal(19,2) not null,
	w_altitude float,
	w_maxdepth float,
	w_diameter float
);

CREATE TABLE MEASUREMENT(
	m_id UUID primary key,
	m_timestamp timestamp with time zone not null,
	m_value VARCHAR (255) not null,
	m_fk_wellid UUID not null,
	sensor_type_st_id UUID not null
);

CREATE TABLE SENSOR_TYPE(
	st_id UUID primary key,
	st_value VARCHAR (255) not null
);

CREATE TABLE WELL_SENSOR_TYPES(
	well_w_id UUID not null,
	sensor_types_st_id UUID not null
);

CREATE INDEX pk_01_idx ON WELL_SENSOR_TYPES (well_w_id, sensor_types_st_id);

/* Insert initial data */
INSERT INTO Systemparameter(sp_name,sp_value) VALUES ('dbversion','1.0');

