package pt.isel.poo.model;

//TODO: solve access dependacy
public class Position {

    private int x, y;

    public Position (int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX () {
        return x;
    }

    public int getY () {
        return y;
    }

    public void setPos (int x, int y) {
        this.x = x;
        this.y = y;
    }
}
