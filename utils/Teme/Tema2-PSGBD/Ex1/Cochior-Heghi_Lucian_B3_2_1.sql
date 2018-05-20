 drop table lab2tema;
 /
 CREATE TABLE lab2tema (a int,
                        b int
)
set serveroutput on;
DECLARE
v_contor int :=0;
v_aux integer;
v_suma int;
v_first int;
v_second int;
v_const constant int := 7;
v_nrDiv int;
v_d int;

BEGIN

   for v_contor in 1..10000 loop
    v_aux := v_contor;
    v_suma := 0;
    while (v_aux != 0) loop
        v_suma := v_suma + (v_aux mod 10);
        v_aux := trunc(v_aux / 10);
    end loop;
    
    
    if(v_suma mod 9 = v_const) then
        v_first := v_contor;

        v_aux:=0;
        v_d := 2;
        v_nrDiv :=0;
    
       loop
         if(v_contor mod v_d = 0) then
            v_nrDiv := v_nrDiv + 1;
         end if;
         
         if(v_d > round(v_contor/2) )then
            exit;
         end if;
         v_d:= v_d + 1;
         
       end loop;
       
       if(v_nrDiv = 0)then
        v_second := 1;
        else
            v_second := 0;
       end if;
       
    
     insert into lab2tema values(v_first, v_second);
     DBMS_OUTPUT.put_line('S-a inserat in tabel A: '||v_first||' B: '|| v_second);
    
    end if;   
   
   end loop;
   
   

END;

select *from lab2tema;