import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.Semaphore;

public class PhilosopherStarvation extends Philosopher{
    public PhilosopherStarvation(Fork leftFork, Fork rightFork, int id) {
        super(leftFork, rightFork, id);
    }

    @Override
    public void run() {
        Instant start;

        start = Instant.now();

        while (true){
            if (Duration.between(start, Instant.now()).toSeconds() > duration)
                break;

            think();
            think();

            leftFork.lock();
            rightFork.lock();

            if (leftFork.free && rightFork.free){
                leftFork.pickup();
                rightFork.pickup();

                leftFork.unlock();
                rightFork.unlock();

                eat();

                leftFork.putDownSem();
                rightFork.putDownSem();

            } else {
                leftFork.unlock();
                rightFork.unlock();
            }
        }
    }
}
