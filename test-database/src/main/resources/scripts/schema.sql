CREATE SCHEMA common;

CREATE TABLE common.countries
(
    count_id SERIAL PRIMARY KEY,
    count_nombre character varying ,
    count_abreviacion character varying
);


CREATE TABLE common.type_categories
(
    tyca_id serial primary key,
    tyca_name character varying ,
    tyca_slug character varying ,
    tyca_status boolean,
    tyca_can_edit boolean
);


CREATE TABLE common.types
(
    type_id serial primary key,
    tyca_id integer not null,
    type_status boolean DEFAULT true,
    type_name character varying ,
    type_description character varying ,
    type_slug character varying ,
    type_can_edit boolean DEFAULT false,
    type_json_params jsonb,
    CONSTRAINT fk_type_categories FOREIGN KEY (tyca_id)
        REFERENCES common.type_categories (tyca_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);


CREATE TABLE common.companies
(
    comp_id BIGSERIAL PRIMARY KEY,
    comp_status boolean DEFAULT true,
    comp_type integer NOT NULL,
    comp_slug character varying(255) NOT NULL,
    comp_schema character varying(255),
    comp_name character varying(45) NOT NULL,
    comp_creation timestamp without time zone NOT NULL DEFAULT now(),
    comp_json_params jsonb,
    comp_city character varying(45),
    comp_state character varying(45),
    count_id integer,
    comp_zip character varying(45),
    comp_address character varying(80),
    comp_email character varying(80),
    comp_phone character varying(45),
    comp_nit character varying,
    CONSTRAINT fk_company_countries FOREIGN KEY (count_id)
        REFERENCES common.countries (count_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);
