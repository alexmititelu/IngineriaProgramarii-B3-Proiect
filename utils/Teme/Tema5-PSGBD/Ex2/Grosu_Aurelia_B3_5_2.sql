
CREATE OR REPLACE PACKAGE pb2_bursa IS
  TYPE student IS RECORD (id_stud NUMBER, procent_bursa NUMBER);
  TYPE lista_stud is varray(100) OF student;
  PROCEDURE majorare_bursa(lista_studenti lista_stud);
END pb2_bursa;
/
CREATE OR REPLACE PACKAGE BODY pb2_bursa IS
  PROCEDURE majorare_bursa(lista_studenti lista_stud) AS
  bursa_actuala NUMBER;
  BEGIN 
    FOR i IN lista_studenti.FIRST..lista_studenti.LAST LOOP
        SELECT bursa INTO bursa_actuala from studenti WHERE lista_studenti(i).id_stud=id;
        IF (bursa_actuala IS NULL) THEN
          DBMS_OUTPUT.PUT_LINE(lista_studenti(i).id_stud||' Studentul are bursa NULL.');
          ELSE
          UPDATE studenti SET bursa=bursa+bursa*(lista_studenti(i).procent_bursa)/100 where id=lista_studenti(i).id_stud;
          DBMS_OUTPUT.PUT_LINE(lista_studenti(i).id_stud||' Studentul are bursa de '||bursa_actuala||' lei.');
          END IF;
    END LOOP;
    FOR i IN lista_studenti.FIRST..lista_studenti.LAST LOOP
        SELECT bursa INTO bursa_actuala from studenti WHERE lista_studenti(i).id_stud=id;
        IF (bursa_actuala IS NULL) THEN
          DBMS_OUTPUT.PUT_LINE(lista_studenti(i).id_stud||' Studentul are bursa NULL.');
          ELSE
          IF(bursa_actuala>3000) THEN 
          UPDATE studenti SET bursa=trunc(bursa,3000) where id=lista_studenti(i).id_stud;
          DBMS_OUTPUT.PUT_LINE(lista_studenti(i).id_stud||' Studentul are bursa de '||bursa_actuala||' lei.');
          END IF;
        END IF;
    END LOOP;
    
  END majorare_bursa;
END pb2_bursa;
/

SET SERVEROUTPUT ON;
DECLARE
   v_student pb2_bursa.lista_stud:=pb2_bursa.lista_stud();  
BEGIN

  v_student.EXTEND;
  v_student(1).id_stud:=340;
  v_student(1).procent_bursa:=5;
  v_student.EXTEND;
  v_student(2).id_stud:=123;
  v_student(2).procent_bursa:=5; 
  
  pb2_bursa.majorare_bursa(v_student);
  
END;