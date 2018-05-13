package ro.uaic.info.ip.proiect.b3.model;


/**
 * Avem o clasa care va primi un String si care are o metoda de set si de getByNrMatricol.
 * Trebuie sa verificam pentru a putea face coverage de 100% , toate metodele din aceasta clasa.
 * 1) Pentru a putea sa verificam contructorul va trebui dupa ce instantam obiectul de tipul ClassForTest sa aiba atributul "mesaj" valoarea pe care am dato la instantierea obiectului
 * 1) Acest lucru se face cu ajutorul assertEquals care este o metoda din Junit ( Un exemplu este in Test )
 * 2) Pentru a putea verifica daca getByNrMatricol functioneaza atunci va trebui sa dam o valoare la mesaj si sa vedem daca getByNrMatricol-ul ne va da acelasi rezultat
 * 3) Pentru a putea verifica daca set functioneaza atunci va trebui sa dam o valoare la mesaj prin metoda set si dupa sa apelam getByNrMatricol iar daca rezultatul este acelasi atunci testul e complet
 * 4) Pentru a putea face toate aceste teste noi va trebui sa ne facem urmatoarele :
 * - Declarari de clase,artribute... pentru a le putea mockui ( pentru a le da anumite valori claselor,atributelor ... sa le ia ).
 * - Atribuirea acelor valori claselor,atributelor pe care dorim sa le testam
 * - Apelarea metodelor din clasa cu parametri respectivi
 * - Verificarea resultatelor
 * <p>
 * <p>
 * Exista mai multe tipuri de testing ex: Positive Testing , Negative Testing , Boundary Check ...
 * Noi ne vom baza in principal pe Positive Testing si Negative Testing.
 * <p>
 * Positive Testing :
 * Acest tip de testing se refera la verificare logici dintr-o metoda.
 * Positive Testing este strans legat de a face 100% coverage pentru ca asta ne dovedeste ca am verificat fiecare mod prin care anumite variabile,clase... pot fi modificate de catre
 * o anumita metoda.
 * <p>
 * <p>
 * Negative Testing :
 * Negative testing se refera la faptul ca noi avand anumite statement-uri in care nu avem un caz negativ atunci acesta este considerat negative testing si trebuie sa verificam ca aceea metoda
 * nu a facut absolut nicio modificare asupra clasei,variabilelor...
 * EX :
 * int y = 0;
 * public void method(int x)
 * {
 * if(x > 2)
 * {
 * y = x;
 * }
 * }
 * Aceasta metoda trebuie sa aiba si negative test deoarece trebuie vazut si data acel if nu este valid rezultand ca y va ramane cu valoare initiala.
 */

public class ClassForTest {

    private String mesaj;
    private Integer y = 0;

    public ClassForTest(String mesaj) {
        this.mesaj = mesaj;
    }


    public String getMesaj() {
        return mesaj;
    }

    public void setMesaj(String mesaj) {
        this.mesaj = mesaj;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public void assignY(Integer x)
    {
        if(x > 2)
        {
            this.y = x;
        }
    }
}