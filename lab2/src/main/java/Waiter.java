import java.util.concurrent.Semaphore;

public class Waiter extends Semaphore {
    public Waiter(int n){
        super(n-1, true);
    }
}
