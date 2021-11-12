import mesh.GraphDrawer;
import mesh.Vertex;
import myProductions.*;
import parallelism.BlockRunner;

import production.PDrawer;



public class LabExecutor extends Thread {
    private final BlockRunner runner;

    public LabExecutor(BlockRunner _runner){
        this.runner = _runner;
    }

    @Override
    public void run(){
        PDrawer<Vertex> drawer = new GraphDrawer();
        Vertex s = new Vertex(null, null, "S");

        //p1
        P1 p1 = new P1(s, drawer);
        this.runner.addThread(p1);

        this.runner.startAll();

        P2 p2 = new P2(p1.getObj(), drawer);
        P3 p3 = new P3(p1.getObj().getRight(), drawer);

        this.runner.addThread(p2);
        this.runner.addThread(p3);

        this.runner.startAll();


        P5 p5A = new P5(p2.getObj(), drawer);
        P4 p4_blue = new P4(p2.getObj().getRight(), drawer);
        P6 P6_blue = new P6(p3.getObj().getLeft(), drawer);
        P5 p5B = new P5(p3.getObj().getRight(), drawer);
        this.runner.addThread(p5A);
        this.runner.addThread(p4_blue);
        this.runner.addThread(P6_blue);
        this.runner.addThread(p5B);

        this.runner.startAll();

        P6 p6_blue_black = new P6(p4_blue.getObj().getRight(), drawer);
        P4 p4_black = new P4(p4_blue.getObj(), drawer);

        this.runner.addThread(p6_blue_black);
        this.runner.addThread(p4_black);

        this.runner.startAll();


        P6 p6_black_blue = new P6(p4_black.getObj().getRight(), drawer);
        P6 p6_black = new P6(p5B.getObj().getLeft(), drawer);

        this.runner.addThread(p6_black_blue);
        this.runner.addThread(p6_black);

        this.runner.startAll();

        System.out.println("done");
        drawer.draw(p6_black_blue.getObj());
    }
}
