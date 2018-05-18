SET SERVEROUTPUT ON;

DECLARE

    v_fibo1 NUMBER;
    v_fibo2 NUMBER;
    v_aux NUMBER;
    v_changesCounter NUMBER:=0;
    v_fiboNumber BOOLEAN;
    v_numberToTest NUMBER;
    v_FiboNoOrNot NUMBER;
    
    CURSOR update_plsqlNewbie IS 
    SELECT a,b FROM plsqlNewbie  FOR UPDATE OF b;
    
BEGIN

    OPEN update_plsqlNewbie;
    
    LOOP
        FETCH update_plsqlNewbie INTO v_numberToTest, v_FiboNoOrNot;
        EXIT WHEN update_plsqlNewbie%NOTFOUND;
        
        v_fiboNumber:=FALSE;
        v_fibo1:=0;
        v_fibo2:=1;
        
        WHILE v_fibo2 <= v_numberToTest LOOP    -- {Testing if the number is a Fibonacci number
            IF v_fibo2=v_numberToTest THEN
                v_fiboNumber:=TRUE;
                EXIT;
            END IF;
            
            v_aux:=v_fibo1;
            v_fibo1:=v_fibo2;
            v_fibo2:=v_fibo2+v_aux;
        END LOOP;                               -- Testing if the number is a Fibonacci number}
        
        IF v_fiboNumber=TRUE AND v_FiboNoOrNot=0 THEN
            UPDATE plsqlNewbie SET b=1 WHERE CURRENT OF update_plsqlNewbie;
            v_changesCounter:=v_changesCounter+1;
        ELSIF v_fiboNumber=FALSE AND v_FiboNoOrNot=1 THEN
            UPDATE plsqlNewbie SET b=0 WHERE CURRENT OF update_plsqlNewbie;
            v_changesCounter:=v_changesCounter+1;
        END IF;
        
    END LOOP;
   
    DBMS_OUTPUT.PUT_LINE(v_changesCounter||' tuples have been changed in the B column of the PLSQLNEWBIE table.');
    
    CLOSE update_plsqlNewbie;
    
END;