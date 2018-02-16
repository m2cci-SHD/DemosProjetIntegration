DROP TABLE places_vendues;
DROP TABLE places;
DROP TABLE lesspectacles;


CREATE TABLE places (
   idplace integer NOT NULL,
   categorie char NOT NULL,
   rang integer NOT NULL,
   colonne integer NOT NULL,
   PRIMARY KEY (idplace)
);

CREATE TABLE lesspectacles (
    idspectacle integer NOT NULL,
    titre varchar(64) NOT NULL,
    PRIMARY KEY (idspectacle)
);

CREATE TABLE places_vendues (
    idplace integer NOT NULL,
    idspectacle integer NOT NULL, 
    CONSTRAINT PK_PlacesVendues PRIMARY KEY (idplace,idspectacle),
    FOREIGN KEY (idplace) REFERENCES places(idplace),
    FOREIGN KEY (idspectacle) REFERENCES lesspectacles(idspectacle)
);


INSERT INTO lesspectacles VALUES(1, 'Le Spectacle 1');
INSERT INTO lesspectacles VALUES(2, 'Le Spectacle 2');
INSERT INTO lesspectacles VALUES(3, 'Le Spectacle 3');
