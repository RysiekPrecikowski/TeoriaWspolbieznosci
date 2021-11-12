import mesh.GraphDrawer;
import mesh.Vertex;
import myProductions.P1;
import myProductions.PJ;
import myProductions.PS;
import myProductions.PW;
import parallelism.BlockRunner;
import production.IProduction;
import production.PDrawer;

import java.util.LinkedList;
import java.util.List;

public class Executor extends Thread {
    
    private final BlockRunner runner;
    
    public Executor(BlockRunner _runner){
        this.runner = _runner;
    }

    @Override
    public void run(){
        run3x3OneByOne();
        run3x3();
        runNxN(5);
    }

    public void runNxN(int n){
        PDrawer<Vertex> drawer = new GraphDrawer();

        List<IProduction<Vertex>> productions = new LinkedList<>();

        Vertex s = new Vertex("S");

        P1 p1 = new P1(s, drawer);
        runner.addThread(p1);

        runner.startAll();

        drawer.draw(p1.getObj());

        productions.add(p1);

        for (int i = 0 ; i < n - 1 ; i++){
            List<IProduction<Vertex>> nextProductions = new LinkedList<>();

            PW pw = new PW(productions.get(0).getObj(), drawer);
            runner.addThread(pw);
            nextProductions.add(pw);

            for(IProduction<Vertex> production : productions){
                Vertex v = production.getObj();

                PS ps = new PS(v, drawer);
                runner.addThread(ps);
                nextProductions.add(ps);

                if (v.getNorth() != null && v.getNorth().getWest() == null) {
                    runner.addThread(new PJ(v.getNorth(), drawer));
                }

            }
            runner.startAll();
            productions = nextProductions;

            drawer.draw(p1.getObj());
        }

        IProduction<Vertex> westmost = productions.remove(0);
        IProduction<Vertex> southmost = null;


        for (int i = 0 ; i < n - 1 ; i++){
            List<IProduction<Vertex>> nextProductions = new LinkedList<>();

            if (southmost != null)
                runner.addThread(new PJ(southmost.getObj(), drawer));
            southmost = productions.get(productions.size() -1);


            for(IProduction<Vertex> production : productions){
                Vertex v = production.getObj();

                if (v != productions.get(productions.size() -1).getObj()){
                    PS ps = new PS(v, drawer);
                    runner.addThread(ps);
                    nextProductions.add(ps);
                }

                if (v.getNorth() != null && v.getNorth().getWest() == null) {
                    runner.addThread(new PJ(v.getNorth(), drawer));
                }
            }
            runner.addThread(westmost = new PS(westmost.getObj(), drawer));

            runner.startAll();

            productions = nextProductions;

            drawer.draw(p1.getObj());
        }


        runner.addThread(new PJ(southmost.getObj(), drawer));
        runner.startAll();

        System.out.println("\n\n");
        drawer.draw(p1.getObj());

    }

    public void run3x3() {
        PDrawer<Vertex> drawer = new GraphDrawer();

        Vertex s = new Vertex("S");

        P1 p1 = new P1(s, drawer);
        this.runner.addThread(p1);

        runner.startAll();


        PW pw1 = new PW(p1.getObj(), drawer);
        PS ps1 = new PS(p1.getObj(), drawer);

        runner.addThread(pw1);
        runner.addThread(ps1);

        runner.startAll();

        System.out.println("\n\n");
        drawer.draw(p1.getObj());


        PW pw2 = new PW(pw1.getObj(), drawer);
        PS ps2 = new PS(ps1.getObj(), drawer);
        PS ps3 = new PS(pw1.getObj(), drawer);

        runner.addThread(pw2);
        runner.addThread(ps2);
        runner.addThread(ps3);

        runner.startAll();

        System.out.println("\n\n");
        drawer.draw(p1.getObj());


        PJ pj1 = new PJ(ps1.getObj(), drawer);
        PS ps4 = new PS(ps3.getObj(), drawer);
        PS ps5 = new PS(pw2.getObj(), drawer);

        runner.addThread(pj1);
        runner.addThread(ps4);
        runner.addThread(ps5);

        runner.startAll();

        System.out.println("\n\n");
        drawer.draw(p1.getObj());

        PJ pj2 = new PJ(ps2.getObj(), drawer);
        PS ps6 = new PS(ps5.getObj(), drawer);
        PJ pj3 = new PJ(ps3.getObj(), drawer);

        runner.addThread(pj2);
        runner.addThread(ps6);
        runner.addThread(pj3);

        runner.startAll();

        System.out.println("\n\n");
        drawer.draw(p1.getObj());

        PJ pj4 = new PJ(ps4.getObj(), drawer);

        runner.addThread(pj4);

        runner.startAll();

        System.out.println("\n\n");
        drawer.draw(p1.getObj());

    }

    private void runOne(IProduction<Vertex> p){
        runner.addThread(p);
        runner.startAll();
    }

    public void run3x3OneByOne() {
        PDrawer<Vertex> drawer = new GraphDrawer();

        Vertex s = new Vertex("S");

        P1 p1 = new P1(s, drawer);
        runOne(p1);

        PW pw1 = new PW(p1.getObj(), drawer);
        runOne(pw1);

        PS ps1 = new PS(p1.getObj(), drawer);
        runOne(ps1);

        PW pw2 = new PW(pw1.getObj(), drawer);
        runOne(pw2);

        PS ps2 = new PS(ps1.getObj(), drawer);
        runOne(ps2);

        PS ps3 = new PS(pw1.getObj(), drawer);
        runOne(ps3);

        PJ pj1 = new PJ(ps1.getObj(), drawer);
        runOne(pj1);

        PS ps4 = new PS(ps3.getObj(), drawer);
        runOne(ps4);

        PS ps5 = new PS(pw2.getObj(), drawer);
        runOne(ps5);

        PJ pj2 = new PJ(ps2.getObj(), drawer);
        runOne(pj2);

        PS ps6 = new PS(ps5.getObj(), drawer);
        runOne(ps6);

        PJ pj3 = new PJ(ps3.getObj(), drawer);
        runOne(pj3);

        PJ pj4 = new PJ(ps4.getObj(), drawer);
        runOne(pj4);


        System.out.println("\n\n");
        drawer.draw(p1.getObj());
    }
}
