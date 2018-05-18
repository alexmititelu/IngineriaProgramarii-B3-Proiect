create or replace package pkg_ex1 IS
   procedure eliminaStudent(p_id studenti.id%type);
   procedure afiseazaInformati(p_id studenti.id%type);
end pkg_ex1;


create or replace package body pkg_ex1 is

    procedure varstaStudent (p_varsta IN studenti.data_nastere%type )AS
         V_DATA_NASTERE VARCHAR(30) := to_char(p_varsta, 'DD-MM-YYYY');
         V_MONTHS INT ;
         V_DAY VARCHAR(30);
         V_NUMBER_DAYS INT;
         V_BIRTH_DATE DATE;
    begin
            SELECT TRUNC(MONTHS_BETWEEN (SYSDATE, TO_DATE(V_DATA_NASTERE ,'DD-MM-YYYY'))) INTO V_MONTHS
             FROM DUAL;
            V_DAY := TO_CHAR(TO_DATE(V_DATA_NASTERE, 'DD-MM-YYYY'),'DAY');
            SELECT ADD_MONTHS(TO_DATE(V_DATA_NASTERE ,'DD-MM-YYYY'), V_MONTHS) INTO V_BIRTH_DATE
            FROM DUAL;
          SELECT (SYSDATE - V_BIRTH_DATE ) INTO V_NUMBER_DAYS
          FROM DUAL;
          DBMS_OUTPUT.put_line('LUNILE DE PE 27.07.1997: '|| V_MONTHS);
           DBMS_OUTPUT.put_line('NUMARUL DE ZILE: '|| V_NUMBER_DAYS);
           DBMS_OUTPUT.put_line('ZIUA IN CARE S-A NASCUT: '||  V_DAY);
      end varstaStudent;
      
       procedure eliminaStudent(p_id IN studenti.id%type)as
         student_id studenti.id%type;
       begin
       student_id := p_id;
            DBMS_OUTPUT.put_line('Id-ul studentului: '||  student_id);
            delete from note
                where id_student = student_id;
            delete from prieteni
                where id_student1 = student_id;
            
            delete from prieteni
                where id_student2 = student_id;
                
            delete from studenti
                where id= student_id;
             
       end eliminaStudent;
       
       
        procedure afiseazaInformati(p_id IN studenti.id%type)AS
            v_data_nastere studenti.data_nastere%type;
            v_nrMatricol studenti.nr_matricol%type;
            v_nume studenti.nume%type;
            v_medie number;
            v_numeMaterie cursuri.titlu_curs%type;
            v_topStudent int;
            v_medie1 number;
        begin
            select data_nastere into v_data_nastere
            from studenti
            where id = p_id;
            
            select nr_matricol, nume into v_nrMatricol, v_nume
            from studenti
            where id = p_id;
             DBMS_OUTPUT.put_line('Am ajuns aici!s');
            for v_nota in (select * from note where id_student= p_id) loop
                    select titlu_curs into v_numeMaterie
                        from cursuri
                        where id = v_nota.id_curs;
            DBMS_OUTPUT.put_line(v_numeMaterie||' '|| v_nota.valoare);
                                        
            end loop;
            
            select avg(valoare) into v_medie
            from note 
            where id_student = p_id
            group by id_student;
            
            DBMS_OUTPUT.put_line('Media studentului '|| v_medie);            
         
            DBMS_OUTPUT.put_line('Prieteni: ');
            for v_prieten in (select *from prieteni where id_student1 = p_id) loop
                select nume into v_nume
                from studenti
                where id = v_prieten.id_student2;
                
                DBMS_OUTPUT.put_line('Nume prieten: '|| v_nume);
                
            end loop;
            
            DBMS_OUTPUT.put_line('Nr_matricol Nume '|| v_nrMatricol ||' '|| v_nume);
            
            varstaStudent(v_data_nastere);
            
        end afiseazaInformati;
      
    
    end pkg_ex1;



drop package ex1;




























set serveroutput on;

declare
varsta studenti.data_nastere%type;
id_student studenti.id%type := 2;
begin
     DBMS_OUTPUT.put_line('Id-ul studentului');
   
    pkg_ex1.eliminaStudent(3);
    
    
end;

