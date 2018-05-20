set serveroutput on;

create or replace package tema is

type students is record(id integer, procentaj integer);
type lista_studenti is varray(5) of students;

procedure functie(studenti_list lista_studenti);

end tema;
/

create or replace package body tema is

v_lista lista_studenti;
v_rec students;

procedure functie(studenti_list in lista_studenti) is
bursaV studenti.bursa%type;
begin

    for iterator in studenti_list.first..studenti_list.last loop
    
      select bursa into bursaV from studenti
      where id = studenti_list(iterator).id;
      
      if (bursaV is null)
      then
      
        update studenti set bursa = (100 + 100 * studenti_list(iterator).procentaj / 100) where id = studenti_list(iterator).id;
        
      else
        
        update studenti set bursa = (bursa + bursa * studenti_list(iterator).procentaj / 100) where id = studenti_list(iterator).id;
        --update studenti set bursa = (bursa * 100 / (100 + studenti_list(iterator).procentaj)) WHERE ID = studenti_list(iterator).id;
  
      end if;
        
    end loop;

    exception when NO_DATA_FOUND then
    dbms_output.put_line('No data found!');

end functie;

end tema;
/

declare

  stud tema.students;
  stud2 tema.students;
  stud3 tema.students;
  stud4 tema.students;
  stud5 tema.students;
  lista tema.lista_studenti;

begin

  stud.id := 123;
  stud.procentaj := 60;
  
  stud2.id := 124;
  stud2.procentaj := 70;
  
  stud3.id := 125;
  stud3.procentaj := 30;
  
  stud4.id := 126;
  stud4.procentaj := 20;
  
  stud5.id := 127;
  stud5.procentaj := 10;
  
  lista := tema.lista_studenti(stud, stud2, stud3, stud4, stud5);
  
  tema.functie(lista);
  
  --select * from studenti where id = 123 or id = 124 or id = 125 or id = 126 or id = 127;
  
end;

