-- DROP TABLE IF EXISTS wards;
-- DROP TABLE IF EXISTS districts;
-- DROP TABLE IF EXISTS provinces;
-- DROP TABLE IF EXISTS administrative_units;
-- DROP TABLE IF EXISTS administrative_regions;

-- CREATE administrative_regions TABLE
CREATE TABLE "aims"."Administrative_regions" (
	id integer NOT NULL,
	"name" varchar(255) NOT NULL,
	name_en varchar(255) NOT NULL,
	code_name varchar(255) NULL,
	code_name_en varchar(255) NULL,
	CONSTRAINT administrative_regions_pkey PRIMARY KEY (id)
);


-- CREATE administrative_units TABLE
CREATE TABLE "aims"."Administrative_units" (
	id integer NOT NULL,
	full_name varchar(255) NULL,
	full_name_en varchar(255) NULL,
	short_name varchar(255) NULL,
	short_name_en varchar(255) NULL,
	code_name varchar(255) NULL,
	code_name_en varchar(255) NULL,
	CONSTRAINT administrative_units_pkey PRIMARY KEY (id)
);


-- CREATE provinces TABLE
CREATE TABLE "aims"."Provinces" (
	code varchar(20) NOT NULL,
	"name" varchar(255) NOT NULL,
	name_en varchar(255) NULL,
	full_name varchar(255) NOT NULL,
	full_name_en varchar(255) NULL,
	code_name varchar(255) NULL,
	administrative_unit_id integer NULL,
	administrative_region_id integer NULL,
	CONSTRAINT provinces_pkey PRIMARY KEY (code),
             FOREIGN KEY (administrative_unit_id) REFERENCES administrative_units(id),
             FOREIGN KEY (administrative_region_id) REFERENCES administrative_regions(id)
);

CREATE INDEX idx_provinces_region ON provinces(administrative_region_id);
CREATE INDEX idx_provinces_unit ON provinces(administrative_unit_id);


-- CREATE districts TABLE
CREATE TABLE "aims"."Districts" (
	code varchar(20) NOT NULL,
	"name" varchar(255) NOT NULL,
	name_en varchar(255) NULL,
	full_name varchar(255) NULL,
	full_name_en varchar(255) NULL,
	code_name varchar(255) NULL,
	province_code varchar(20) NULL,
	administrative_unit_id integer NULL,
	CONSTRAINT districts_pkey PRIMARY KEY (code),
             FOREIGN KEY (province_code) REFERENCES provinces(code),
             FOREIGN KEY (administrative_unit_id) REFERENCES administrative_units(id)
);

CREATE INDEX idx_districts_province ON districts(province_code);
CREATE INDEX idx_districts_unit ON districts(administrative_unit_id);



-- CREATE wards TABLE
CREATE TABLE "aims"."Wards" (
	code varchar(20) NOT NULL,
	"name" varchar(255) NOT NULL,
	name_en varchar(255) NULL,
	full_name varchar(255) NULL,
	full_name_en varchar(255) NULL,
	code_name varchar(255) NULL,
	district_code varchar(20) NULL,
	administrative_unit_id integer NULL,
	CONSTRAINT wards_pkey PRIMARY KEY (code),
             FOREIGN KEY (district_code) REFERENCES districts(code),
             FOREIGN KEY (administrative_unit_id) REFERENCES administrative_units(id)
);

CREATE INDEX idx_wards_district ON wards(district_code);
CREATE INDEX idx_wards_unit ON wards(administrative_unit_id);