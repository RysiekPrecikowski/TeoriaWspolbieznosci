import java.util.*;

public class Excecutor {
    int n;
    double[][] M;

    double[][] m;
    double[][][] c;

    void A(int i, int k){
        double v = M[k][i] / M[i][i];

        m[i][k] = v;
    }

    void B(int i, int j, int k){
        double v = M[i][j] * m[i][k];
        c[i][j][k] = v;
    }

    void C(int i, int j, int k){
        M[k][j] = M[k][j] - c[i][j][k];
    }

    void runThreads(List<Thread> threads) throws InterruptedException {
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
    }

    void FA(int r) throws InterruptedException {
        List<Thread> threads = new LinkedList<>();
        for (int k = r+1; k < n; k++) {
            int finalK = k;
            threads.add(new Thread(() -> A(r, finalK)));
        }
        runThreads(threads);
    }

    void FB(int r) throws InterruptedException {
        List<Thread> threads = new LinkedList<>();

        for (int j = r ; j < n + 1 ; j++) {
            for (int k = r+1; k < n; k++) {
                int finalK = k;
                int finalJ = j;

                threads.add(new Thread(() -> B(r, finalJ, finalK)));
            }
        }
        runThreads(threads);
    }

    void FC(int r) throws InterruptedException {
        List<Thread> threads = new LinkedList<>();

        for (int j = r ; j < n + 1 ; j++) {
            for (int k = r+1; k < n; k++) {
                int finalK = k;
                int finalJ = j;

                threads.add(new Thread(() -> C(r, finalJ, finalK)));
            }
        }
        runThreads(threads);
    }



    public double[][] run(double[][] M) throws InterruptedException {
        n = M.length;
        this.M = M;

        m = new double[n-1][n];
        c = new double[n-1][n+1][n];

        for (int r = 0 ; r < n-1; r+=1){
            FA(r);
            FB(r);
            FC(r);
        }


        for (int i = n-1 ; i >= 0 ; i--){
            for(int j = i + 1; j < n ; j ++){
                M[i][n] -= M[i][j] * M[j][n];
                M[i][j] = 0;
            }

            M[i][n] /= M[i][i];
            M[i][i] = 1;
        }

        for (int i = 0 ; i < n ; i++){
            for(int j = 0 ; j < n+1 ; j++){

                System.out.print(M[i][j] + " ");
            }
            System.out.println();
        }
        return this.M;
    }


}
