SET SERVEROUTPUT ON;

DROP TABLE plsqlnewbie;

CREATE TABLE plsqlnewbie (a NUMBER, b NUMBER);

DECLARE
    c_userInput CONSTANT NUMBER := 4;
    v_forCounter1 INTEGER;
    v_forCounter2 INTEGER;
    v_forCounter1Copy INTEGER;
    v_digitSum INTEGER;
    v_prime BOOLEAN;
    
BEGIN
    FOR v_forCounter1 IN 1..10000 LOOP
        v_digitSum := 0;
        v_forCounter1Copy := v_forCounter1;
        
        WHILE ( v_forCounter1Copy != 0 ) LOOP
            v_digitSum := v_digitSum + ( v_forCounter1Copy MOD 10 );
            v_forCounter1Copy := FLOOR(v_forCounter1Copy / 10);
        END LOOP;

        IF v_digitSum MOD 9 = c_userInput THEN
        
            IF v_forCounter1 = 1 THEN
                INSERT INTO plsqlnewbie (a,b) VALUES (v_forCounter1,0);
            ELSIF ( v_forCounter1 = 2 OR v_forCounter1 = 3 ) THEN
                INSERT INTO plsqlnewbie (a,b) VALUES (v_forCounter1,1);
            ELSE
                v_prime := true;
                
                FOR v_forCounter2 IN 2..sqrt(v_forCounter1) LOOP
                    IF v_forCounter1 MOD v_forCounter2 = 0 THEN
                        v_prime := false;
                        EXIT WHEN v_prime = false;
                    END IF;
                END LOOP;
                
                IF v_prime = true THEN
                    INSERT INTO plsqlnewbie (a,b) VALUES (v_forCounter1,1);
                ELSE
                    INSERT INTO plsqlnewbie (a,b) VALUES (v_forCounter1,0);
                END IF;

            END IF;
        END IF;

    END LOOP;
END;