set serveroutput on;

declare
  v_id number := 1;
  v_numar number := 1000;
  procedure majoreaza_bursa(p_id studenti.id%type, p_numar number) is
    bursa_prea_mare exception;
    v_bursa_initiala studenti.bursa%type;
    v_bursa_majorata studenti.bursa%type;
  begin
    select bursa into v_bursa_initiala from studenti where id= p_id; 
    v_bursa_majorata := v_bursa_initiala + p_numar;
    update studenti set bursa = v_bursa_majorata where id = p_id;
    
    --if (v_bursa_majorata > 3000) then
      --v_bursa_majorata := 3000;
    --end if;
  end majoreaza_bursa;
begin

majoreaza_bursa(v_id, v_numar);

end;
