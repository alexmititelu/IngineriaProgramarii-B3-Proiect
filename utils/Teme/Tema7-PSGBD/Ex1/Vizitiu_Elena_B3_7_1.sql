set serveroutput on;
drop table produse CASCADE constraints;
create table produse(
  id int not null primary key,
  nume varchar2(20) not null,
  pret int not null
);
/
drop table cumparatori CASCADE CONSTRAINTS;
create table cumparatori(
  id_cumparator int not null primary key,
  id_produs int not null,
  nume_cumparator varchar2(20) not null,
  prenume varchar2(20) not null,
  adresa varchar2(30),
  CONSTRAINT fk_cump_id FOREIGN KEY (id_produs) REFERENCES produse(id)
);
/

INSERT INTO produse VALUES(12, 'aspirator',300);
INSERT INTO produse VALUES(24,'laptop',1000);
INSERT INTO produse VALUES(39,'imprimanta',400);
INSERT INTO produse VALUES(26,'scaun',100);
INSERT INTO produse VALUES(10,'telefon',800);

INSERT INTO cumparatori VALUES(1,12, 'Grosu','Aurelia','Arsenie nr 24');
INSERT INTO cumparatori VALUES(2,24,'Vizitiu','Elena', 'Fierarului nr 25');
INSERT INTO cumparatori VALUES(3,39,'Proca','Teodor','Bucium nr 34');
INSERT INTO cumparatori VALUES(4,26,'Borcea','Malina', 'Socola nr 13');
INSERT INTO cumparatori VALUES(5,10,'Cochior','Lucian','Cucului nr 69');

CREATE or replace VIEW view_lab7 AS SELECT *FROM cumparatori c JOIN produse p on c.id_produs=p.id;

CREATE OR REPLACE TRIGGER insert_lab7
INSTEAD OF insert ON view_lab7
BEGIN
    INSERT into produse VALUES(:NEW.id,:NEW.nume,:NEW.pret);
    INSERT into cumparatori VALUES(:NEW.id_cumparator,:NEW.id_produs,:NEW.nume_cumparator,:NEW.prenume,:NEW.adresa);
     
END;
/

CREATE OR REPLACE TRIGGER update_lab7
  INSTEAD OF update ON view_lab7
BEGIN
   update produse set id=:NEW.id, nume=:NEW.nume,pret=:NEW.pret where id=:old.id;
   update cumparatori set id_cumparator=:NEW.id_cumparator,id_produs=:NEW.id_produs,nume_cumparator=:NEW.nume_cumparator,prenume=:NEW.prenume,adresa=:NEW.adresa where id_produs=:OLD.id;
END;
/

CREATE OR REPLACE TRIGGER delete_lab7
  INSTEAD OF delete ON view_lab7
BEGIN
  dbms_output.put_line('Stergem produsul cu id-ul:' || :OLD.id);
  delete from cumparatori where id_produs=:OLD.id;
  delete from produse where id=:OLD.id;
END delete_lab7;
/

INSERT INTO view_lab7(id_cumparator,id_produs,nume_cumparator,prenume,adresa,id,nume,pret)VALUES(19,45,'Betiuc','Claudiu','Sturza nr 22',45,'masa',200);
--UPDATE view_lab7 set nume='valiza' where id=12;
--DELETE from view_lab7 where id=12;