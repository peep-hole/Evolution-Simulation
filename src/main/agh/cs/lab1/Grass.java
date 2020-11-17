package agh.cs.lab1;

public class Grass {

    private final Vector2d position;

    public Grass(Vector2d position) {
        this.position = position;
    }

    public Vector2d getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "*";
    }

    @Override
    public boolean equals(Object other) {
        if (other == null)
            return false;
        if(this == other)
            return true;
        if (!(other instanceof Grass))
            return false;
        Grass that = (Grass) other;
        return (position.x == that.position.x)&&(position.y==that.position.y);
    }

    @Override
    public int hashCode() {
        return 23* position.x + 13 * position.y;
    }

}
