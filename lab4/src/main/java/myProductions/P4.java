package myProductions;


import mesh.Vertex;
import production.AbstractProduction;
import production.PDrawer;

public class P4 extends AbstractProduction<Vertex> {

    public P4(Vertex _obj, PDrawer<Vertex> _drawer) {
        super(_obj, _drawer);
    }

    @Override
    public Vertex apply(Vertex t2) {
        System.out.println("p4");
        Vertex t2Prim = new Vertex(t2, t2.getRight(), "T2");
        t2.setRight(t2Prim);
        return t2;
    }
}
