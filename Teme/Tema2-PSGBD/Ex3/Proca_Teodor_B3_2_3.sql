set serveroutput on;

declare 
   cursor update_B is
      select * from Ex1 for update of B nowait;
      
    count_change int := 0;
    
    elem_1 int := 0;
    elem_2 int := 1;
    elem_curr int := 1;
    
    ok int := 1;
begin


   for linie_curenta in update_B loop
            elem_1 := 0;
            elem_2 := 1;
            elem_curr := 1;
            
            while (elem_curr <= linie_curenta.A) loop
                elem_1 := elem_2;
                elem_2 := elem_curr;
                elem_curr := elem_1 + elem_2;
                
                if (linie_curenta.A = elem_curr)
                then 
                    if (linie_curenta.B = 0)
                    then
                        update Ex1 set B = 1 where current of update_B;
                        count_change := count_change + 1;
                        ok := 0;
                    else
                        ok := 0;
                    end if;
                end if;
            end loop;
             if (linie_curenta.B = 1 and ok = 1)
                    then
                    
                        update Ex1 set B = 0 where current of update_B;
                        count_change := count_change + 1;
                        ok := 0;
                    end if;
        ok := 1;
   end loop;
   
  DBMS_OUTPUT.PUT_LINE('Elemente schimbate: ' || count_change);
end;