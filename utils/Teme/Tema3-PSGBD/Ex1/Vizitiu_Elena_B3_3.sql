drop package manager_facultate;
set serveroutput on;
CREATE OR REPLACE PACKAGE manager_facultate IS
    v_nume studenti.nume%type;
    v_prenume studenti.prenume%type;
    v_nr_matricol studenti.nr_matricol%type;
    v_avg number;
    v_count int;
    v_nota note.valoare%type;
    v_curs cursuri.titlu_curs%type;
      --PROCEDURE adauga_student (nume studenti.nume%type, prenume studenti.prenume%type);
      procedure varsta(p_id in int);
      procedure sterge_student(p_id in studenti.id%type);
      --procedure afisare_informatii(p_idstud in studenti.id%type);
END manager_facultate;
/

CREATE OR REPLACE PACKAGE BODY manager_facultate IS
    procedure varsta(p_id in INT) is
    v_ani int;
    v_luni int;
    v_zile int;
    v_data_nastere studenti.data_nastere%type;
begin
   select data_nastere into v_data_nastere from studenti
    where id = p_id; 
    v_ani:=trunc((sysdate-v_data_nastere)/365);  
    v_luni:=months_between(sysdate, v_data_nastere);
    v_zile:=trunc(sysdate-add_months(v_data_nastere, v_luni));
    DBMS_OUTPUT.PUT_LINE('Numarul de ani trecuti: '||v_ani);
    DBMS_OUTPUT.PUT_LINE('Numarul de luni trecute: '||v_luni mod 12);
    DBMS_OUTPUT.PUT_LINE('Numarul de zile trecute: '||v_zile);
end;

    /*PROCEDURE adauga_student(nume studenti.nume%type, prenume studenti.prenume%type) 
       IS BEGIN
          DBMS_OUTPUT.PUT_LINE(nume||prenume);
    END adauga_student;*/
    
   procedure sterge_student(p_id in studenti.id%type) is
   begin
      delete from note
      where id_student=p_id;
      delete from prieteni
      where id_student1=p_id or id_student2=p_id;
      delete from studenti
      where id=p_id;
    end;
    /*
    procedure afisare_informatii(p_idstud in studenti.id%type) is
    begin
    DECLARE
   CURSOR lista_note  IS
       SELECT valoare from note;
       v_std_linie lista_note%rowtype;
  BEGIN
      OPEN lista_note;
      LOOP
          FETCH lista_note INTO v_std_linie;
          EXIT WHEN lista_note%NOTFOUND;
          DBMS_OUTPUT.PUT_LINE(v_std_linie.valoare);
      END LOOP;
      CLOSE lista_note;  
  END;
      select nume, prenume, nr_matricol into v_nume, v_prenume, v_nr_matricol
      from studenti where p_idstud=id;
      select avg(valoare) into v_avg
      from note where p_idstud=id_student;
      dbms_output.put_line(v_nume||' '||v_prenume||' '||v_nr_matricol||' '||v_avg);
      --manager_facultate.varsta(p_idstud);
      /*select count(nr_matricol) into v_count from studenti s1 join note n1 on s1.id=n1.id_student where exists(select 'x' from studenti s2 where 
      s1.an=s2.an and s1.grupa=s2.grupa and s1.nr_matricol!=s2.nr_matricol)
      group by s1.id
      having avg(valoare)>(select avg(valoare) from note where id_student=p_idstud);
      dbms_output.put_line(v_count);*/
      /*select valoare, titlu_curs into v_nota, v_curs from note n join cursuri c on n.id_curs=c.id
      where id_student=p_idstud;/*
      /*
    end;
    */
   
      
END manager_facultate;
/
begin
   manager_facultate.varsta(15);
   manager_facultate.sterge_student(11);
   --manager_facultate.afisare_informatii(4);
   --manager_facultate.varsta(4);
   
end;