set serveroutput on;
create or replace package tema4 is
  type lista_parametrii is record(p_id_student number , p_procentaj number);
  type myTab is table of lista_parametrii;
  procedure creste_bursa(p_stud myTab);
  procedure afisare_modificari;
    cursor lista_stud is
      select id, istoric from studenti
        where istoric is not null;
end tema4;
/
create or replace package body tema4 is
  procedure creste_bursa(p_stud myTab) is
  v_bursa_initiala studenti.bursa%type;
  v_bursa_marita studenti.bursa%type;
  v_lista_updates studenti.istoric%type;
  begin
    for i in p_stud.first..p_stud.last loop
      select bursa into v_bursa_initiala 
        from studenti 
          where id = p_stud(i).p_id_student;
      if(v_bursa_initiala is null) then
        update studenti set istoric = null 
          where id=p_stud(i).p_id_student;
        v_bursa_initiala:=100;
        update studenti set bursa=v_bursa_initiala 
          where id=p_stud(i).p_id_student;        
      v_bursa_marita := v_bursa_initiala + (p_stud(i).p_procentaj/100*v_bursa_initiala);
       update studenti set bursa=v_bursa_marita
          where id=p_stud(i).p_id_student;
       end if;
        select istoric into v_lista_updates from studenti
          where id=p_stud(i).p_id_student;
       if(v_bursa_initiala is not null) then
        if(v_lista_updates is not null) then
           v_lista_updates.extend(1);
           v_lista_updates(v_lista_updates.last) := v_bursa_initiala;
           update studenti set istoric=v_lista_updates
              where id=p_stud(i).p_id_student;
        else
          update studenti set istoric=lista_istoric(v_bursa_initiala) 
              where id=p_stud(i).p_id_student;
        end if;
       v_bursa_marita := v_bursa_initiala + (p_stud(i).p_procentaj/100*v_bursa_initiala);
       update studenti set bursa=v_bursa_marita 
          where id=p_stud(i).p_id_student;
       end if;
    end loop;
  end creste_bursa;
  
  procedure afisare_modificari is
    v_ist studenti.istoric%type;
    v_id studenti.id%type;
   -- v_std_linie lista_stud%rowtype;
  begin
   dbms_output.put_line('Istoric burse:');
    open lista_stud;
    loop
      fetch lista_stud into v_id, v_ist;
        exit when lista_stud%notfound;
        for i in v_ist.first..v_ist.last loop
          if (v_ist(i) is not null) then
            dbms_output.put_line('Id student: ' || v_id || ' , bursa: ' || v_ist(i));
          end if;
        end loop;
    end loop;
    close lista_stud;
  end afisare_modificari;

end tema4;
/
declare
  myRecord tema4.lista_parametrii;
  linii tema4.myTab;
begin
  select id, trunc(dbms_random.value(20,70)) bulk collect into linii 
    from studenti where id<6;
  tema4.creste_bursa(linii);
  tema4.afisare_modificari;
end;
/
--select * from studenti where id<6;
--update studenti set bursa=null where id=1 or id=2;
--update studenti set bursa=150 where id=3 or id=4;
--update studenti set bursa=200 where id=5;
--update studenti set istoric=null where id<6;
--update studenti set istoric=null;