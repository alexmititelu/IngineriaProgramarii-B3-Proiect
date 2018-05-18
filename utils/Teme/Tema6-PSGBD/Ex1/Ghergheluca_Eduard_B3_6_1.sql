set serveroutput on;

drop table masini;
create table masini(serie integer, cars masina);
/

--drop type masina;
CREATE OR REPLACE TYPE masina AS OBJECT 
(
nume varchar2(15),
tara varchar2(15),
an_fabricatie integer,
culoare varchar2(15),
member procedure afiseaza_nume,
member procedure afiseaza_culoare,
not final member procedure afiseaza_statut,
member procedure afiseaza_date (tara varchar2, culoare varchar2),
member procedure afiseaza_date (id integer, an integer),
map member function compara_an return number,
constructor function masina(nume varchar2, tara varchar2, culoare varchar2) return self as result
)NOT FINAL;
/

CREATE OR REPLACE TYPE BODY masina AS
    constructor function masina(nume varchar2, tara varchar2, culoare varchar2) return self as result as
    begin
        self.nume := nume;
        self.tara := tara;
        self.culoare := culoare;
        return;
    end;
    
    member procedure afiseaza_date(tara varchar2, culoare varchar2) is
        begin
            dbms_output.put_line('tara este '||tara||' iar culoarea este '||culoare);
        end afiseaza_date; 
        
       member procedure afiseaza_date(id integer, an integer) is
        begin
            dbms_output.put_line('id-ul este '||id||' iar anul fabricatiei este '||an);
        end afiseaza_date;
    
    member procedure afiseaza_nume is 
        begin
            dbms_output.put_line(self.nume);
        end afiseaza_nume;
        
    member procedure afiseaza_culoare is
        begin
            dbms_output.put_line(self.culoare);
        end afiseaza_culoare;
        
    map member function compara_an return number is
    begin
        return self.an_fabricatie;
    end compara_an;
    
    member procedure afiseaza_statut is
    begin
        dbms_output.put_line('masina noua');
    end afiseaza_statut;
    
     
END;
/

--drop type masini_secondhand;
create or replace type masini_secondhand under masina
(
    overriding member procedure afiseaza_statut
    --overriding member procedure afiseaza_culoare
);
/

create or replace type body masini_secondhand as
    overriding member procedure afiseaza_statut is
        begin
            dbms_output.put_line('masina second hand');
        end afiseaza_statut;
         
           
end;
/

declare
mobil masina;
mobil1 masina;
mobil2 masina;
mobil3 masina;
mobil4 masina;
vechitura masini_secondhand;
vechitura2 masini_secondhand;
begin
mobil := masina('mertan', 'rucania', 'alb');
mobil.afiseaza_nume;
mobil.afiseaza_culoare;
mobil.afiseaza_statut;
vechitura := masini_secondhand('dacia','venezuelea', 1942, 'verde');
vechitura2 := masini_secondhand('dacia','venezuelea', 2000, 'verde');
vechitura.afiseaza_statut;
mobil.afiseaza_date('anglia', 'verde');
mobil.afiseaza_date(120,1950);


mobil1 := masina('dacia','romania', 1990, 'verde');
mobil2 := masina('bmw', 'germania', 1992, 'negru');
mobil3 := masina('vw', 'germania', 1993, 'alb');
mobil4 := masina('volvo', 'suedia', 1994, 'portocaliu');

insert into masini values (120, mobil1);
insert into masini values (121, mobil2);
insert into masini values (122, mobil3);
insert into masini values (123, mobil4);
insert into masini values (124, vechitura);
insert into masini values (125, vechitura2);
--select* from masini order by cars desc;


end;



