import java.time.Duration;
import java.time.Instant;

public class PhilosopherWaiter extends Philosopher {
    Waiter waiter;
    public PhilosopherWaiter(Fork leftFork, Fork rightFork, int id, Waiter waiter) {
        super(leftFork, rightFork, id);
        this.waiter = waiter;
    }

    @Override
    public void run() {
        Instant start;

        start = Instant.now();
        while (true){
            if (Duration.between(start, Instant.now()).toSeconds() > duration)
                break;

            try {
                waiter.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            think();
            leftFork.lock();

            think();
            rightFork.lock();

            eat();


            leftFork.unlock();
            rightFork.unlock();

            waiter.release();
        }
    }
}
