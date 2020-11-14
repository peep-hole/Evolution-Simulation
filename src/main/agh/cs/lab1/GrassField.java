package agh.cs.lab1;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.lang.Math;

public class GrassField extends AbstractWorldMap {

    private final List<Grass> grasses;

    public GrassField(int n) {

        if( n < 0 ) throw new IllegalArgumentException("Grass amount can not be negative");

        animals = new LinkedList<>();
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

            // Adding grass only if position is not occupied by other grass so total grass amount on map is equal n
            i--;
            if(!(objectAt(grassPosition) instanceof Grass)) {
                grasses.add(new Grass(grassPosition));
                i++;
            }

        }
        upperRightCorner = new Vector2d(maxX, maxY);
        lowerLeftCorner = new Vector2d(0, 0);

    }

    @Override
    public boolean canMoveTo(Vector2d position) {

        boolean result = super.canMoveTo(position);
        if(!result) return false;

        // changing map borders if needed
        if(!position.precedes(upperRightCorner)) {
            upperRightCorner = new Vector2d(Math.max(position.x, upperRightCorner.x), Math.max(position.y, upperRightCorner.y));
        }
        if(!position.follows(lowerLeftCorner)) {
            lowerLeftCorner = new Vector2d(Math.min(position.x, lowerLeftCorner.x), Math.min(position.y, lowerLeftCorner.y));
        }

        return true;
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
        return result;
    }




}

