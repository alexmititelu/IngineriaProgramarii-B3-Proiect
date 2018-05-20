
CREATE OR REPLACE PACKAGE tema4 IS
  TYPE student IS RECORD (id_stud NUMBER, procent_bursa NUMBER);
  TYPE lista_stud is varray(100) OF student;
  PROCEDURE bursa(lista_studenti lista_stud);
END tema4;
/
CREATE OR REPLACE PACKAGE BODY tema4 IS
  PROCEDURE bursa(lista_studenti lista_stud) AS
  bursa_actuala NUMBER;
  BEGIN 
    FOR i IN lista_studenti.FIRST..lista_studenti.LAST LOOP
        SELECT bursa INTO bursa_actuala from studenti WHERE lista_studenti(i).id_stud=id;
        IF (bursa_actuala IS NULL) THEN
          UPDATE studenti SET bursa=100 WHERE id=lista_studenti(i).id_stud;
          END IF;
    END LOOP;
    FOR i IN lista_studenti.FIRST..lista_studenti.LAST LOOP
        SELECT bursa INTO bursa_actuala from studenti WHERE lista_studenti(i).id_stud=id;
        UPDATE studenti SET bursa_modificata = lista_bursa(bursa,bursa-bursa*(lista_studenti(i).procent_bursa)/100,bursa-bursa*(lista_studenti(i).procent_bursa)/100-bursa*(lista_studenti(i).procent_bursa)/100) where id=lista_studenti(i).id_stud ;
        UPDATE studenti SET bursa=bursa+bursa*(lista_studenti(i).procent_bursa)/100 where id=lista_studenti(i).id_stud;
        DBMS_OUTPUT.PUT_LINE(lista_studenti(i).id_stud||' '||bursa_actuala);
    END LOOP;
    
  END bursa;
END tema4;
/

SET SERVEROUTPUT ON;
DECLARE
   v_student tema4.lista_stud:=tema4.lista_stud();  
BEGIN

  v_student.EXTEND;
  v_student(1).id_stud:=100;
  v_student(1).procent_bursa:=5;
  v_student.EXTEND;
  v_student(2).id_stud:=105;
  v_student(2).procent_bursa:=7;
  v_student.EXTEND;
  v_student(3).id_stud:=248;
  v_student(3).procent_bursa:=10;
  
  tema4.bursa(v_student);
  
END;

CREATE OR REPLACE TYPE lista_bursa IS TABLE OF NUMBER;

ALTER TABLE studenti ADD (bursa_modificata lista_bursa) NESTED TABLE bursa_modificata STORE AS burse;

UPDATE studenti SET bursa_modificata = NEW lista_bursa(); 