set serveroutput on;
/*CREATE TYPE history_burse is varray(5) of number(5); 
alter table studenti add istoric_burse  history_burse default  history_burse();*/
create or replace package sgbd4
is
type obj is record(id integer, procent integer);
type object_list is varray(5) of obj;

procedure tema4(v_obj_table object_list);
--procedure afisare_modif;
end sgbd4;
/

create or replace package body sgbd4
is
v_obj_list object_list;
v_obj obj;


procedure tema4(v_obj_table object_list) is
valoare_bursa studenti.bursa%TYPE;
bursa_precedenta history_burse;

begin
bursa_precedenta:=history_burse();
FOR i IN v_obj_table.FIRST..v_obj_table.LAST LOOP


     select bursa into valoare_bursa from studenti where id=v_obj_table(i).id;
     select istoric_burse into bursa_precedenta from studenti where id=v_obj_table(i).id;
     if(valoare_bursa is null)
     then 
       UPDATE STUDENTI set bursa=100 WHERE id=v_obj_table(i).id;
      else
        UPDATE STUDENTI set bursa=bursa+bursa*v_obj_table(i).procent/100 WHERE id=v_obj_table(i).id;

      end if;
     bursa_precedenta.extend;
     bursa_precedenta(bursa_precedenta.last):=valoare_bursa;
     
     /*if (valoare_bursa is null) then
      update studenti set istoric_burse=0 WHERE id=v_obj_table(i).id;
    else */
    update studenti set istoric_burse=bursa_precedenta WHERE id=v_obj_table(i).id;
     --end if;
    end loop;
end tema4; 
end sgbd4;
/

