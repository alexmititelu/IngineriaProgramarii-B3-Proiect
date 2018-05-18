/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magicsquare;

import java.util.Scanner;

/**
 *
 * @author Eduard
 */
public class MagicSquare {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        long startTime = System.nanoTime();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter an odd value:");
        int x = 1, copyn = 0;
        while (x == 1) {
            int n;
            n = scanner.nextInt();
            if (n % 2 == 0) {
                System.out.println("this is not an odd value, try again");
            } else if (n % 2 == 1) {
                System.out.println("this value is correct, you entered" + " " + n);
                copyn = n;
                break;
            }
        }

        int[][] matrix = new int[copyn][copyn];
        for (int i = 0; i < copyn; i++) {
            for (int j = 0; j < copyn; j++) {
                matrix[i][j] = copyn * ((i + j + 1 + copyn / 2) % copyn) + (i + 2 * j + 1) % copyn + 1;
            }
        }
            /*
            for(int i=0;i<copyn;i++){
            for(int j=0;j<copyn;j++){
                System.out.print(matrix[i][j]+" ");
            }
                System.out.println();
            }
            */
        Integer lSum = 0, cSum = 0, dSum = 0;
        if (copyn <= 10) {
            for (int i = 0; i < copyn; i++) {
                for (int j = 0; j < copyn; j++) {
                    System.out.print('\u25AE' + " ");
                    //System.out.print(square[i][j] + " ");
                }
                System.out.println();
            }
            System.out.println("The magic number is " + copyn * (copyn * copyn + 1) / 2);
        }
        int k = 1,i,j;
        for (i = 0; i < copyn; i++) {
            for (j= 0; j < copyn; j++) {
                lSum = lSum + matrix[i][j];
                cSum = cSum + matrix[j][i];
                if (i == j) {
                    dSum = dSum + matrix[i][j];
                }
            }
            if (lSum != copyn * (copyn * copyn + 1) / 2) {
                System.out.println("It's not magic row line: " + i);
                k = 0;
                break;
            }
            if (cSum != copyn * (copyn * copyn + 1) / 2) {
                System.out.println("It's not magic column: " + j);
                k = 0;
                break;
            }
            lSum = 0;
            cSum = 0;
        }
        if (k == 1 && dSum != copyn * (copyn * copyn + 1) / 2) {
            System.out.println("It's not magic diagonal");
            k = 0;
        }
        if (k == 1) {
            System.out.println("The square is magic");
        }
        if (copyn > 10) {
            System.out.println("The magic number is " + copyn * (copyn * copyn + 1) / 2);

            long endTime   = System.nanoTime();
            long totalTime = endTime - startTime;
            System.out.println(totalTime);

        }
    }
    
}
