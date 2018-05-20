drop type persoana force;
CREATE OR REPLACE TYPE persoana AS OBJECT
(nume varchar2(10),
 prenume varchar2(10),
 cnp number(4),
 data_nastere date,
 member procedure afiseaza_nume,
 member procedure afiseaza_prenume,
 member procedure afiseaza(nume varchar2,prenume varchar2),
 member procedure afiseaza(nume varchar2,prenume varchar2, cnp number,data_nastere date),
 map member FUNCTION cod_numeric RETURN NUMBER,
 CONSTRUCTOR FUNCTION persoana(nume varchar2, prenume varchar2)
  RETURN SELF AS RESULT
)NOT FINAL;
/ 
drop type student force;
CREATE OR REPLACE TYPE student UNDER persoana
(    
   an NUMBER(1),
   grupa varchar2(4),
   bursa NUMBER(6,2),
   OVERRIDING member procedure afiseaza_nume
)
/
CREATE OR REPLACE TYPE BODY persoana AS
   MEMBER PROCEDURE afiseaza_nume IS
   BEGIN
       DBMS_OUTPUT.PUT_LINE('Numele persoanei este: '||nume);
   END afiseaza_nume;
   MEMBER PROCEDURE afiseaza_prenume IS
   BEGIN
       DBMS_OUTPUT.PUT_LINE('Prenumele persoanei este: '||prenume);
   END afiseaza_prenume;
  
   MEMBER PROCEDURE afiseaza(nume varchar2,prenume varchar2) AS
   BEGIN
      DBMS_OUTPUT.PUT_LINE('Numele este: '||nume||' si prenumele este: '||prenume);
   END;
  MEMBER PROCEDURE afiseaza(nume varchar2,prenume varchar2, cnp number,data_nastere date) AS
   BEGIN
      DBMS_OUTPUT.PUT_LINE('Numele este: '||nume||' ,iar prenumele este: '||prenume||', cnp-ul este: '||cnp||' si data_nasterii este: '||data_nastere);
   END;
  
  map member FUNCTION cod_numeric RETURN NUMBER AS
  BEGIN 
    return self.cnp;
  END;
  
  CONSTRUCTOR FUNCTION persoana(nume varchar2, prenume varchar2)
    RETURN SELF AS RESULT
  AS
  BEGIN
    SELF.nume := nume;
    SELF.prenume := prenume;
    SELF.cnp := cnp;
    SELF.data_nastere := sysdate;
    RETURN;
  END;
  
END;
/
CREATE OR REPLACE TYPE BODY student AS
    OVERRIDING MEMBER PROCEDURE afiseaza_nume IS
    BEGIN
       dbms_output.put_line('Persoana cu numele: '||nume||' este student.');
    END afiseaza_nume;
END;
/
drop table persoane;
create table persoane (id number(2), obiect persoana);

set serveroutput on;
DECLARE
   v_persoana1 persoana;
   v_persoana2 persoana;
   v_persoana3 persoana;
   v_persoana4 persoana;
   v_student student;
BEGIN
    v_persoana1 := persoana('Vizitiu','Elena', 253, TO_DATE('09/05/1997', 'dd/mm/yyyy'));
    v_persoana2 := persoana('Arcana','Delia', 239, TO_DATE('13/02/1997', 'dd/mm/yyyy'));
    v_persoana1.afiseaza_nume();
    v_persoana1.afiseaza_prenume();
    v_persoana2.afiseaza_nume();
    v_persoana2.afiseaza_prenume();
    v_student := student('Mihalcea', 'Mircea', 245, TO_DATE('18/09/1996', 'dd/mm/yyyy'), 1, 'B3',100);
    dbms_output.put_line(v_student.nume);
    v_student.afiseaza_nume();
    v_persoana3 := persoana('Grosu','Aurelia');
    v_persoana3.afiseaza('Grosu','Aurelia');
    v_persoana4 := persoana('Grosu','Aurelia',345,TO_DATE('05/09/1997', 'dd/mm/yyyy'));
    v_persoana4.afiseaza('Grosu','Aurelia',345,TO_DATE('05/09/1997', 'dd/mm/yyyy'));
    insert into persoane values(12,v_persoana1);
    insert into persoane values(10,v_persoana2);
   -- select*from persoane order by 2;
END;