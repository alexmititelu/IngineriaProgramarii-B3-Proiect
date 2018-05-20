drop type angajat FORCE;
CREATE OR REPLACE TYPE angajat AS OBJECT
(nume varchar2(15),
 prenume varchar2(15),
 id number(3),
 data_nastere date,
 adresa varchar2(30),
 telefon varchar2(10),
 departament varchar2(20),
 MEMBER PROCEDURE afiseaza_varsta_angajat,
 MEMBER PROCEDURE afiseaza_informatii_angajat,
 MAP MEMBER FUNCTION ordoneaza
    RETURN NUMBER,
 CONSTRUCTOR FUNCTION angajat(nume varchar2, prenume varchar2,data_nastere date, adresa varchar2, telefon varchar2, departament varchar2)
    RETURN SELF AS RESULT,
  CONSTRUCTOR FUNCTION angajat(nume varchar2, prenume varchar2)
    RETURN SELF AS RESULT
)NOT FINAL;
/ 

drop type angajat_over FORCE;
CREATE OR REPLACE TYPE angajat_over UNDER angajat
(    
   salar NUMBER(6),
   OVERRIDING member procedure afiseaza_informatii_angajat
)
/
drop table angajati;
create table angajati(cnp number(10),obiect angajat);

CREATE OR REPLACE TYPE BODY angajat_over AS
    OVERRIDING MEMBER PROCEDURE afiseaza_informatii_angajat IS
    BEGIN
       DBMS_OUTPUT.PUT_LINE(nume||' '||prenume||' '||adresa||' '||telefon||' '||departament||' '||salar);
    END afiseaza_informatii_angajat;
END;
/
CREATE OR REPLACE TYPE BODY angajat AS
   CONSTRUCTOR FUNCTION angajat(nume varchar2, prenume varchar2,data_nastere date, adresa varchar2, telefon varchar2, departament varchar2)
      RETURN SELF AS RESULT
    AS
    BEGIN
      SELF.nume := nume;
      SELF.prenume := prenume;
      SELF.data_nastere := data_nastere;
      SELF.adresa := adresa;
      SELF.telefon := telefon;
      SELF.departament := departament;
      RETURN;
    END;
    
    CONSTRUCTOR FUNCTION angajat(nume varchar2, prenume varchar2)
      RETURN SELF AS RESULT AS
      BEGIN
        self.nume:=nume;
        self.prenume:=prenume;
        RETURN;
    END;
    
    MEMBER PROCEDURE afiseaza_varsta_angajat IS
   BEGIN
       DBMS_OUTPUT.PUT_LINE('Angajatul cu numele '||nume||' '||prenume||' are '||floor((sysdate-data_nastere)/365)||' '||'ani');
   END afiseaza_varsta_angajat;
    
    MEMBER PROCEDURE afiseaza_informatii_angajat IS
    BEGIN
      DBMS_OUTPUT.PUT_LINE(nume||' '||prenume||' '||adresa||' '||telefon||' '||departament);
    END;
    
    MAP MEMBER FUNCTION ordoneaza
    RETURN NUMBER AS
    BEGIN
      return self.id;
    END;
        
END;
  /

set serveroutput on;
DECLARE
   v_angajat1 angajat;
   v_angajat2 angajat;
   v_angajat3 angajat_over;
   v_angajat4 angajat;
BEGIN
   v_angajat1 := angajat('Popescu', 'Ionut',9, '11-NOV-1994','Str. Aviatiei','0746585156','C++');
   v_angajat2 := angajat('Vasilu', 'George',10, '25-DEC-1989','Str. George Cosbuc','0745624358','SQL');
   v_angajat1.afiseaza_varsta_angajat();
   v_angajat1.afiseaza_informatii_angajat();
   v_angajat3 := angajat_over('Ionescu', 'Ana',5, '14-JUN-1995','Str. Alexandru Cel Bun','0742666656','Java',2500);
   v_angajat3.afiseaza_informatii_angajat();
   v_angajat4:= angajat('Grosu','Aurelia');
   v_angajat4.afiseaza_informatii_angajat();
   insert into angajati values (100, v_angajat1);
   insert into angajati values (101, v_angajat2);
   --select*from angajati order by 2;
END;