set SERVEROUTPUT ON;

declare

  numar_studenti number := 0;
  nume_familie studenti.nume%type := &input_familie;
  
  idul_student studenti.id%type;
  prenume_student studenti.prenume%type;
  
  nota_min note.valoare%type;
  nota_max note.valoare%type;
  
begin

select count(id) into numar_studenti
from studenti
where nume like nume_familie;

if (numar_studenti > 0)
then

DBMS_OUTPUT.PUT_LINE('Numarul de studenti cu numele de familie ' || nume_familie || ' este ' || numar_studenti);

select id, prenume into idul_student, prenume_student
from studenti
where nume like nume_familie and rownum < 2
order by prenume;

DBMS_OUTPUT.PUT_LINE('Studentul ' || nume_familie || ' ' || prenume_student || ' are id-ul ' || idul_student);

select min(valoare), max(valoare) into nota_min, nota_max
from note
where id_student = idul_student;

DBMS_OUTPUT.PUT_LINE('Studentul ' || nume_familie || ' ' || prenume_student || ' are cea mai mica nota ' || nota_min || ' si cea mai mare nota ' || nota_max);

DBMS_OUTPUT.PUT_LINE('Valoarea este: ' || nota_max**nota_min);

else

DBMS_OUTPUT.PUT_LINE('asf');

end if;

end;




