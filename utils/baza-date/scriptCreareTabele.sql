CREATE TABLE STUDENTI(
	id bigint unsigned not null auto_increment primary key,
	email varchar(255) not null unique,
	nume varchar(30),
	prenume varchar(60),
	nr_matricol VARCHAR(16) NOT NULL unique
);
/
CREATE TABLE CONTURI(
	id bigint unsigned not null primary key,
	email varchar(255) not null unique,
	username varchar(30)  NOT NULL unique,
	password varchar(64),
	permission int not null,
	FOREIGN KEY (email) REFERENCES STUDENTI(email)
);
/
CREATE TABLE CONTURI_CONECTATE(
	id bigint unsigned not null primary key,
	username varchar(30) NOT NULL,
	token varchar(64) NOT NULL unique,
	creation_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	FOREIGN KEY (username) references conturi(username)
);
/
CREATE TABLE REGISTER_LINKS(
	id bigint unsigned not null primary key,
	email varchar(255) NOT NULL,
	creation_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	token varchar(64) NOT NULL unique
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
	titlu VARCHAR(100) NOT NULL,
	an int not null,
	semestru int not null,
	descriere varchar(1000) not null	
);
/
CREATE TABLE INSCRIERI (
	id_cont bigint unsigned NOT NULL,
	id_materie BIGINT UNSIGNED NOT NULL,
	FOREIGN KEY (id_materie) REFERENCES MATERII(id),
	FOREIGN KEY (id_cont) REFERENCES CONTURI(id),
	PRIMARY KEY (id_cont,id_materie)
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
	id_cont bigint unsigned NOT NULL,
	data_notarii TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	FOREIGN KEY (id_materie) REFERENCES MATERII(id),
	FOREIGN KEY (id_cont) REFERENCES CONTURI(id)	
);
/
CREATE TABLE TEME(
	id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
	id_materie BIGINT UNSIGNED NOT NULL,
	deadline TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	enunt varchar(1000) NOT NULL,
	nr_exercitii int unsigned not null,
	nume_tema varchar(100) not null,
	FOREIGN KEY (id_materie) REFERENCES MATERII(id)	
);
/
CREATE TABLE teme_incarcate(
	id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
	id_cont bigint unsigned NOT NULL,
	id_tema BIGINT UNSIGNED NOT NULL,
	nr_exercitii int unsigned not null,
	data_incarcarii TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	FOREIGN KEY (id_tema) REFERENCES TEME(id),
	FOREIGN KEY (id_cont) REFERENCES CONTURI(id)	
);
/
CREATE TEMA_EXERCITIU_EXTENSIE(
	id bigint unsigned not null primary key,
	id_tema bigint unsigned not null,
	nr_exercitiu int unsigned,
	extensie_acceptata varchar(10) not null,
	FOREIGN KEY (id_tema) REFERENCES TEME(id)
);
/
CREATE TABLE COMENTARII_PROFESORI(
   id bigint unsigned not null primary key,
   id_tema_incarcata bigint unsigned not null,
   nr_exercitiu int unsigned,
   startrow bigint unsigned not null,
   endrow bigint unsigned not null,
   comentariu varchar(1000),
   FOREIGN KEY(id_tema_incarcata) references teme_incarcate(id)
);
/