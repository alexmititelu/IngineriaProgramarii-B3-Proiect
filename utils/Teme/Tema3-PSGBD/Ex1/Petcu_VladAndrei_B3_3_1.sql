set serveroutput on;
create or replace package manager_facultate is
--  cursor lista_studenti is
  --  select nr_matricol, nume, prenume, grupa, an from studenti order by nume;
  procedure public_varsta_student(p_data_nastere date);
  procedure adauga_student(p_nume studenti.nume%type, p_prenume studenti.prenume%type, p_an studenti.an%type);
end manager_facultate;
/
create or replace package body manager_facultate is

  procedure varsta_student (p_data_nastere date) is
    v_numar_ani integer := 0;
    v_numar_luni integer := 0;
    v_numar_total_luni integer := 0;
    v_numar_zile integer := 0;
  begin
    v_numar_ani := floor((sysdate - p_data_nastere)/365);
    v_numar_total_luni := trunc(months_between(sysdate, p_data_nastere));
    v_numar_luni := v_numar_total_luni - (v_numar_ani * 12) ; 
    v_numar_zile := sysdate - add_months(p_data_nastere, v_numar_total_luni);
      dbms_output.put_line('Varsta student: ' || v_numar_ani || ' ani, ' || v_numar_luni || ' luni, ' || v_numar_zile || ' zile.');  
  end varsta_student;
  
  procedure public_varsta_student (p_data_nastere date)  is
  begin
  varsta_student(p_data_nastere);  
  end public_varsta_student;
  
  procedure adauga_student(p_nume studenti.nume%type, p_prenume studenti.prenume%type, p_an studenti.an%type) is
  begin
 
  null;
  
  end adauga_student;

end manager_facultate;

-- blocul in care apelez
/
declare 

    v_data_nastere studenti.data_nastere%type;
   

BEGIN

  select data_nastere into v_data_nastere 
  from studenti where id = 14;
  manager_facultate.public_varsta_student(v_data_nastere);
  
END;