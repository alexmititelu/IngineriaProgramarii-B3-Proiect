
CREATE TABLE log_valori (
  linii   NUMBER
 );

CREATE OR REPLACE TRIGGER modif_valori
FOR UPDATE ON NOTE
COMPOUND TRIGGER
linii_modificate INT:=0;
valoare_veche int;
valoare_noua int;

  BEFORE EACH ROW IS 
  BEGIN
    valoare_veche:=:OLD.valoare;
    valoare_noua:=:NEW.valoare;

    IF (valoare_veche<valoare_noua) 
      THEN 
       linii_modificate :=linii_modificate +1;
     else
       :NEW.valoare:=valoare_veche;
    END IF;
       
     
  END BEFORE EACH ROW;
  
   AFTER STATEMENT IS 
   BEGIN 
     INSERT INTO log_valori VALUES(linii_modificate);
   END AFTER STATEMENT ;
   
END modif_valori;

--select * from note where id in (123,234,456,567);
--update note set valoare=8 where id in (123,234,456,567);
--select * from log_valori;
