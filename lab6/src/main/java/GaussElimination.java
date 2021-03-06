import java.io.*;
import java.util.Scanner;

public class GaussElimination {

    static public void main(String[] args) throws InterruptedException, FileNotFoundException {
        Scanner sc = new Scanner(new BufferedReader(new FileReader("to_solve")));
        String sizeString = sc.nextLine();
        int n = Integer.parseInt(sizeString);

        double[][] M = new double[n][n+1];

        for (int i = 0 ; i < n ; i++){
            String[] line = sc.nextLine().trim().split(" ");
            for (int j = 0 ; j < n ; j ++){
                M[i][j] = Double.parseDouble(line[j]);
            }
        }

        String[] line = sc.nextLine().trim().split(" ");
        for (int i = 0 ; i < n ; i ++){
            M[i][n] = Double.parseDouble(line[i]);
        }


        Excecutor e = new Excecutor();
        double[][] res = e.run(M.clone());


        File file = new File("out.txt");
        FileOutputStream fos = new FileOutputStream(file);
        PrintStream ps = new PrintStream(fos);
        // set default output stream to file
        System.setOut(ps);

        // print matrix size
        System.out.println(n);
        // print matrix
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(res[i][j] + " ");
            }
            System.out.println();
        }

        // print RHS
        for (int j = 0; j < n; j++) {
            System.out.print(res[j][n] + " ");
        }
        System.out.println();
    }
}
