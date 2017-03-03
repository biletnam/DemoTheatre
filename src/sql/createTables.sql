CREATE TABLE places (idplace integer NOT NULL, categorie char NOT NULL, rang integer NOT NULL, colonne integer NOT NULL, PRIMARY KEY (idplace));

CREATE TABLE places_vendues (idplace integer NOT NULL, idspectacle integer NOT NULL);

CREATE TABLE lesspectacles (idspectacle integer NOT NULL, titre varchar(64) NOT NULL, PRIMARY KEY (idspectacle));
