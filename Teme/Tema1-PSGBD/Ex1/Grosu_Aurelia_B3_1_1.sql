set serveroutput on;
DECLARE
   nume_nou studenti.nume%TYPE :='&i_nume';
   numar_nume studenti.id%TYPE;
   id_nou studenti.id%TYPE;
   prenume_nou studenti.prenume%TYPE;
   nota_maxima note.valoare%TYPE;
   nota_minima note.valoare%TYPE;
BEGIN   
   SELECT count(id) INTO numar_nume FROM studenti WHERE nume=nume_nou;
   IF(numar_nume=0)
   then DBMS_OUTPUT.PUT_LINE('Numele '||nume_nou||' nu exista in baza de date.');
      ELSE DBMS_OUTPUT.PUT_LINE('Numele '||nume_nou||' exista in baza de date. '|| 'Numarul de studenti cu acest nume este: '||numar_nume||'.');
           SELECT id,prenume INTO id_nou,prenume_nou FROM studenti WHERE nume=nume_nou and rownum=1 ORDER BY prenume_nou asc;
           DBMS_OUTPUT.PUT_LINE('Id-ul studentului cu numele '||nume_nou||' este: '||id_nou||', iar prenumele in ordine lexicografica este: '||prenume_nou||'.');
           SELECT MAX(valoare),MIN(valoare) INTO nota_maxima,nota_minima FROM note WHERE id_student=id_nou;
           DBMS_OUTPUT.PUT_LINE('Nota maxima a studentului '||nume_nou||' '||prenume_nou||' este: '||nota_maxima||', iar nota minima este: '||nota_minima||'.');
           DBMS_OUTPUT.PUT_LINE('Valoare notei maxime la puterea notei minime a studentului '||nume_nou||' '||prenume_nou||' este: '||power(nota_maxima,nota_minima)||'.');
   END IF;  
  
END;