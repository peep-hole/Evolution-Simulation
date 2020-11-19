package agh.cs.lab1;

import java.util.LinkedList;

public class RectangularMap extends AbstractWorldMap {

    private final Vector2d upperRightCorner;
    private final Vector2d lowerLeftCorner;

    RectangularMap(int width, int height) { // public
        upperRightCorner = new Vector2d(width-1, height-1);
        lowerLeftCorner = new Vector2d(0, 0);
        animals = new LinkedList<>();   // czemu mapa abstrakcyjna tego nie stworzyła?
    }

    public Vector2d getUpperRightCorner() {
        return upperRightCorner;
    }

    public Vector2d getLowerLeftCorner() {
        return lowerLeftCorner;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return ((position.follows(lowerLeftCorner))&&(position.precedes(upperRightCorner))&&super.canMoveTo(position));
    }

}
