import mesh.GraphDrawer;
import mesh.Vertex;
import myProductions.P1;
import myProductions.PS;
import myProductions.PW;
import parallelism.BlockRunner;
import production.PDrawer;

public class Executor extends Thread {
    
    private final BlockRunner runner;
    
    public Executor(BlockRunner _runner){
        this.runner = _runner;
    }

    @Override
    public void run() {

        PDrawer<Vertex> drawer = new GraphDrawer();

        Vertex s = new Vertex("S");

        P1 p1 = new P1(s, drawer);
        this.runner.addThread(p1);

        runner.startAll();

        PS ps = new PS(p1.getObj(), drawer);
        PW pw = new PW(p1.getObj(), drawer);

        this.runner.addThread(ps);
        this.runner.addThread(pw);

        runner.startAll();

        PW pw2 = new PW(pw.getObj(), drawer);
        PS ps1 = new PS(pw.getObj(), drawer);


        this.runner.addThread(pw2);
        this.runner.addThread(ps1);


        runner.startAll();


        PS ps2 = new PS(ps1.getObj(), drawer);

        this.runner.addThread(ps2);

        runner.startAll();

        System.out.println("\n\n");
        drawer.draw(p1.getObj());

    }
}
