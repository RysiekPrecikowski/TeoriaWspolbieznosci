package mesh;

import production.PDrawer;

import java.util.*;


class Key {

    public final int x;
    public final int y;

    public Key(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Key key = (Key) o;
        return x == key.x && y == key.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}

public class GraphDrawer implements PDrawer<Vertex> {

    @Override
    public void draw(Vertex v) {
        Map<Vertex, Key> graph = new HashMap<>();
        Set<Vertex> visited = new HashSet<>();
        Queue<Vertex> q = new LinkedList<>();

        graph.put(v, new Key(0, 0));
        visited.add(v);
        visited.add(null);
        q.add(v);

        int xMin = 0, xMax = 0, yMin = 0, yMax = 0;

        while (q.size() > 0){
            v = q.poll();

            int x = graph.get(v).x, y = graph.get(v).y;
            if (x < xMin)
                xMin = x;
            if (x > xMax)
                xMax = x;
            if (y > yMax)
                yMax = yMin;
            if (y < yMin)
                yMin = y;


            Vertex nV = v.getEast();
            if (!visited.contains(nV)){
                visited.add(nV);
                q.add(nV);

                graph.put(nV, new Key(graph.get(v).x + 1, graph.get(v).y));
            }

            nV = v.getNorth();
            if (!visited.contains(nV)){
                visited.add(nV);
                q.add(nV);

                graph.put(nV, new Key(graph.get(v).x, graph.get(v).y + 1));
            }

            nV = v.getWest();
            if (!visited.contains(nV)){
                visited.add(nV);
                q.add(nV);

                graph.put(nV, new Key(graph.get(v).x - 1, graph.get(v).y));
            }

            nV = v.getSouth();
            if (!visited.contains(nV)){
                visited.add(nV);
                q.add(nV);

                graph.put(nV, new Key(graph.get(v).x, graph.get(v).y - 1));

            }

        }

        Map<Key, Vertex> idk = new HashMap<>();

        for(Vertex vertex: graph.keySet()){
            idk.put(graph.get(vertex), vertex);
        }



        System.out.println();

        for (int j = yMax; j >= yMin ; j--) {
            StringBuilder nextLine = new StringBuilder();
            for (int i = xMin; i <= xMax ; i++) {
                if (i == xMin){
                    System.out.printf(" ");
                }
                Key cords = new Key(i, j);

                if (idk.containsKey(cords)){
                    System.out.printf("M");
                    if (idk.get(cords).getEast() != null)
                        System.out.printf(" - ");
                    else
                        System.out.printf("   ");

                    if (idk.get(cords).getSouth() != null)
                        nextLine.append(" |  ");
                    else
                        nextLine.append("    ");

                } else {
                    System.out.printf("    ");
                    nextLine.append  ("    ");
                }

            }
            System.out.printf("\n%s\n", nextLine.toString());
        }
    }
}
