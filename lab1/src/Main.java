class CounterSynchronized {
    public static int counter = 0;

    public synchronized static void increment() {
        counter += 1;
    }

    public synchronized static void decrement() {
        counter -= 1;
    }

}

class CounterNotSynchronized {
    public static int counter = 0;

    public static void increment() {
        counter += 1;
    }

    public static void decrement() {
        counter -= 1;
    }

}

class Semaphore {
    private boolean locked = false;
    private int waiting = 0;

    public Semaphore(int n) {
        waiting = n;
    }

    public synchronized void lock() {
        while (waiting == 0){
            try {
                this.wait();
            } catch (InterruptedException exception){
                exception.printStackTrace();
            }
        }

        waiting -= 1;
        locked = true;
    }

    public synchronized void unlock() {
        waiting += 1;
        locked = false;

        this.notify();
    }
}

class KILL_ME {
    private static Object s = new Object();
    private static int count = 0;
    public static void kill(){
        for(;;){
            new Thread(new Runnable(){
                public void run(){
                    synchronized(s){
                        count += 1;
                        System.err.println("New thread #"+count);
                    }
                    int i=100,j=1;
                    for(;;){
                        j = i * j + 1;
                    }
                }
            }).start();
        }
    }
}

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int n = (int) 10e6 ;

        long s;
        long t;


        Thread t1 = new Thread(() -> {
            for (int i = 0; i < n; i++) {
                CounterSynchronized.increment();
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < n; i++) {
                CounterSynchronized.decrement();
            }
        });


        s = System.currentTimeMillis();
        t1.start();
        t2.start();


        t1.join();
        t2.join();

        t = System.currentTimeMillis();

        System.out.println("Counter: " + CounterSynchronized.counter);
        System.out.println("time: " + (t - s) + " ms");


        Semaphore sem = new Semaphore(1);


        t1 = new Thread(() -> {
            for (int i = 0; i < n; i++) {
                sem.lock();
                CounterNotSynchronized.increment();
                sem.unlock();
            }
        });
        t2 = new Thread(() -> {
            for (int i = 0; i < n; i++) {
                sem.lock();
                CounterNotSynchronized.decrement();
                sem.unlock();
            }
        });

        s = System.currentTimeMillis();

        t1.start();
        t2.start();


        t1.join();
        t2.join();
        t = System.currentTimeMillis();

        System.out.println("Counter: " + CounterNotSynchronized.counter);
        System.out.println("time: " + (t - s) + " ms");

//        KILL_ME.kill();




    }
}
