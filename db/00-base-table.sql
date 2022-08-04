CREATE USER testuser WITH PASSWORD '1234';
CREATE DATABASE test;

GRANT ALL PRIVILEGES ON DATABASE test TO testuser;

\c test

CREATE SEQUENCE test_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;

CREATE TABLE test (
      id bigint DEFAULT nextval('test_seq'::regclass) NOT NULL,
      name character varying(50),
      age integer,
      create_time timestamp without time zone DEFAULT now() NOT NULL
);

INSERT INTO test(name, age) VALUES ('Evis', 31);