package agh.cs.lab1;

import java.util.LinkedList;

public class RectangularMap extends AbstractWorldMap {

    RectangularMap(int width, int height) {
        upperRightCorner = new Vector2d(width-1, height-1);
        lowerLeftCorner = new Vector2d(0, 0);
        animals = new LinkedList<>();
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return ((position.follows(lowerLeftCorner))&&(position.precedes(upperRightCorner))&&super.canMoveTo(position));
    }

}
