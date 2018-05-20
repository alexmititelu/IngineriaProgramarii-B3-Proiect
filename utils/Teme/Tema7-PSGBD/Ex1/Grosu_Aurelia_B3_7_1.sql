set serveroutput on;
drop table firma;
create table firma(
id int not null primary key,
denumire varchar2(30),
data_infiintare date
);
drop table angajat;
create table angajat(
id_angajat int not null primary key,
id_firma int not null,
nume varchar2(15 byte),
prenume varchar2(30 byte),
data_nastere date,
CONSTRAINT fk_angajat_id_firma FOREIGN KEY (id_firma) REFERENCES firma(id)
);
/
INSERT INTO firma VALUES(1,'Amazon',TO_DATE('09/05/1995', 'dd/mm/yyyy'));
INSERT INTO firma VALUES(2,'Continental',TO_DATE('21/07/1999', 'dd/mm/yyyy'));
INSERT INTO firma VALUES(3,'Centric',TO_DATE('06/04/2000', 'dd/mm/yyyy'));
INSERT INTO firma VALUES(4,'Gemini',TO_DATE('03/02/2012', 'dd/mm/yyyy'));
INSERT INTO firma VALUES(5,'Bitdefender',TO_DATE('25/01/2015', 'dd/mm/yyyy'));

INSERT INTO angajat VALUES(1,2,'Vizitiu','Elena',TO_DATE('09/05/1997', 'dd/mm/yyyy'));
INSERT INTO angajat VALUES(2,1,'Grosu','Mihaela',TO_DATE('29/07/1995', 'dd/mm/yyyy'));
INSERT INTO angajat VALUES(3,3,'Arcana','Delia',TO_DATE('24/02/1997', 'dd/mm/yyyy'));
INSERT INTO angajat VALUES(4,4,'Betiuc','Ciprian',TO_DATE('09/03/1994', 'dd/mm/yyyy'));
INSERT INTO angajat VALUES(5,5,'Proca','Teodor',TO_DATE('11/09/1997', 'dd/mm/yyyy'));
INSERT INTO angajat VALUES(6,2,'Ciobanu','Ionut',TO_DATE('02/08/1997', 'dd/mm/yyyy'));
INSERT INTO angajat VALUES(7,3,'Popescu','Maria',TO_DATE('05/01/1989', 'dd/mm/yyyy'));
INSERT INTO angajat VALUES(8,2,'Ionescu','Dan',TO_DATE('15/06/1997', 'dd/mm/yyyy'));
INSERT INTO angajat VALUES(9,4,'Iacob','Marius',TO_DATE('21/10/1996', 'dd/mm/yyyy'));
INSERT INTO angajat VALUES(10,1,'Cazacu','Carmen',TO_DATE('04/11/1994', 'dd/mm/yyyy'));
INSERT INTO angajat VALUES(11,4,'Cobuz','Camelia',TO_DATE('13/12/1993', 'dd/mm/yyyy'));
/
create or replace view firma_angajat as select *  from firma f full outer join angajat a on f.id=a.id_firma; 

CREATE OR REPLACE TRIGGER stergere_angajat
  INSTEAD OF delete ON firma_angajat
BEGIN
  dbms_output.put_line('Stergem pe angajatul:' || :OLD.nume||', din firma:'|| :OLD.denumire);
  delete from angajat where id_angajat=:OLD.id;
  delete from firma where id=:OLD.id;
END;
/
CREATE OR REPLACE TRIGGER inserare
INSTEAD OF INSERT ON firma_angajat
DECLARE
v_contor int;
BEGIN
  dbms_output.put_line('Introducem angajatul cu numele:' || :NEW.nume||' la firma: '||:NEW.denumire);
  select count(*) into v_contor from firma where id=:NEW.id;
  if(v_contor<=0)
   then
     INSERT into firma VALUES(:NEW.id,:NEW.denumire,:NEW.data_infiintare);
     dbms_output.put_line('Inseram firma cu denumirea:' || :NEW.denumire);
   end if;
  INSERT into angajat VALUES(:NEW.id_angajat,:NEW.id_firma,:NEW.nume,:NEW.prenume,:NEW.data_nastere);
END;
/
CREATE OR REPLACE TRIGGER actualizare
  INSTEAD OF update ON firma_angajat
BEGIN
   dbms_output.put_line('Actualizam pe angajatul cu numele:' || :NEW.prenume||' care lucreaza la '||:NEW.denumire);
   update firma set id=:NEW.id,denumire=:NEW.denumire,DATA_INFIINTARE=:NEW.data_infiintare where id=:old.id;
   update angajat set id_angajat=:NEW.id_angajat,id_firma=:NEW.id,nume=:NEW.nume,prenume=:NEW.prenume,DATA_NASTERE=:NEW.data_nastere where id_firma=:OLD.id;
END;
/
set serveroutput on;
select * from firma_angajat;
delete from angajat where id_angajat=5;
delete from firma where id=5;
delete from firma_angajat where id=5;
insert into firma values(7,'Levi',TO_DATE('21/07/2005', 'dd/mm/yyyy'));
insert into angajat values(12,7,'Todireanu','Corina',TO_DATE('12/06/1985', 'dd/mm/yyyy'));
insert into firma_angajat values(6,'Nine',TO_DATE('21/07/1995', 'dd/mm/yyyy'),14,6,'Vasile','Larisa',TO_DATE('30/03/2000','dd/mm/yyyy'));
update angajat set id_angajat=7,nume='Moraru',prenume='Maria',data_nastere=TO_DATE('05/01/1989','dd/mm/yyyy'),ID_firma=3 where nume='Popescu';
update firma set denumire='Endava' where denumire='Amazon';
update firma_angajat set nume='Marinescu' where nume='Popescu';