package agh.cs.lab1;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.lang.Math;

public class GrassField extends AbstractWorldMap {

    private final List<Grass> grasses;

    private Vector2d upperRightCorner;
    private Vector2d lowerLeftCorner;

    public GrassField(int n) {

        if( n < 0 ) throw new IllegalArgumentException("Grass amount cannot be negative");

        grasses = new LinkedList<>();

        Random generator = new Random();
        int range = Math.round((float)Math.sqrt(10*n));

        int maxX = 0, maxY = 0;

        for(int i=0; i<n; i++) {

            // setting up map borders
            int randomX = generator.nextInt(range);
            if(randomX > maxX)  maxX = randomX;

            int randomY = generator.nextInt(range);
            if(randomY > maxY)  maxY = randomY;

            Vector2d grassPosition = new Vector2d(randomX, randomY);

            // Adding grass only if position is not occupied by other grass so total grass amount on map equals n
            i--;
            if(!(objectAt(grassPosition) instanceof Grass)) {
                grasses.add(new Grass(grassPosition));
                i++;
            }

        }
        upperRightCorner = new Vector2d(maxX, maxY);
        lowerLeftCorner = new Vector2d(0, 0);

    }

    public void updateMapSizeIfNeeded(Vector2d newPosition) {
        // changing map borders if needed
        if(!newPosition.precedes(upperRightCorner)) {
            upperRightCorner = new Vector2d(Math.max(newPosition.x, upperRightCorner.x), Math.max(newPosition.y, upperRightCorner.y));
        }
        if(!newPosition.follows(lowerLeftCorner)) {
            lowerLeftCorner = new Vector2d(Math.min(newPosition.x, lowerLeftCorner.x), Math.min(newPosition.y, lowerLeftCorner.y));
        }
    }

    @Override
    public void positionChanged(Vector2d oldPosition,Vector2d newPosition) {
        super.positionChanged(oldPosition, newPosition);

        updateMapSizeIfNeeded(newPosition);
    }

    @Override
    public boolean place(Animal animal) {
        boolean result = super.place(animal);

        if(result) {
            updateMapSizeIfNeeded(animal.getPosition());
        }

        return result;
    }



    public Vector2d getUpperRightCorner() {
        return upperRightCorner;
    }

    public Vector2d getLowerLeftCorner() {
        return lowerLeftCorner;
    }


    @Override
    public Object objectAt(Vector2d position) {
        Object result = super.objectAt(position);
        if (result instanceof Animal) {
            return result;
        }
        for(Grass grass: grasses) {
            if(grass.getPosition().equals(position)) {
                return grass;
            }
        }
        return null;
    }

}

