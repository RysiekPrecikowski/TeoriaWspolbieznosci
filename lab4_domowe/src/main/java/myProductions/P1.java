package myProductions;

import mesh.Vertex;
import production.AbstractProduction;
import production.PDrawer;


public class P1 extends AbstractProduction<Vertex> {

    public P1(Vertex _obj, PDrawer<Vertex> _drawer) {
        super(_obj, _drawer);
    }

    @Override
    public Vertex apply(Vertex s) {
        System.out.println("p1");

        return new Vertex("M");
    }
}