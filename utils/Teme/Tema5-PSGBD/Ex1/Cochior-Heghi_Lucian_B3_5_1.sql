drop table studentiErasmus;
/
create table studentiErasmus(
    nr_matricol varchar2(6),
    nume varchar2(15),
    prenume varchar2(30),
    idCountry number
);
/
-----------------------------------------------------

--1
create unique index  nrMatricol_idx
on studentiErasmus (nr_matricol);

drop index nrMatricol_idx;

--2

set serveroutput on;

declare
 v_randomId studenti.id%type;
 v_nrMatricol studenti.nr_matricol%type;
 v_nume studenti.nume%type;
 v_prenume studenti.prenume%type;
 
begin

    for i in 1..100 loop
        v_randomId := round(dbms_random.value()*1024) +1;
        
        select nr_matricol into v_nrMatricol
        from studenti
        where id = v_randomId;
        
        select nume into v_nume
        from studenti
        where id = v_randomId;
        
        select prenume into v_prenume
        from studenti
        where id = v_randomId;
        
        begin
        
        insert into studentiErasmus (nr_matricol, nume, prenume)
        values (v_nrMatricol, v_nume, v_prenume);
        
        DBMS_OUTPUT.put_line('Studentul : '||  v_nume ||' '||v_prenume ||' cu nrMatricol: '||v_nrMatricol||' a fost inserat cu succes.');
        
        exception
        when DUP_VAL_ON_INDEX then
            DBMS_OUTPUT.put_line('Studentul : '||  v_nume ||' '||v_prenume ||' cu nrMatricol: '||v_nrMatricol||' exisa deja in tabel--------------------------------');
            
        end;
        
        
        
    end loop;

end;