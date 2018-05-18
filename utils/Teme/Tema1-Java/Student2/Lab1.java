import java.math.BigInteger;

public class Lab1 {
    public static void main (String[] args) {
        System.out.println("hello world");
        String[] languages={"C", "C++", "C#", "Go", "JavaScript", "Perl", "PHP", "Python", "Swift", "Java"};
        int n;
        n = (int) (Math.random() * 1_000_000);
        String s="ff";
        int result;
        int decimal = Integer.parseInt(s, 16);
        int c,sum=0,sum2=0;
        result=((n*3)+0B10101+decimal)*6;
        System.out.println(result);

        while(result!=0){
            c=result%10;
            result=result/10;
            sum=sum+c;
        }
        if(sum<10)
            System.out.println(sum);

        if(sum>9){
            while(sum!=0){
                int c1=sum%10;
                sum=sum/10;
                sum2=sum2+c1;
            }
        }
        System.out.println("Suma este " +sum2);

        System.out.println("Willy-nilly, this semester I will learn " +languages[sum2]);



    }
}
