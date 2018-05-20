import java.util.Random;

public class Lab1 {

    public static void main(String[] args) {
        System.out.println("Hello Word");

        String[] str = {"C", "C++", "C#", "GO", "JavaScript", "Perl", "PHP", "Python", "Swift", "Java"};
        int n = (int) (Math.random() * 1_000_000);
        n *= 3;
        n += 0b10101;
        n += 0xff;
        n *= 6;

        int rez = 0;

        while (n > 9) {
            rez = 0;

            while (n != 0) {
                rez += n % 10;
                n /= 10;
            }

            n = rez;
        }

        System.out.println("Willy-nilly, this semester I will learn " + str[rez] + ".");
    }
}