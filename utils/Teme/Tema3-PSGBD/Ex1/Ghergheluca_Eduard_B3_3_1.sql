set serveroutput on;

create or replace package faculty as
PROCEDURE birth_call (id integer);
PROCEDURE delete_call (id integer);
PROCEDURE stud_info_call (id integer);
PROCEDURE add_student_call(x studenti.nume%TYPE, y studenti.prenume%TYPE );
end faculty;
/
create or replace package body faculty as
    PROCEDURE student_birth(idStud IN integer) is
        years integer;
        birthDateCpy date;
        monthsBetween integer;
        today date;
        days integer;
        months number(38);
        BEGIN 
            select data_nastere into birthDateCpy from studenti where id = idStud;
            select extract(year from sysdate) - extract(year from birthDateCpy) into years from dual;
            select months_between(sysdate,birthDateCpy ) into monthsBetween from dual;
            select add_months(birthDateCpy, monthsBetween) into today from dual;
            select abs(sysdate-today) into days from dual;
            select mod(monthsBetween,12) into months from dual;
            dbms_output.put_line('ani: ' || years);
            dbms_output.put_line( 'luni: '||months);
            dbms_output.put_line('zile '||days);
            dbms_output.put_line('');
    END student_birth;
    
    PROCEDURE delete_student(idStud integer) is
        BEGIN
        delete from prieteni where id_student1 = idStud or id_student2 = idStud;
        dbms_output.put_line('prietenia a fost eliminata');
        delete from note where id_student = idStud;
        dbms_output.put_line('notele au fost sterse');
        delete from studenti where id = idStud;
        dbms_output.put_line('studentul a fost sters');
        dbms_output.put_line(' ');
    END delete_student;
   
    PROCEDURE stud_info (idStud integer) is
        matricol studenti.nr_matricol%TYPE;
        media float;
        counter integer:=0;
        grupaCpy studenti.grupa%type;
        anCpy integer;
        BEGIN
            select nr_matricol into matricol from studenti where id = idStud;
            dbms_output.put_line('Studentul cu numarul matricol '|| matricol||' are urmatoarele note');
            for v_cursor in (select* from note n join cursuri c on n.id_curs = c.id where id_student = idStud) loop
                    dbms_output.put_line('nota '||v_cursor.valoare||' la materia ' ||v_cursor.titlu_curs);
            end loop;
            dbms_output.put_line('');
            select avg(valoare) into media from note where id_student = idStud;
                dbms_output.put_line('media studentului este '||media);
                dbms_output.put_line('');
        
            dbms_output.put_line('Varsta studentului este');
            student_birth(idStud);
            dbms_output.put_line('');
            select grupa into grupaCpy from studenti where id = idStud;
            select an into anCpy from studenti where id = idStud;
            for v_cursor4 in (select* from( select id_student, avg(valoare) from note n join studenti s on n.id_student = s.id where grupa = grupaCpy and an = anCpy group by id_student) order by 2 desc) loop
              counter:=counter+1;
              exit when v_cursor4.id_student = idStud;
           end loop;
            dbms_output.put_line('studentul este al '||counter||'-lea pe grupa');
            dbms_output.put_line(' ');
            
            
            dbms_output.put_line('prietenii studentului sunt: ');
            for v_cursor2 in (select * from prieteni where id_student1 = idStud) loop
                dbms_output.put_line(v_cursor2.id_student2);
            end loop;
            for v_cursor3 in (select* from prieteni where id_student2 = idStud) loop
                dbms_output.put_line(v_cursor3.id_student1);
            end loop;
    END stud_info;
    
    PROCEDURE add_student(names studenti.nume%TYPE, surnames studenti.prenume%TYPE) is
        id_stud studenti.id%type;
        numar_matricol studenti.nr_matricol%TYPE;
        cifre integer;
        cifra integer;
        litere varchar(3);
        concat1 varchar(10);
        concat2 varchar(10);
        anRand integer;
        literaGrupa varchar(2);
        cifraGrupa integer;
        grupa_final varchar(3);
        zi_nastere date;
        id_nota integer;
        nota integer;
        data_nota date;
        
        BEGIN
            select dbms_random.value(1025,1500) into id_stud from dual;
            select dbms_random.value(100,999) into cifre from dual;
            select dbms_random.string('u',2) into litere from dual;
            select concat(to_char(cifre), litere) into concat1  from dual;
            select dbms_random.value(0,9) into cifra from dual;
            select concat(concat1, to_char(cifra)) into concat2 from dual;
            select dbms_random.value(1,3) into anRand from dual;
            loop
                select dbms_random.string('u',1) into literaGrupa from dual;
                exit when literaGrupa = 'A' or literaGrupa = 'B';
            end loop;
            select dbms_random.value(1,6) into cifraGrupa from dual;
            select concat(literaGrupa, to_char(cifraGrupa)) into grupa_final from dual;
            select to_date(trunc(DBMS_RANDOM.VALUE(to_char (DATE '1970-01-01','J'),to_char(DATE '1997-12-31','J'))),'J') into zi_nastere from dual;
            dbms_output.put_line('numarul matricol este: '||concat2||' ,iar id-ul este '||id_stud|| ' anul este: '|| anRand||' grupa este '||grupa_final);
            insert into studenti (id, nr_matricol, nume, prenume, an, grupa, data_nastere) values (id_stud, concat2, names,surnames,anRand,grupa_final,zi_nastere);
            dbms_output.put_line('studentul a fost adaugat cu succes');
            if (anRand = 1) then
             for parcurgere in (select id from cursuri where an=1) loop
                select to_date(trunc(DBMS_RANDOM.VALUE(to_char (DATE '2015-01-01','J'),to_char(DATE '2017-12-31','J'))),'J') into data_nota from dual;
                select dbms_random.value(16500, 20000) into id_nota from dual;
                select dbms_random.value(4,10) into nota from dual;
                insert into note(id,id_student,id_curs, valoare,data_notare) values (id_nota,id_stud,parcurgere.id, nota,data_nota );
             end loop;
            end if;
            
            if (anRand = 2) then
             for parcurgere in (select id from cursuri where an=1) loop
                select to_date(trunc(DBMS_RANDOM.VALUE(to_char (DATE '2015-01-01','J'),to_char(DATE '2017-12-31','J'))),'J') into data_nota from dual;
                select dbms_random.value(16500, 20000) into id_nota from dual;
                select dbms_random.value(4,10) into nota from dual;
                insert into note(id,id_student,id_curs, valoare,data_notare) values (id_nota,id_stud,parcurgere.id, nota,data_nota );
             end loop;
            end if;
            
            if (anRand = 3) then
             for parcurgere in (select id from cursuri where an<3) loop
                select to_date(trunc(DBMS_RANDOM.VALUE(to_char (DATE '2015-01-01','J'),to_char(DATE '2017-12-31','J'))),'J') into data_nota from dual;
                select dbms_random.value(16500, 20000) into id_nota from dual;
                select dbms_random.value(4,10) into nota from dual;
                insert into note(id,id_student,id_curs, valoare,data_notare) values (id_nota,id_stud,parcurgere.id, nota,data_nota );
             end loop;
            end if;
           dbms_output.put_line('studentul a fost adaugat in tabela note');
            
        END add_student;
        
    PROCEDURE birth_call (id integer) is 
        BEGIN 
            student_birth(id);
        END birth_call;
    
    PROCEDURE delete_call (id integer) is
        BEGIN
            delete_student(id);
        END delete_call;
    
    PROCEDURE stud_info_call (id integer) is
        BEGIN
            stud_info(id);
        END stud_info_call;
        
    PROCEDURE add_student_call(x studenti.nume%TYPE, y studenti.prenume%TYPE ) is
        BEGIN
            add_student(x,y);
        END add_student_call;
    end faculty;
    /

BEGIN
--faculty.birth_call(7);
--select * from studenti where id = 34;
--faculty.delete_call(34);
--faculty.stud_info_call(72);
faculty.add_student_call('gigel', 'frone');
select* from note where id_student = 1106;
--select* from studenti where nume = 'gigel';

END;