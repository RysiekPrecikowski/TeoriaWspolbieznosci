package myProductions;

import mesh.Vertex;
import production.AbstractProduction;
import production.PDrawer;


public class PJ extends AbstractProduction<Vertex> {

    public PJ(Vertex _obj, PDrawer<Vertex> _drawer) {
        super(_obj, _drawer);
    }

    @Override
    public Vertex apply(Vertex s) {
        System.out.println("pJ");

        Vertex t = s.getNorth().getWest().getSouth();
        s.setWest(t);
        t.setEast(s);

        return t;
    }
}