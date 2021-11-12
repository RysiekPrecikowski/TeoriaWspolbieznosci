package myProductions;


import mesh.Vertex;
import production.AbstractProduction;
import production.PDrawer;

public class P6 extends AbstractProduction<Vertex> {

    public P6(Vertex _obj, PDrawer<Vertex> _drawer) {
        super(_obj, _drawer);
    }

    @Override
    public Vertex apply(Vertex t2) {
        System.out.println("p6");
        t2.setLabel("Iel2");
        return t2;
    }
}
