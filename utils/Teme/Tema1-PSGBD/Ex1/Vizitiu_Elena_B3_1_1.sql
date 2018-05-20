set serveroutput on;
declare
  x varchar2(50):='&i_nume';
  v_prenume studenti.prenume %type;
  v_numar_studenti int:=0;
  v_id_student studenti.id %type;
  v_nota_minima note.valoare %type;
  v_nota_maxima note.valoare %type;
begin
  select count(*) into v_numar_studenti from studenti where x = nume;
  
  if(v_numar_studenti<=0)
  then
    dbms_output.put_line('Nu exista in baza de date');
  else
    dbms_output.put_line('Nr studenti cu numele dat: '||v_numar_studenti);
    
    select id, prenume into v_id_student, v_prenume from studenti where rownum=1
    and x=nume
    order by prenume;
    dbms_output.put_line('Id-ul studentului: '||v_id_student||' Prenumele studentului: '||v_prenume);
    
    select min(valoare), max(valoare) into v_nota_minima, v_nota_maxima from note where id_student=v_id_student;
    dbms_output.put_line('Nota minima a studentului: '||v_nota_minima||' Nota maxima a studentului: '||v_nota_maxima);
    dbms_output.put_line('Nota maxima ridicata la nota minima: '||power(v_nota_maxima,v_nota_minima));
    
  end if;
end;