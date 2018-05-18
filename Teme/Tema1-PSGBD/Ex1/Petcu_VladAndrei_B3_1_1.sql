set serveroutput on;

declare 
  v_nume_student studenti.nume%type := '&i_nume';
  v_numar_studenti integer := 0;
  v_id_primul_student studenti.id%type;
  v_prenume_primul_student studenti.prenume%type;
  v_nota_maxima note.valoare%type;
  v_nota_minima note.valoare%type;

begin
select count(nume) into v_numar_studenti
  from studenti
  where nume = v_nume_student ;

if(v_numar_studenti = 0) then
  dbms_output.put_line('Nu sunt studenti cu numele introdus');
else
  dbms_output.put_line('Numar de studenti cu acelasi nume: ' || v_numar_studenti);
  
  select id, prenume into v_id_primul_student, v_prenume_primul_student
    from studenti 
    where nume = v_nume_student and rownum = 1
    order by prenume asc;
    dbms_output.put_line('ID-ul primului student: ' || v_id_primul_student);
    dbms_output.put_line('Prenumele primului student: ' || v_prenume_primul_student);
  
  select max(valoare), min(valoare) into v_nota_maxima, v_nota_minima
    from note 
    where id_student = v_id_primul_student;
    dbms_output.put_line('Nota maxima si nota minima: ' || v_nota_maxima || ' ' || v_nota_minima);
    
   dbms_output.put_line('A la puterea B: ' || v_nota_maxima ** v_nota_minima);
    
end if;


end;