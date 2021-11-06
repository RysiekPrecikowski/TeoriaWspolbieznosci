//import javax.xml.parsers.SAXParser;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.Semaphore;
//
//abstract class Philosopher implements Runnable {
//    abstract public void run();
//
//    enum Action {thinking, eating, pickingLeft, pickingRight}
//    protected Fork leftFork, rightFork;
//    int i;
//
//    public Philosopher(Fork leftFork, Fork rightFork, int i) {
//        this.leftFork = leftFork;
//        this.rightFork = rightFork;
//        this.i = i;
//    }
//
//
//    protected void doSth(Action action) throws InterruptedException {
//        String msg = "Philosopher " + i + " ";
//
//
//        switch (action){
//            case eating -> msg += "eating";
//            case thinking -> msg += "thinking";
//            case pickingLeft -> msg += "picking left fork " + leftFork.i;
//            case pickingRight -> msg += "picking right fork " + rightFork.i;
//        }
//
//        System.out.println(msg);
//        Thread.sleep(500);
//    }
//}
//
//
//class PhilosopherStarvation extends Philosopher {
//    public PhilosopherStarvation(Fork leftFork, Fork rightFork, int i) {
//        super(leftFork, rightFork, i);
//    }
//
//    @Override
//    public void run() {
//        try{
//            while (true){
//                doSth(Philosopher.Action.thinking);
//
//                leftFork.acquire();
//                rightFork.acquire();
//
//
//                doSth(Philosopher.Action.pickingLeft);
//                doSth(Philosopher.Action.pickingRight);
//
//                doSth(Philosopher.Action.eating);
//
//                leftFork.release();
//                rightFork.release();
//            }
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//    }
//}
//
//class PhilosophersWaiter extends Philosopher {
//    Waiter waiter = new Waiter();
//    public PhilosophersWaiter(Fork leftFork, Fork rightFork, int i) {
//        super(leftFork, rightFork, i);
//    }
//
//    @Override
//    public void run() {
//        try{
//            while (true){
//                doSth(Philosopher.Action.thinking);
//
//
//                waiter.acquire();
//                leftFork.acquire();
//                rightFork.acquire();
//
//
//                doSth(Philosopher.Action.pickingLeft);
//                doSth(Philosopher.Action.pickingRight);
//
//                doSth(Philosopher.Action.eating);
//
//                leftFork.release();
//                rightFork.release();
//                waiter.release();
//            }
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//    }
//}
//
//
//class Waiter extends Semaphore{
//    public Waiter(){
//        this(4);
//    }
//
//    private Waiter(int permits) {
//        super(permits);
//    }
//}
//
//class Fork extends Semaphore{
//    public String i;
//
//    public Fork(String i){
//        this(1);
//        this.i = i;
//    }
//
//    @Override
//    public void acquire() throws InterruptedException {
//        super.acquire();
////        System.out.println("Fork " + i + " picked");
//    }
//
//    @Override
//    public void release() {
//        super.release();
//        System.out.println("Fork " + i + " put down");
//    }
//
//    private Fork(int permits) {
//        super(permits);
//    }
//}
//
//class Solve{
//    Philosopher[] philosophers = new Philosopher[5];
//    Fork[] forks = new Fork[5];
//    int n = 5;
//
//    public Solve() {
//        for (int i = 0 ; i < n ; i++){
//            forks[i] = new Fork(Integer.toString(i));
//        }
//
//        for (int i = 0 ; i < n ; i++){
//            philosophers[i] = new PhilosophersWaiter(forks[i], forks[getIndex(i+1)], i);
//
//            Thread t = new Thread(philosophers[i], "philosopher" + i);
//            t.start();
//        }
//
//    }
//
//    private int getIndex(int i){
//        return (i + 5) % 5;
//    }
//}
//
//
//
//public class Main {
//    public static void main(String[] args){
//        Solve solver = new Solve();
//
//    }
//}
