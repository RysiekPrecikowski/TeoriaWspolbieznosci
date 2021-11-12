package myProductions;

import mesh.Vertex;
import production.AbstractProduction;
import production.PDrawer;


public class PS extends AbstractProduction<Vertex> {

    public PS(Vertex _obj, PDrawer<Vertex> _drawer) {
        super(_obj, _drawer);
    }

    @Override
    public Vertex apply(Vertex s) {
        System.out.println("pS");

        Vertex t = new Vertex(null, null, s, null, "M");
        s.setSouth(t);

        return t;
    }
}