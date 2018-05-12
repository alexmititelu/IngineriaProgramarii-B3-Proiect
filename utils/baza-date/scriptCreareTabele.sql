CREATE TABLE STUDENTI(
	nr_matricol VARCHAR(16) NOT NULL PRIMARY KEY,
	email varchar(255) not null 
);
/
CREATE TABLE CONTURI(
	username varchar(30)  NOT NULL PRIMARY KEY,
	email varchar(255) not null,
	password varchar(64),
	FOREIGN KEY (email) REFERENCES STUDENTI(email)
);
/
CREATE TABLE CONTURI_CURENTE(
	token varchar(64) NOT NULL PRIMARY KEY,
	username varchar(30) NOT NULL,
	creation_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	FOREIGN KEY (username) not null,
);
/
CREATE TABLE REGISTER_LINKS(
	token varchar(64) NOT NULL PRIMARY KEY,
	email varchar(255) NOT NULL,
	creation_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
/
CREATE TABLE PROFESORI (
	id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
	nume VARCHAR(100) NOT NULL,
	prenume VARCHAR(100) NOT NULL,
	email VARCHAR(255) NOT NULL
);
/
CREATE TABLE MATERII  (
	id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
	titlu VARCHAR(100) NOT NULL	
);
/
CREATE TABLE INSCRIERI (
	id_student BIGINT UNSIGNED NOT NULL,
	id_materie BIGINT UNSIGNED NOT NULL,
	FOREIGN KEY (id_materie) REFERENCES MATERII(id),
	FOREIGN KEY (id_student) REFERENCES STUDENTI(nr_matricol),
	PRIMARY KEY (id_student,id_materie)
);
/
CREATE TABLE DIDACTIC (
	id_materie BIGINT UNSIGNED NOT NULL,
	id_profesor BIGINT UNSIGNED NOT NULL,
	FOREIGN KEY (id_materie) REFERENCES MATERII(id),
	FOREIGN KEY (id_profesor) REFERENCES PROFESORI(id)
	PRIMARY KEY (id_profesor,id_materie) 
);
/
CREATE TABLE NOTE (
	id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
	id_materie BIGINT UNSIGNED NOT NULL,
	id_student BIGINT UNSIGNED NOT NULL,
	data_crearii TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	FOREIGN KEY (id_materie) REFERENCES MATERII(id),
	FOREIGN KEY (id_student) REFERENCES STUDENTI(nr_matricol)	
);
/
CREATE TABLE TEME(
	id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
	id_student BIGINT UNSIGNED NOT NULL,
	id_materie BIGINT UNSIGNED NOT NULL,
	nume_fisier VARCHAR(100) UNIQUE NOT NULL,
	data_incarcarii TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	FOREIGN KEY (id_materie) REFERENCES MATERII(id),
	FOREIGN KEY (id_student) REFERENCES STUDENTI(nr_matricol)	
);
/