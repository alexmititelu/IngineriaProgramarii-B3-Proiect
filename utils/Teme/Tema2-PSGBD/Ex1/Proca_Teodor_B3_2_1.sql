drop table Ex1;
create table Ex1(A number(10), B number(10));

set serveroutput on;

declare 

    number_A Ex1.A%type;
    iterator int := 0;
    suma_cifre int :=0;
    aux int := 0;
    
    prim_iterator int;
    prim int := 1;
    
    numar_script int := 7;

begin
    for iterator in 0..10000 loop
        aux := iterator;
        suma_cifre := 0;
        while (aux > 0) loop
            suma_cifre := suma_cifre + aux mod 10;
            aux := floor(aux / 10);
        end loop;
        
        prim := 1;
        
        for prim_iterator in 2..iterator/2 loop
            if (iterator mod prim_iterator = 0)
            then
                prim := 0;
            end if;
        end loop;
        
        if(suma_cifre mod 9 = numar_script)
        then
            INSERT INTO Ex1 VALUES (iterator, prim);
        end if;
    end loop;
    
    
end;