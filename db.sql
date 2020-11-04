SET MODE postgresql;


CREATE DATABASE foodbank;

\c foodbank

CREATE TABLE foodBankLocations(
id serial PRIMARY KEY,
location varchar
);

CREATE TABLE donationTypes(
id serial PRIMARY KEY,
foodItem varchar
);