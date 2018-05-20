
public class Lab1 {

    public static void main(String[] args) {

        System.out.println("Hello World!");

        String languages[]={"C", "C++", "C#", "Go", "JavaScript", "Perl", "PHP", "Python", "Swift", "Java"};

        int n = (int) (Math.random() * 1_000_000);

        n=(n*3+Integer.parseInt("1001",2)+Integer.parseInt("FF",16))*6;

        int rezultat=0, rezultat1=0;
        while(n>0)
        {
            rezultat=rezultat + n%10;
            n=n/10;
            if(rezultat>=10)
            {
                rezultat1=rezultat%10;
                rezultat=rezultat/10;
                rezultat=rezultat+rezultat1;
            }
        }

        System.out.println("Willy-nilly, this semester I will learn " + languages[rezultat]);

    }
}
