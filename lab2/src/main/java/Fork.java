import java.util.concurrent.Semaphore;

public class Fork {
    Semaphore semaphore = new Semaphore(1);
    boolean free = true;

    int id;

    public Fork(int id) {
        this.id = id;
    }

    public void pickupSem(){
        lock();
        free = false;
        unlock();
    }

    public void pickup(){
        free = false;
    }

    public void putDownSem(){
        lock();
        free = true;
        unlock();
    }

    public void putDown(){
        free = true;
    }

    public void lock() {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void unlock(){
        semaphore.release();
    }
}
