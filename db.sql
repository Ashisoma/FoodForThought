SET MODE postgresql;


CREATE DATABASE foodbank;

\c foodbank


CREATE TABLE registration(
id serial PRIMARY KEY,
name varchar,
email varchar,
password varchar,
location varchar,
donationType varchar
);