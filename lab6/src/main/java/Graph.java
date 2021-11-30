import java.util.*;
import java.util.List;

public class Graph {
    int n;


    enum Type {E1, E2, E3, E4, E5}

    abstract class Vertex{
        int r;
        abstract public String toString();
    }

    class A extends Vertex{
        int i, k;

        public A(int i, int k) {
            r = i;
            this.i = i;
            this.k = k;
        }

        @Override
        public String toString() {
            return "\"A_" + (i+1) + "," + (k+1)+"\"";
        }
    }

    class B extends Vertex{
        int i, j, k;

        public B(int i, int j, int k) {
            r = i;
            this.i = i;
            this.j = j;
            this.k = k;
        }

        @Override
        public String toString() {
            return "\"B_" + (i+1) + "," + (j+1) + "," + (k+1)+"\"";
        }
    }

    class C extends Vertex{
        int i, j, k;

        public C(int i, int j, int k) {
            r = i;
            this.i = i;
            this.j = j;
            this.k = k;
        }

        @Override
        public String toString() {
            return "\"C_" + (i+1) + "," + (j+1) + "," + (k+1)+"\"";
        }
    }



    class Edge{
        Type type;
        Vertex v1, v2;

        public Edge(Type type, Vertex v1, Vertex v2) {
            this.type = type;
            this.v1 = v1;
            this.v2 = v2;
        }
    }


    List<Edge> edges = new LinkedList<>();
    Map<Integer, Map<Integer, Vertex>> VA = new HashMap<>();
    Map<Integer, Map<Integer, Map<Integer, Vertex>>> VB = new HashMap<>();
    Map<Integer, Map<Integer, Map<Integer, Vertex>>>  VC = new HashMap<>();



    public Graph(int n) {
        this.n = n;

        for(int i = 0 ; i < n-1 ; i++){
            VA.put(i, new HashMap<>());
            VB.put(i, new HashMap<>());
            VC.put(i, new HashMap<>());
            for (int k = i+1 ; k < n ; k++){
                VA.get(i).put(k, new A(i, k));
            }

            for (int j = i ; j < n+1 ; j++){
                VB.get(i).put(j, new HashMap<>());
                VC.get(i).put(j, new HashMap<>());
                for (int k = i+1 ; k < n ; k++){
                    VB.get(i).get(j).put(k, new B(i, j, k));
                    VC.get(i).get(j).put(k, new C(i, j, k));

                    edges.add(new Edge(Type.E1, VA.get(i).get(k), VB.get(i).get(j).get(k)));
                    edges.add(new Edge(Type.E2, VB.get(i).get(j).get(k), VC.get(i).get(j).get(k)));
                }
            }
        }

        for(int i1 = 0 ; i1 < n-1 ; i1++){
            for (int i2 = 0 ; i2 < n-1 ; i2 ++) {
                for (int j1 = i1; j1 < n + 1; j1++) {
                    for (int j2 = i2; j2 < n + 1; j2++) {
                        for (int k1 = i1 + 1; k1 < n; k1++) {
                            for (int k2 = i2 + 1; k2 < n; k2++) {

                                if (j1 == j2 && k1 == i2 && i1 == i2-1 && j1 != i2)
                                    edges.add(new Edge(Type.E3, VC.get(i1).get(j1).get(k1), VB.get(i2).get(j2).get(k2)));

                                if (i2 == j1 && (k2 == k1 || i2 == k1) && i1 == i2 -1 && j2 == i2) // j2 tylko przez te dzikie fory, zehy tylko raz sie wykonywalo
                                    edges.add(new Edge(Type.E4, VC.get(i1).get(j1).get(k1), VA.get(i2).get(k2)));

                                if (i1 == i2 - 1 && j1 == j2 && k1 == k2 && i2 != j1)
                                    edges.add(new Edge(Type.E5, VC.get(i1).get(j1).get(k1), VC.get(i2).get(j2).get(k2)));

                            }
                        }
                    }
                }
            }
        }

    }

    void print_sth(Set<Vertex> a, int g, int b){
        StringBuilder rank = new StringBuilder("{rank = same; ");
        for (Vertex v : a){
            String color = Integer.toHexString(255 / n * (r+1)) + Integer.toHexString(g) + Integer.toHexString(b);
            System.out.println(v + "[fillcolor=\"#"+color+"\" style=filled]");
            rank.append(v).append(";");
        }
        rank.append("}");
        System.out.println(rank);
    }

    void printcolors(){
        print_sth(a, 50, 50);
        print_sth(b, 150, 150);
        print_sth(c, 100, 100);

            a.clear();
            b.clear();
            c.clear();

    }

    void add_to_set(Vertex v){
        if (v.r != r)
            return;
        if (A.class.equals(v.getClass())) {
            a.add( v);
        } else if (B.class.equals(v.getClass())) {
            b.add( v);
        } else {
            c.add(v);
        }
    }

    Set<Vertex> a = new HashSet<>();
    Set<Vertex> b = new HashSet<>();
    Set<Vertex> c = new HashSet<>();
    int r = 0;
    public void print(){
        System.out.println("digraph g {");

        for (Edge edge : edges) {
            if (edge.v1.r > r) {
                printcolors();
                r = edge.v1.r;
            }

            System.out.println(edge.v1 + " -> " + edge.v2);

            add_to_set(edge.v1);
            add_to_set(edge.v2);
        }

        printcolors();

        System.out.println("}");
    }
}
