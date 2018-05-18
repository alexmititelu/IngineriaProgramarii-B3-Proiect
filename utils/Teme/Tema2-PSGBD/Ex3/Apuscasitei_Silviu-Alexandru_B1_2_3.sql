select T1.id, T1.nume, T1.prenume, T1.an, c.titlu_curs, n.valoare, T1."avg" from ( select * from (select  s.id, s.nume, s.prenume, s.an, 
avg(n.valoare) as "avg" from studenti s join note n on n.id_student=s.id group by s.id, s.nume, s.prenume, s.an, s.nume, s.prenume
having count(n.valoare) >= 3 order by avg(n.valoare) desc, s.an desc, s.nume asc, s.prenume asc ) where rownum = 1) T1 join note n on
n.id_student = T1.id join cursuri c on c.id = n.id_curs;

SET SERVEROUTPUT ON;
DECLARE
   CURSOR lista_studenti  IS
       SELECT id,nume,prenume,an FROM studenti
       order by nume asc;
   CURSOR lista_note  IS
       SELECT id,id_student,id_curs,valoare FROM note;
   CURSOR lista_cursuri  IS
       SELECT id,titlu_curs FROM cursuri;
   v_numar_de_note NUMBER := 0;
   v_suma_de_note NUMBER := 0;
   v_medie NUMBER := 0;
   v_medie_maxima NUMBER := 0;
   v_temporar_id_student NUMBER := 0;
   v_temporar_id_curs NUMBER := 0;
   v_temporar_an NUMBER := 0;
BEGIN
    FOR v_studenti_linie IN lista_studenti LOOP
      v_numar_de_note := 0;
      v_suma_de_note := 0;
      FOR v_note_linie IN lista_note LOOP  
        IF (v_studenti_linie.id = v_note_linie.id_student)
          THEN
            v_numar_de_note := v_numar_de_note + 1;
            v_suma_de_note := v_suma_de_note + v_note_linie.valoare;
          END IF;
      END LOOP;
        v_medie := (v_suma_de_note/v_numar_de_note); 
        IF (v_medie_maxima < v_medie) 
          THEN
            v_medie_maxima := v_medie;
            v_temporar_id_student := v_studenti_linie.id;
            v_temporar_an := v_studenti_linie.an;
          ELSE IF (v_medie_maxima = v_medie)
                  THEN 
                    IF (v_temporar_an < v_studenti_linie.an)
                      THEN
                        v_temporar_id_student := v_studenti_linie.id;
                        v_temporar_an := v_studenti_linie.an;
                    END IF;
                END IF;
          END IF;
    END LOOP; 
      FOR v_studenti_linie IN lista_studenti LOOP
        IF (v_studenti_linie.id = v_temporar_id_student)
          THEN
            DBMS_OUTPUT.PUT_LINE('Student: ' || v_studenti_linie.nume || ' ' || v_studenti_linie.prenume);
            DBMS_OUTPUT.PUT_LINE('media: ' || v_medie_maxima);       
          END IF;
      END LOOP;
      FOR v_note_linie IN lista_note LOOP
        IF (v_note_linie.id_student = v_temporar_id_student)
          THEN 
            v_temporar_id_curs := v_note_linie.id_curs;
            FOR v_cursuri_linie IN lista_cursuri LOOP
              IF (v_cursuri_linie.id = v_temporar_id_curs)
                THEN
                  DBMS_OUTPUT.PUT_LINE(v_cursuri_linie.titlu_curs || ': ' || v_note_linie.valoare);
                END IF;
            END LOOP;
          END IF;
      END LOOP;
END;