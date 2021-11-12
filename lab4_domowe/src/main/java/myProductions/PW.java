package myProductions;

import mesh.Vertex;
import production.AbstractProduction;
import production.PDrawer;


public class PW extends AbstractProduction<Vertex> {

    public PW(Vertex _obj, PDrawer<Vertex> _drawer) {
        super(_obj, _drawer);
    }

    @Override
    public Vertex apply(Vertex s) {
        System.out.println("pW");

        Vertex t = new Vertex(null, s, null, null, "M");
        s.setWest(t);

        return t;
    }
}