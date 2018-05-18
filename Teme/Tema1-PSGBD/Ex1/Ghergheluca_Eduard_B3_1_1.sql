set SERVEROUTPUT on;

DECLARE
    inputName studenti.nume%TYPE:='&input';
    getNameNr int;
    getID studenti.id%TYPE;
    getPrenume studenti.prenume%TYPE;
    notaMax note.valoare%TYPE;
    notaMin note.valoare%TYPE;
    powerNum int;
    
BEGIN
select count(NUME) into getNameNr from STUDENTI where nume=inputName;

    if(getNameNr = 0) 
    THEN
    DBMS_OUTPUT.PUT_LINE('NUMELE NU EXISTA IN BAZA DE DATE');
    ELSE
    DBMS_OUTPUT.PUT_LINE('NUMELE EXISTA IN BAZA DE DATE');
    
   
DBMS_OUTPUT.PUT_LINE('SUNT' ||' '|| getNameNr||' '|| 'STUDENTI' || ' ' || 'CU NUMELE' || ' ' || inputName);

select id ,prenume into getID, getPrenume from 
(select id, prenume from studenti where nume=inputName order by prenume asc)
where rownum<2;
DBMS_OUTPUT.PUT_LINE('ID-UL STUDENTULUI ESTE' || ' '|| getID ||' '||',IAR PRENUMELE ESTE'||' '||getPrenume);

select max(valoare), min(valoare) into notaMax, notaMin from note
where id_student=492;
DBMS_OUTPUT.PUT_LINE('CEA MAI MARE NOTA ESTE'||' '||notaMax||',IAR CEA MAI MICA NOTA ESTE'||' '||notaMin);

select power(max(valoare), min(valoare)) into powerNum from note
where id_student=492;
DBMS_OUTPUT.PUT_LINE('NUMARUL A LA PUTEREA B ESTE:'||' '||powerNum);

END IF;
END;



