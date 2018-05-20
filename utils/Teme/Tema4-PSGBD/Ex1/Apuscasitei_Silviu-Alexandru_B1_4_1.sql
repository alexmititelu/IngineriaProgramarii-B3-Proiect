CREATE OR REPLACE TYPE for_update AS varray(100) OF NUMBER(10);
/
ALTER TABLE studenti
  ADD history_bursa for_update default null; 
  
CREATE OR REPLACE PACKAGE update_tables AS
  TYPE MyRecord IS RECORD(id_student NUMBER, procentaj_bursa NUMBER);
  TYPE MyVarray IS VARRAY(5) OF MyRecord;
  PROCEDURE update_bursa(p_my_vector IN update_tables.MyVarray);
  PROCEDURE afis_lista;
END update_tables;

CREATE OR REPLACE PACKAGE BODY update_tables AS
    v_aux NUMBER := 0;
    v_id_aux for_update := for_update();
    
  PROCEDURE update_bursa(p_my_vector IN update_tables.MyVarray) IS
    CURSOR lista_studenti IS
        SELECT * FROM studenti FOR UPDATE OF bursa,history_bursa NOWAIT;
    BEGIN
      FOR v_linie IN lista_studenti LOOP
        FOR i IN p_my_vector.FIRST..p_my_vector.LAST LOOP
         v_aux := 0;
          IF (v_linie.id = p_my_vector(i).id_student) 
            THEN
              IF (v_linie.bursa is null)
                THEN
                  v_aux := 100 + 100*p_my_vector(i).procentaj_bursa/100;
                ELSE
                  v_aux := v_linie.bursa + p_my_vector(i).procentaj_bursa*v_linie.bursa/100;
              END IF;
              
              IF (v_linie.history_bursa is null)
                THEN
                  v_id_aux.EXTEND(1);
                  v_id_aux(1) := '100';
                  UPDATE studenti SET history_bursa = v_id_aux WHERE CURRENT OF lista_studenti;
                  v_id_aux.EXTEND(1);
                  v_id_aux(2) := v_aux;
                ELSE
                  v_id_aux.EXTEND(v_linie.history_bursa.COUNT);
                  FOR i IN v_linie.history_bursa.FIRST..v_linie.history_bursa.LAST LOOP
                    v_id_aux(i) := v_linie.history_bursa(i);
                  END LOOP; 
                  v_id_aux.EXTEND(1);
                  FOR i IN v_id_aux.FIRST..v_id_aux.LAST LOOP
                    IF (i = v_id_aux.LAST)
                      THEN
                        v_id_aux(i) := v_aux;
                    END IF;
                  END LOOP; 
              END IF;
              
              UPDATE studenti SET history_bursa = v_id_aux WHERE CURRENT OF lista_studenti;
              UPDATE studenti SET bursa = v_aux WHERE CURRENT OF lista_studenti;
              v_id_aux.DELETE;
          END IF;
        END LOOP; 
      END LOOP;
    END update_bursa;
    
    PROCEDURE afis_lista IS 
      CURSOR lista_studenti  IS
        SELECT * FROM studenti;
      v_std_linie lista_studenti%ROWTYPE; 
    BEGIN
      FOR v_linie IN lista_studenti LOOP    
        IF (v_linie.history_bursa is not null)
          THEN
            v_id_aux := v_linie.history_bursa;
            IF (v_id_aux(1) is not null)
             THEN
              FOR i IN v_id_aux.FIRST..v_id_aux.LAST LOOP
                DBMS_OUTPUT.PUT_LINE(v_linie.id||' '||v_linie.nume||' '||v_linie.prenume||' '|| v_id_aux(i));
              END LOOP;
            END IF;
          DBMS_OUTPUT.PUT_LINE(' ');
        END IF;
        v_id_aux.DELETE;
      END LOOP; 
    END afis_lista;

END update_tables;

select *from studenti where history_bursa is null;
select *from studenti where id = 144;

SELECT t1.id, t1.nr_matricol, t1.nume, t1.prenume, t1.an, t1.grupa, t1.bursa, t1.data_nastere, t2.column_value as history_bursa
   FROM studenti t1, TABLE(t1.HISTORY_BURSA) t2
   where id = 258;


SET SERVEROUTPUT ON;
DECLARE
  v_my_vector update_tables.MyVarray;
BEGIN
  v_my_vector := update_tables.MyVarray();
  
  v_my_vector.EXTEND(5);
  v_my_vector(1).id_student := '27';
  v_my_vector(1).procentaj_bursa := '2';
  v_my_vector(2).id_student := '124';
  v_my_vector(2).procentaj_bursa := '5';
  v_my_vector(3).id_student := '9';
  v_my_vector(3).procentaj_bursa := '2';
  v_my_vector(4).id_student := '112';
  v_my_vector(4).procentaj_bursa := '10';
  v_my_vector(5).id_student := '103';
  v_my_vector(5).procentaj_bursa := '1';
  
  UPDATE_TABLES.UPDATE_BURSA(v_my_vector);
  UPDATE_TABLES.AFIS_LISTA();
END;
