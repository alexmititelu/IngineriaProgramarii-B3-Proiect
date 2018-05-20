drop type programator force;
drop type incepator force;

CREATE OR REPLACE TYPE programator AS OBJECT
(nume varchar2(50),
 prenume varchar2(50),
 limbaj varchar2(30),
 inaltime number,
 member procedure afiseaza_inaltime,
 member procedure afiseaza_limbaj,
 map member function comparator return number,
 member procedure detalii(nume varchar2, prenume varchar2),
 member procedure detalii(nume varchar2, inaltime number),
 not final member procedure afiseaza_nume,
 constructor function programator(nume varchar2, prenume varchar2) return self as result
) not final;
/

CREATE OR REPLACE TYPE BODY programator AS
   constructor function programator(nume varchar2, prenume varchar2) return self as result as
   begin
    self.nume := nume;
    self.prenume := prenume;
    self.limbaj := 'java';
    self.inaltime := 21;
    return;
   end;
   
   member procedure afiseaza_inaltime is
   begin
    dbms_output.put_line('acest programator are inaltimea de ' || self.inaltime || ' cm');
   end afiseaza_inaltime;
   
   member procedure afiseaza_limbaj is
   begin
    dbms_output.put_line('acest programator cunoste limbajul ' || self.limbaj);
   end afiseaza_limbaj;
   
   map member function comparator return number is
   begin
    return self.inaltime;
   end comparator;
   
   member procedure detalii(nume varchar2, prenume varchar2) is
   begin
    dbms_output.put_line('acest programator este ' || nume || ' ' || prenume);
   end detalii;
   
   member procedure detalii(nume varchar2, inaltime number) is
   begin
    dbms_output.put_line('acest programator este ' || nume || ' si are inaltimea de ' || inaltime || ' cm');
   end detalii;
   
   member procedure afiseaza_nume is
   begin
    dbms_output.put_line('acest programator este ' || self.nume);
   end afiseaza_nume;
   
   
   
END;
/


create or replace type incepator under programator(
  overriding member procedure afiseaza_nume
);
/

create or replace type body incepator as
  overriding member procedure afiseaza_nume is
  begin
    dbms_output.put_line('acest incepator este ' || self.nume);
  end afiseaza_nume;
end;
/

drop table programatori;

create table programatori(numar_ordine integer, angajat programator);
/

set serveroutput on;

declare
  programator1 programator;
  programator2 programator;
  programator3 programator;
  programator4 programator;
  
  incepator1 incepator;
  
begin
  
  programator1 := programator('Gelu', 'Pinguinescu', 'C--', 23);
  programator2 := programator('Nelu', 'Pinguinescu', 'Java', 14);
  programator3 := programator('Felu', 'Pinguinescu', 'JavaScript', 5);
  programator4 := programator('Telu', 'Pinguinescu');
  
  incepator1 := incepator('Relu', 'Pinguinescu', 'C++', 16);
  
  programator1.afiseaza_nume;
  programator1.afiseaza_inaltime;
  programator1.afiseaza_limbaj;
  
  incepator1.afiseaza_nume;
  incepator1.afiseaza_inaltime;
  incepator1.afiseaza_limbaj;
  
  incepator1.detalii('Fabian', 'Piunguinescu');
  incepator1.detalii('Fabian', 23);
  
  insert into programatori values(1, programator1);
  insert into programatori values(2, programator2);
  insert into programatori values(3, programator3);
  insert into programatori values(4, programator4);
  
  insert into programatori values(5, incepator1);
  
end;

--select * from programatori order by angajat;























