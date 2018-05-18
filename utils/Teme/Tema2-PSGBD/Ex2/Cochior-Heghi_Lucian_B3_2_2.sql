set serveroutput on;

declare
cursor v_update_linie is select a,b from lab2tema for update of b NOWAIT;
v_unu int;
v_doi int;
v_trei int;
v_nrUpdate int := 0;
begin

    for v_linie in v_update_linie loop
        v_unu := 0;
        v_doi := 1;
        
        loop
            v_trei := v_unu + v_doi;
            if(v_trei = v_linie.a)then
                if(v_linie.b = 0)then
                    update lab2tema set b=1 where current of v_update_linie;
                    v_nrUpdate := v_nrUpdate + 1;
                end if;
                exit;
            elsif(v_trei > v_linie.a)then
                 if(v_linie.b = 1)then
                  v_nrUpdate := v_nrUpdate + 1;
                  update lab2tema set b=0 where current of v_update_linie;
                  end if;
                 exit;
            end if;
            v_unu := v_doi;
            v_doi := v_trei;
        end loop;
    end loop;
    DBMS_OUTPUT.put_line('Numar update: '|| v_nrUpdate);

end;