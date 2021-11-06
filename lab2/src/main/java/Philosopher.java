public abstract class Philosopher extends Thread{
    Fork leftFork, rightFork;
    int id;
    double duration = 5;
    int howManyTimes = 0;

    public Philosopher(Fork leftFork, Fork rightFork, int id) {
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.id = id;
    }

    public void eat(){
        try {
//            System.out.println("Philosopher " + id + " is eating with forks " + leftFork.id + ", " + rightFork.id);
            howManyTimes++;
            Thread.sleep(5);
//            System.out.println("Philosopher " + id + " stops eating with forks " + leftFork.id + ", " + rightFork.id);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void think(){
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public double averageWaitingTime(){
        return (double)duration / (double) howManyTimes;
    }

    abstract public void run();
}
