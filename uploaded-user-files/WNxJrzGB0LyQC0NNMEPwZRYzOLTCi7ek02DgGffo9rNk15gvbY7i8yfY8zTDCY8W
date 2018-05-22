public class HelloWorld {
    private static int n;
    private static String[] strg = {"C", "C++", "C#", "Go", "JavaScript", "Perl", "PHP", "Python", "Swift", "Java"};

    public static void main(String[] args) {

        System.out.println("Hello word!");
        for(int i=0; i< strg.length;i++){
            System.out.println(strg[i]);
        }
        n = (int) (Math.random()*1000000);
        n*=3;
        String bynaryNumber = "10101";
        n = n + Integer.parseInt(bynaryNumber, 2);
        bynaryNumber = "FF";
        n = n + Integer.parseInt(bynaryNumber, 16);
        n = n*6;
        System.out.println("n = " + n);

        int sum;

        while (true){
            sum = 0;
            while(n!=0){
                sum+=n%10;
                n = n/10;
            }

            if(sum/10 == 0) break;
            n = sum;
        }

        System.out.println("Willy-nilly, this semester I will learn " + strg[sum]);

    }
}
