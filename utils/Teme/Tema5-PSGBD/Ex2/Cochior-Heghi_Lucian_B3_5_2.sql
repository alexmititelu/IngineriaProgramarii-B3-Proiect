create or replace function majorare_bursa_studenti(p_id in studenti.id%type)
    return int
as
v_bursa studenti.bursa%type;
bursa_mare exception;
PRAGMA EXCEPTION_INIT(bursa_mare, -20001);
v_marire_harcodata int := 2500;
v_nrStudenti int;
begin

    select count(*) into v_nrStudenti
    from studenti 
    where id = p_id 
    group by id;
    
    if( v_nrStudenti = 0) then
        return 0;
    end if;
    
    select bursa into v_bursa
    from studenti
    where id = p_id;
    
    if(v_bursa is null)then
        v_bursa := 0;
    end if;
    
    update studenti
    set istorie_burse = v_bursa
    where id = p_id;
    
    v_bursa := v_bursa + v_marire_harcodata;
--    
--    begin
    if(v_bursa > 3000)then
      v_bursa := 3000;
      update studenti
      set bursa = v_bursa
      where id = p_id;
      commit;
      raise bursa_mare;
    end if;
--    exception
--    when bursa_mare then
--        raise_application_error (-20001,'Studentul cu matricolul ' || p_id || ' are bursa mai mare ca .' || v_bursa);
--    end;
    
    
    if (v_bursa <= 3000) then
      update studenti
      set bursa = v_bursa
      where id = p_id;
      return 1;
    end if;

end majorare_bursa_studenti;


alter table studenti
add istorie_burse int;



set serveroutput on;
declare
v_return int;
bursa_mare exception;
PRAGMA EXCEPTION_INIT(bursa_mare, -20001);
begin
   
    for i in 101..200 loop
        begin
           v_return := majorare_bursa_studenti(i);
           exception
           when bursa_mare then
             DBMS_OUTPUT.put_line('Studentul----bursa prea mare.............');
        end;
    end loop;
   

end;


select *
from(
select id ||' ' ||nume||'-'||prenume||' '||bursa||' '|| istorie_burse
from studenti
where bursa is not null 
order by bursa desc)
where rownum <11;


