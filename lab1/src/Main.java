interface Counter {
    void increment();

    void decrement();

    int getCounter();
}

interface Semaphore {
    void lock();

    void unlock();
}

class CounterSynchronized implements Counter {
    public int counter = 0;

    public synchronized void increment() {
        counter += 1;
    }

    public synchronized void decrement() {
        counter -= 1;
    }

    public int getCounter() {
        return counter;
    }
}

class CounterNotSynchronized implements Counter {
    public int counter = 0;

    public void increment() {
        counter += 1;
    }

    public void decrement() {
        counter -= 1;
    }

    public int getCounter() {
        return counter;
    }
}

class BinarySemaphore implements Semaphore {
    private boolean blocked = false;

    public synchronized void lock() {
        while (blocked) {
            try {
                this.wait();
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        }

        blocked = true;
    }

    public synchronized void unlock() {
        blocked = false;

        this.notify();
    }
}


/**
 * W semaforze nie wystarczy tylko instukcja if
 */
class BinarySemaphoreWithIf implements Semaphore {
    private boolean blocked = false;

    public synchronized void lock() {
        if (blocked) {
            try {
                this.wait();
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        }

        blocked = true;
    }

    public synchronized void unlock() {
        blocked = false;

        this.notify();
    }
}

/**
 * semafor binarny jest szczególnym przypadkiem semafora ogólnego
 * jeśli w semaforze binarnym użylibyśmy do sprawdzania wartosci 0 i 1 zamiast true i false
 * to uzyskalibyśmy semafor ogólny z 1 jednostką dostępnego zasobu
 */
class CountingSemaphore implements Semaphore {
    private int free;

    public CountingSemaphore(int free) {
        this.free = free;
    }

    public synchronized void lock() {
        while (free == 0) {
            try {
                this.wait();
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        }

        free -= 1;
    }

    public synchronized void unlock() {
        free += 1;

        this.notify();
    }
}

class ThreadCounterTest {
    Thread t1, t2;
    long s, t;
    Counter counter;

    public ThreadCounterTest(int n, Counter counter) {
        this.counter = counter;
        t1 = new Thread(() -> {
            for (int i = 0; i < n; i++) {
                counter.increment();
            }
        });
        t2 = new Thread(() -> {
            for (int i = 0; i < n; i++) {
                counter.decrement();
            }
        });
    }

    public ThreadCounterTest(int n, Counter counter, Semaphore semaphore) {
        this.counter = counter;
        t1 = new Thread(() -> {
            for (int i = 0; i < n; i++) {
                semaphore.lock();
                counter.increment();
                semaphore.unlock();
            }
        });
        t2 = new Thread(() -> {
            for (int i = 0; i < n; i++) {
                semaphore.lock();
                counter.decrement();
                semaphore.unlock();
            }
        });
    }


    void run() throws InterruptedException {
        s = System.currentTimeMillis();
        t1.start();
        t2.start();

        t1.join();
        t2.join();

        t = System.currentTimeMillis();
    }

    public void printTimes() {
        System.out.println("Counter: " + counter.getCounter());
        System.out.println("time: " + (t - s) * 0.001 + " s\n");
    }

    public void printTimes(String msg) {
        System.out.println(msg);
        printTimes();
    }
}

class MaxThreadTest {
    public static void testWithComputations() {
        int count = 0;
        while (true) {
            new Thread(() -> {
                int i = 100, j = 50;
                while (true) {
                    i = j + i * j;
                    j = i - j * i;
                }
            }).start();
            System.out.println("Thread nr " + count);
            count += 1;
        }
    }

    public static void testSleep() {
        int count = 0;
        while (true) {
            new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(Integer.MAX_VALUE);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            System.out.println("Thread nr " + count);
            count += 1;
        }
    }
}

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int n = (int) 2e7;

        ThreadCounterTest counterNotSynchronized = new ThreadCounterTest(n, new CounterNotSynchronized());
        ThreadCounterTest counterSynchronized = new ThreadCounterTest(n, new CounterSynchronized());
        ThreadCounterTest counterWithBinarySemaphore = new ThreadCounterTest(n, new CounterNotSynchronized(), new BinarySemaphore());
        ThreadCounterTest counterWithIfSemaphore = new ThreadCounterTest(n, new CounterNotSynchronized(), new BinarySemaphoreWithIf());
        ThreadCounterTest counterWithCountingSemaphore = new ThreadCounterTest(n, new CounterNotSynchronized(), new CountingSemaphore(1));


        counterNotSynchronized.run();
        counterSynchronized.run();
        counterWithBinarySemaphore.run();
        counterWithIfSemaphore.run();
        counterWithCountingSemaphore.run();


        counterNotSynchronized.printTimes("Not synchronized counter");
        counterSynchronized.printTimes("Synchronized counter");
        counterWithBinarySemaphore.printTimes("Counter with binary semaphore");
        counterWithIfSemaphore.printTimes("Counter with IF statement semaphore");
        counterWithCountingSemaphore.printTimes("Counter with counting semaphore");

//        MaxThreadTest.testWithComputations();
//        MaxThreadTest.testSleep();
    }
}
