package mesh;

public class Vertex {

    //label
    String label;
    //links to adjacent elements
    Vertex west;
    Vertex east;
    Vertex north;
    Vertex south;

    //methods for adding links


    public Vertex(Vertex west, Vertex east, Vertex north, Vertex south, String label) {
        this.label = label;
        this.west = west;
        this.east = east;
        this.north = north;
        this.south = south;
    }

    public Vertex(String label) {
        this.label = label;
    }

    public Vertex getNorth() {
        return north;
    }

    public void setNorth(Vertex north) {
        this.north = north;
    }

    public Vertex getSouth() {
        return south;
    }

    public void setSouth(Vertex south) {
        this.south = south;
    }

    public Vertex getWest() {
        return this.west;
    }

    public void setWest(Vertex west) {
        this.west = west;
    }

    public Vertex getEast() {
        return this.east;
    }

    public void setEast(Vertex east) {
        this.east = east;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String _lab) {
        this.label = _lab;
    }
}
