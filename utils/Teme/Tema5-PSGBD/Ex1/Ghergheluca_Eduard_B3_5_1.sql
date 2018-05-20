set serveroutput on;

drop table erasmus;
create table Erasmus (nr_matricol VARCHAR2(20), nume VARCHAR2(30), prenume VARCHAR2(30), id_destinatie INTEGER);
create unique index matricol_idx on Erasmus (nr_matricol);

DECLARE
idCpy float;
countryId pls_integer;
counter integer;
nr_matricolCpy varchar2(30);
numeCpy varchar2(30);
prenumeCpy varchar2(30);
BEGIN

for counter in 1..100 loop
        BEGIN
    select id into idCpy from (select* from studenti order by dbms_random.value) where rownum<=1;
    select nr_matricol, nume, prenume into nr_matricolCpy, numeCpy, prenumeCpy from studenti where id = idCpy;
    select dbms_random.value(1,5) into countryId from dual;
    insert into Erasmus(nr_matricol, nume, prenume, id_destinatie) values (nr_matricolCpy, numeCpy, prenumeCpy, countryId);
    dbms_output.put_line('The student with the id '||idCpy||' was SUCCESFULL inserted');
    exception
    when DUP_VAL_ON_INDEX then
    goto end_loop;
        END;
   <<end_loop>>
        dbms_output.put_line('Found a duplicate');
        dbms_output.put_line('The student with the id '||idCpy||' WAS NOT inserted');
        null;
end loop;

END;