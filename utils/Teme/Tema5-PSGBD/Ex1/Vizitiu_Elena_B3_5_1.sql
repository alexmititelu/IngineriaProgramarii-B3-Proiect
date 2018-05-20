drop table erasmus;
/
create table erasmus (
numar_matricol varchar2(6 byte),
num varchar2(15 byte),
pren varchar2(30 byte),
tara number(4,0)
);
/
create unique index index_nr ON  erasmus(numar_matricol);
/
set serveroutput on;
declare
  v_random studenti.id%type;
begin
  for i in 1..100 loop
    v_random:= TRUNC(dbms_random.value(1,1025));
    for v_linie in (select *from studenti where id=v_random) loop
      begin
        insert into erasmus values(v_linie.nr_matricol,v_linie.nume,v_linie.prenume,null);
        --dbms_output.put_line ('nu e exceptie');
        
        exception
          when DUP_VAL_ON_INDEX then
          dbms_output.put_line (v_linie.id||' e exceptie');
      end;
    end loop;
  end loop;
end;
