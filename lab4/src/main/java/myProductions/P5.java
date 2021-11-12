package myProductions;


import mesh.Vertex;
import production.AbstractProduction;
import production.PDrawer;

public class P5 extends AbstractProduction<Vertex> {

    public P5(Vertex _obj, PDrawer<Vertex> _drawer) {
        super(_obj, _drawer);
    }

    @Override
    public Vertex apply(Vertex t1) {
        System.out.println("p5");
        t1.setLabel("Iel1");
        return t1;
    }
}
