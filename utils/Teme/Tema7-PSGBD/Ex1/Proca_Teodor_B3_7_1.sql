set serveroutput on;

DROP VIEW view_join;
DROP TABLE masini;
DROP TABLE proprietari;
CREATE TABLE proprietari ( id INT NOT NULL PRIMARY KEY, nume VARCHAR2(30) NOT NULL, prenume VARCHAR2(30));
INSERT INTO proprietari VALUES(1,'Popescu','Damian');
INSERT INTO proprietari VALUES(2,'Ionescu','Eugen');
INSERT INTO proprietari VALUES(3,'Constantin','Florin');
INSERT INTO proprietari VALUES(4,'Panaite','Daniel');
INSERT INTO proprietari VALUES(5,'Popa','Virgil');

CREATE TABLE masini (id_masina INT NOT NULL PRIMARY KEY, firma VARCHAR2(30) NOT NULL, id_proprietar INT NOT NULL,
CONSTRAINT fk_fructe_id_proprietar FOREIGN KEY (id_proprietar) REFERENCES proprietari(id)
);
INSERT INTO masini VALUES(1,'Ford',1);
INSERT INTO masini VALUES(2,'Ferrari',2);
INSERT INTO masini VALUES(3,'Audi',3);
INSERT INTO masini VALUES(4,'BMW',2);
INSERT INTO masini VALUES(5,'Jaguar',4);


CREATE or replace VIEW view_join AS SELECT masini.id_masina, masini.firma, proprietari.id, proprietari.nume, proprietari.prenume FROM masini FULL OUTER JOIN proprietari on 
masini.id_proprietar=proprietari.id;

CREATE OR REPLACE TRIGGER delete_proprietar
  INSTEAD OF delete ON view_join
BEGIN
  delete from masini where id_proprietar=:OLD.id;
  delete from proprietari where id=:OLD.id;
END delete_proprietar;
/

CREATE OR REPLACE TRIGGER update_view_join
  INSTEAD OF update ON view_join
BEGIN
   update proprietari set id=:NEW.id,nume=:NEW.nume,prenume=:NEW.prenume where id=:old.id;
   update masini set id_masina=:NEW.id_masina,firma=:NEW.firma,id_proprietar=:NEW.id where id_proprietar=:OLD.id;
END;
/

CREATE OR REPLACE TRIGGER insert_view_join
  INSTEAD OF insert ON view_join
DECLARE
v_nr INT;
BEGIN
  select count(*) into v_nr from proprietari where id=:NEW.id;
  if(v_nr<1)
   then
     INSERT into proprietari VALUES(:NEW.id,:NEW.nume,:NEW.prenume);
   end if;
  INSERT into masini VALUES(:NEW.id_masina,:NEW.firma,:NEW.id);
END;
/

--delete from view_join where id='3';
--select * from view_join;
--select * from masini;
--select * from proprietari;
--update view_join set id_masina=1,firma='Ford',id=1,nume='Gavril',prenume='Damian' where nume='Popescu';
--insert into view_join values(10,'Dacia',8,'Rusu','Vlad');