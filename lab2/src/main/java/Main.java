public class Main {
    public static void main(String[] argv){
        int[] i = {5, 10, 25, 50};

        for(int n : i){
            System.out.println(n + " Philosophers");
            System.out.println("Starvation");
            Table tableStarvation = new Table(n, Table.Mode.starvation);
            tableStarvation.run();


            System.out.println("\nWaiter");
            Table tableWaiter = new Table(n, Table.Mode.waiter);
            tableWaiter.run();

            System.out.println("\n\n\n");
        }
    }
}
