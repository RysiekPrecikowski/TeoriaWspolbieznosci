import parallelism.ConcurentBlockRunner;

class Application {

    public static void main(String args[]) {

        LabExecutor e = new LabExecutor(new ConcurentBlockRunner());
        e.start();
    }
}
