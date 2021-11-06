import java.util.concurrent.Semaphore;

public class Table {
    enum Mode {starvation, waiter};
    Philosopher[] philosophers;
    Fork[] forks;
    int n;

    public Table(int n, Mode mode) {
        this.n = n;

        forks = new Fork[n];

        for (int i = 0; i < n; i++) {
            forks[i] = new Fork(i);
        }

        switch (mode){
            case starvation -> {
                philosophers = new PhilosopherStarvation[n];
                for (int i = 0; i < n; i++) {
                    philosophers[i] = new PhilosopherStarvation(forks[i], forks[(i+1) % n], i);
                }
            }
            case waiter -> {
                Waiter waiter = new Waiter(n);
                philosophers = new PhilosopherWaiter[n];
                for (int i = 0; i < n; i++) {
                    philosophers[i] = new PhilosopherWaiter(forks[i], forks[(i+1) % n], i, waiter);
                }
            }
        }
    }

    public void run() {
        for (Philosopher philosopher : philosophers)
            philosopher.start();

        for (Philosopher philosopher : philosophers) {
            try {
                philosopher.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (Philosopher philosopher : philosophers) {
//            System.out.println("philosopher " + String.format("% 3d", philosopher.id) +
//                    " -- average waiting time " + String.format("%.3g", philosopher.averageWaitingTime()) +
//                    " -- " + philosopher.howManyTimes);

            System.out.print(String.format("%.3g", philosopher.averageWaitingTime()).replace(',', '.') + ", ");
        }
    }
}
