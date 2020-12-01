package agh.cs.lab1;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.lang.Math;

public class GrassField extends AbstractWorldMap {

    private final List<Grass> grasses;

    private final MapBoundary boundary;

    public GrassField(int n) {

        boundary = new MapBoundary();

        if( n < 0 ) throw new IllegalArgumentException("Grass amount cannot be negative");

        grasses = new LinkedList<>();

        Random generator = new Random();
        int range = Math.round((float)Math.sqrt(10*n));

        for(int i=0; i<n; i++) {

            int randomX = generator.nextInt(range);

            int randomY = generator.nextInt(range);

            Vector2d grassPosition = new Vector2d(randomX, randomY);

            // Adding grass only if position is not occupied by other grass so total grass amount on map equals n
            i--;
            if(!(objectAt(grassPosition) instanceof Grass)) {
                grasses.add(new Grass(grassPosition));
                boundary.addElement(grasses.get(grasses.size()-1));
                i++;
            }

        }

    }

    @Override
    public void positionChanged(Vector2d oldPosition,Vector2d newPosition) {
        super.positionChanged(oldPosition, newPosition);
    }

    @Override
    public boolean place(Animal animal) {
        if(super.place(animal)) {
            boundary.addElement(animal);
            return true;
        }
        return false;
    }



    public Vector2d getUpperRightCorner() {
        return boundary.upperRight();
    }

    public Vector2d getLowerLeftCorner() {
        return boundary.lowerLeft();
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

