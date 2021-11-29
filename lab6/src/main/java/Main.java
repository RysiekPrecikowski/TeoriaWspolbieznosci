import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Main {
    static double abs(double v){
        if (v < 0 )
            return -v;
        return v;
    }

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

        for(int i = 0 ; i < n-1 ; i++){
            for (int k = i+1 ; k < n ; k++){
                double m = M[k][i] / M[i][i];
                for (int j = i ; j < n+1 ; j++){
                    double c = M[i][j] * m;
                    M[k][j] -= c;
                }
            }
        }



        for(int i = 0 ; i < n ; i++) {
            for (int j = 0; j < n + 1; j++) {
                if (abs(M[i][j] - res[i][j]) > 1e-12){
                    System.out.println("XXXXXXXXXXXX");
                }
            }
        }
    }
}
