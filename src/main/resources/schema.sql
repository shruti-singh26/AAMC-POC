CREATE TABLE PERSON (
  id bigserial PRIMARY KEY NOT NULL,
  name VARCHAR (50) NOT NULL,
  email VARCHAR (256) UNIQUE NOT NULL,
  gender VARCHAR(256),
  age integer NOT NULL,
  address VARCHAR(256)
);