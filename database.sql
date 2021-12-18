
.open database.db
PRAGMA foreign_keys= ON;

DROP TABLE IF EXISTS Practicas;
DROP TABLE IF EXISTS Alumnos;

CREATE TABLE Alumnos
(
	dni VARCHAR (10) NOT NULL,
	nombre VARCHAR (30) NOT NULL,
	usuario_Git VARCHAR (30) NOT NULL,

	PRIMARY KEY (dni)
);

CREATE TABLE Practicas
(
	dni VARCHAR (10) NOT NULL,
	nombre VARCHAR (70) NOT NULL,
	url VARCHAR (80) NOT NULL,
	
	FOREIGN KEY (dni) REFERENCES Alumnos (dni),
	PRIMARY KEY (url)
);

INSERT INTO Alumnos(dni, nombre, usuario_Git)
	VALUES ('26237769H','Paco Fernandez','pacfer');
	
INSERT INTO Alumnos(dni, nombre, usuario_Git)
	VALUES ('46239069U','María Perez','mariaperez');

INSERT INTO Alumnos(dni, nombre, usuario_Git)
	VALUES ('95639423Y','Laura Gonzalez','lauGon');
	
	
INSERT INTO Practicas(dni, nombre, url)
	VALUES ('95639423Y','P1','http://gitlab.com/laugon/P1');

INSERT INTO Practicas(dni, nombre, url)
	VALUES ('95639423Y','P2','http://gitlab.com/laugon/P2');

INSERT INTO Practicas(dni, nombre, url)
	VALUES ('26237769H','P1','http://gitlab.com/pacfer/P1');

INSERT INTO Practicas(dni, nombre, url)
	VALUES ('26237769H','P2','http://gitlab.com/pacfer/P2');	
	
INSERT INTO Practicas(dni, nombre, url)
	VALUES ('46239069U','P1','http://gitlab.com/mariaperez/P1');
	
INSERT INTO Practicas(dni, nombre, url)
	VALUES ('46239069U','P3','http://gitlab.com/mariaperez/P3');
	
