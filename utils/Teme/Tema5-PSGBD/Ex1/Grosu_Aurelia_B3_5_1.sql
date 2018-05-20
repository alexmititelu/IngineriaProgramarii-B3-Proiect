DROP TABLE ERASMUS CASCADE CONSTRAINTS;
/

CREATE TABLE ERASMUS (
numar_matricol VARCHAR2(6) NOT NULL PRIMARY KEY,
nume VARCHAR2(15) NOT NULL,
prenume VARCHAR2(30) NOT NULL,
id_tara NUMBER(38,0) NOT NULL
)
/
commit;
/
SET SERVEROUTPUT ON;
DROP INDEX nr_matricol;
/
 CREATE UNIQUE INDEX nr_matricol ON ERASMUS(numar_matricol);
 
INSERT INTO ERASMUS(numar_matricol,nume,prenume,id_tara) VALUES ('333HS2','Grosu','Aurelia','1');
INSERT INTO ERASMUS(numar_matricol,nume,prenume,id_tara) VALUES ('334HS2','Arcana','Delia','2');
INSERT INTO ERASMUS(numar_matricol,nume,prenume,id_tara) VALUES ('335HS2','Vizitiu','Elena','1');
INSERT INTO ERASMUS(numar_matricol,nume,prenume,id_tara) VALUES ('336HS2','Proca','Teodor','2');
INSERT INTO ERASMUS(numar_matricol,nume,prenume,id_tara) VALUES ('337HS2','Ignat','Cristian','2');
INSERT INTO ERASMUS(numar_matricol,nume,prenume,id_tara) VALUES ('338HS2','Ardeleanu','Angel','1');
INSERT INTO ERASMUS(numar_matricol,nume,prenume,id_tara) VALUES ('339HS2','Popescu','Ion','1');
INSERT INTO ERASMUS(numar_matricol,nume,prenume,id_tara) VALUES ('340HS2','Colesniuc','Constantin','1');
INSERT INTO ERASMUS(numar_matricol,nume,prenume,id_tara) VALUES ('341HS2','Ionescu','Andra','1');
INSERT INTO ERASMUS(numar_matricol,nume,prenume,id_tara) VALUES ('342HS2','Iosub','Irina Adina','1');
INSERT INTO ERASMUS(numar_matricol,nume,prenume,id_tara) VALUES ('343HS2','Cimpoesu','Theodor Petru','1');
INSERT INTO ERASMUS(numar_matricol,nume,prenume,id_tara) VALUES ('344HS2','Bradea','Malina Teodora','2');
INSERT INTO ERASMUS(numar_matricol,nume,prenume,id_tara) VALUES ('345HS2','Chmilevski','Laura','1');
INSERT INTO ERASMUS(numar_matricol,nume,prenume,id_tara) VALUES ('346HS2','Baciu','Laura','1');
INSERT INTO ERASMUS(numar_matricol,nume,prenume,id_tara) VALUES ('347HS2','Glodeanu','Iulia Xena','2');
INSERT INTO ERASMUS(numar_matricol,nume,prenume,id_tara) VALUES ('348HS2','Frunza','Mirela','1');
INSERT INTO ERASMUS(numar_matricol,nume,prenume,id_tara) VALUES ('349HS2','Maftei','Nicusor','1');
INSERT INTO ERASMUS(numar_matricol,nume,prenume,id_tara) VALUES ('350HS2','Gavriliuc','Mihai','1');
INSERT INTO ERASMUS(numar_matricol,nume,prenume,id_tara) VALUES ('351HS2','Tesu','Simona','2');
INSERT INTO ERASMUS(numar_matricol,nume,prenume,id_tara) VALUES ('352HS2','Coca','Ana','2');
INSERT INTO ERASMUS(numar_matricol,nume,prenume,id_tara) VALUES ('353HS2','Asaftei','Stefana','1');
INSERT INTO ERASMUS(numar_matricol,nume,prenume,id_tara) VALUES ('354HS2','Jitariuc','Vladimir','2');
INSERT INTO ERASMUS(numar_matricol,nume,prenume,id_tara) VALUES ('355HS2','Schifirnet','Iustin','2');
INSERT INTO ERASMUS(numar_matricol,nume,prenume,id_tara) VALUES ('356HS2','Bulai','Alexandra','1');
INSERT INTO ERASMUS(numar_matricol,nume,prenume,id_tara) VALUES ('357HS2','Bradea','Paul','1');


--COPY FROM STUDENTI TO ERASMUS(numar_matricol,nume,prenume,id_tara) INSERT ERASMUS(numar_matricol,nume,prenume,id_tara) USING SELECT*FROM(SELECT nr_matricol,nume,prenume from STUDENTI) WHERE ROWNUM<101;