CREATE OR REPLACE PACKAGE manager_facultate IS
  PROCEDURE sterge_student (nr_matr studenti.nr_matricol%type);
END manager_facultate;

CREATE OR REPLACE PACKAGE BODY manager_facultate IS

    v_an NUMBER;

    FUNCTION calculeaza_varsta (p_nume studenti.nume%type, p_prenume studenti.prenume%type) RETURN INT AS
      v_data_nastere studenti.data_nastere%type;
      v_numar_de_luni NUMBER := 0;
    BEGIN
      SELECT data_nastere INTO v_data_nastere FROM STUDENTI WHERE nume like p_nume and prenume like p_prenume;
      DBMS_OUTPUT.PUT_LINE('Data nastere: '||v_data_nastere);
      SELECT FLOOR(MONTHS_BETWEEN(sysdate, v_data_nastere)) INTO v_numar_de_luni FROM DUAL;
      RETURN FLOOR(v_numar_de_luni/12);
    END calculeaza_varsta;   
    
    PROCEDURE sterge_student (nr_matr studenti.nr_matricol%type) IS 
      v_student_id studenti.id%type;
    BEGIN
      SELECT ID INTO v_student_id FROM studenti where NR_MATRICOL = nr_matr;
      DELETE FROM prieteni where (ID_STUDENT1 = v_student_id or ID_STUDENT2 = v_student_id);
      DELETE FROM note WHERE ID_STUDENT = v_student_id;
      DELETE FROM studenti WHERE NR_MATRICOL = nr_matr;
    END sterge_student;
    
END manager_facultate; 


create or replace package PKG as
  type t_numlist is table of number index by varchar2(50);
  procedure SomeProc(p_var in pkg.t_numlist);
end;
/

create or replace package body PKG as
  procedure someproc(p_var in pkg.t_numlist) is
  begin
    null;
  end;
end;
/

declare
  --type t_numlist is table of number index by varchar2(50);
  l_var pkg.t_numlist;
begin
  pkg.someproc(l_var);
end;
