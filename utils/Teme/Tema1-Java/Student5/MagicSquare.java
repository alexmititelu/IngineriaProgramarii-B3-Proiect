import java.util.Scanner;

public class MagicSquare {

    public static void main(String[] args) {


        //optional

        Scanner in = new Scanner(System.in);
        int n= in.nextInt();
        in.close();
        long startTime=System.nanoTime();
        if(n % 2 == 0)
        {
            System.out.println("n par");
            return;
        }
        //else System.out.println(n);

        int l=31_000, c=31_000;
        int square[][]=new int[l][c];
        for(int i=0; i<n;i++)
        {
            for(int j=0; j<n;j++)
            {
                int x= n * ((i + j + 1 + n / 2) % n) + (i + 2 * j + 1) % n + 1;
                square[i][j] =x;
            }
        }

        int magicConstant= n*(n*n + 1)/2, suma=0;
        boolean verifica = true;
        //verific pe linii
        for(int i=0; i<n;i++)
        {
            for(int j=0; j<n;j++)
                suma=suma + square[i][j];
            if(suma != magicConstant)
                verifica= false;
            suma=0;
        }

        //verific pe coloane
        for(int j=0; j<n;j++)
        {
            for(int i=0; i<n;i++)
                suma=suma + square[i][j];
            if(suma != magicConstant)
                verifica= false;
            suma=0;
        }

        //verific pe dp
        for(int i=0; i<n;i++)
        {
            for(int j=0; j<n;j++)
                if(i == j )
                    suma=suma + square[i][j];
        }
        if(suma != magicConstant)
            verifica= false;
        suma=0;

        //verific pe ds
        for(int i=0; i<n;i++)
        {
            for(int j=0; j<n;j++)
            {
                if(j == n-i-1 )
                    suma=suma + square[i][j];
            }
        }
        if(suma != magicConstant)
            verifica= false;

        if(verifica == false)
            System.out.println("Suma pe fiecare linie, coloana, diagonala difera.");
        else
            System.out.println("Suma pe fiecare linie, coloana, diagonala este aceeasi.");

        if(n<=10)
        {
            for(int i=0; i<n;i++)
            {
                for(int j=0; j<n;j++)
                    System.out.print(square[i][j] + " ");
                System.out.print("\n");
            }
            System.out.print("magicConstant = " + magicConstant);
        }
        long endTime   = System.nanoTime();
        long totalTime = (endTime - startTime)/1000000000;
        if(n>10)
        {
            System.out.println("magicConstant = " + magicConstant);
            System.out.println("Timp executie = " + totalTime + " sec");
        }
    }
}