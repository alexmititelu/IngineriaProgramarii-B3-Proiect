package lab1;

public class Lab1 {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        String[] str = {"C","C++", "C#", "Go", "JavaScript", "Perl", "PHP", "Python", "Swift", "Java"};
        int n = (int) (Math.random() * 1_000_000);
        n = n * 3; 
        n = n + 0b10101;
        n = n + 0xff;
        n = n * 6;
        int rez;
        while(n>9){
            rez = 0;
            while(n != 0){
            rez = rez + n % 10;
            n = n / 10;
            }
        n = rez;
        }
       System.out.println("Willy-nilly, this semester I will learn " + str[n] + "!");
    }
       
}
