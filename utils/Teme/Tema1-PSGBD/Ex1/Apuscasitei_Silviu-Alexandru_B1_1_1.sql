set serveroutput on;
DECLARE
   v_nume_student varchar2(20) := &i_nume;
   v_valoare_nume NUMBER;
BEGIN
   DBMS_OUTPUT.PUT_LINE('Prenume: '|| v_nume_student);
   SELECT count(*) INTO v_valoare_nume FROM STUDENTI WHERE nume like v_nume_student;
   IF (v_valoare_nume = 0) 
      THEN 
        DBMS_OUTPUT.PUT_LINE('Nu exista studenti cu numele '|| v_nume_student);
      ELSE 
        DBMS_OUTPUT.PUT_LINE('- Numarul de studenti cu numele '||v_nume_student||' este: '|| v_valoare_nume);
        DECLARE 
           v_id_student STUDENTI.ID%type;
           v_prenume_student STUDENTI.PRENUME%type;
        BEGIN
           SELECT prenume_student,id_student
             INTO v_prenume_student,v_id_student
             FROM(
                 SELECT prenume as prenume_student, id as id_student FROM studenti
                 where nume like v_nume_student
                 order by prenume asc)
                 where rownum = 1;
           DBMS_OUTPUT.PUT_LINE('- ID-ul studentului este: '|| v_id_student || ' si prenumele este: '|| v_prenume_student);
           DECLARE 
              v_valoare_maxima_student note.valoare%type;
              v_valoare_minima_student note.valoare%type;
           BEGIN
              SELECT max(valoare),min(valoare) INTO v_valoare_maxima_student,v_valoare_minima_student FROM note n join studenti s on n.id = s.id
              where nume like v_nume_student and prenume like v_prenume_student
              group by n.id;
              DBMS_OUTPUT.PUT_LINE('- Nota maxima a studentului este: '|| v_valoare_maxima_student || ' si nota minima este: '|| v_valoare_minima_student);
              DBMS_OUTPUT.PUT_LINE('- Nota A la puterea B: '|| POWER(v_valoare_maxima_student, v_valoare_minima_student));
           END;
        END;
   END IF;
END;