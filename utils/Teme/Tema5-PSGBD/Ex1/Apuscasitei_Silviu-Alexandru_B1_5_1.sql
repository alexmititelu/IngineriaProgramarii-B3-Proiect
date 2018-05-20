DROP TABLE erasmus CASCADE CONSTRAINTS
/

CREATE TABLE erasmus (
 nr_matricol varchar2(100),
 nume varchar2(100),
 prenume varchar2(100)
)
/

CREATE UNIQUE INDEX add_idx
  ON erasmus (nr_matricol);

 
SET SERVEROUTPUT ON;  
CREATE OR REPLACE PROCEDURE add_tabel IS
    n pls_integer;
    v_nr_matricol_student VARCHAR2(100);
    v_nume_student VARCHAR2(100);
    v_prenume_student VARCHAR2(100);
    v_nr_matricol_aux VARCHAR2(100);
    message_succes VARCHAR2(100) := 'Student inserat cu succes';
    message_failed VARCHAR2(100);
    
    CURSOR lista_studenti  IS
      SELECT * FROM studenti;
      
    CURSOR lista_erasmus IS
      SELECT *FROM erasmus;
      
begin
      n := dbms_random.value(1,1025);
        
      FOR v_linie IN lista_studenti LOOP
        IF (v_linie.id = n)
          THEN
            SELECT nr_matricol,nume,prenume INTO v_nr_matricol_student, v_nume_student, v_prenume_student FROM(
            SELECT nr_matricol,nume,prenume FROM studenti where id = v_linie.id
            order by nr_matricol desc) 
            where rownum <=1;
        END IF;
      END LOOP;
      
    insert into erasmus values(v_nr_matricol_student, v_nume_student, v_prenume_student);
    COMMIT;
EXCEPTION
WHEN DUP_VAL_ON_INDEX THEN
    message_failed := v_nume_student || ' ' || v_prenume_student;
    DBMS_OUTPUT.PUT_LINE(message_failed);
END add_tabel;


DECLARE
  nr NUMBER := 0;
BEGIN
  while(nr < 100) LOOP
    add_tabel();
    nr := nr + 1;
  END LOOP;
END;

select *from erasmus;

