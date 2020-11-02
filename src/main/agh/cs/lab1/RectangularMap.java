package agh.cs.lab1;

import java.util.ArrayList;
import java.util.List;

public class RectangularMap implements IWorldMap {

    private final Vector2d upperRightCorner;
    private final Vector2d lowerLeftCorner;

    private List<Animal> animals;

    private MapVisualizer mapVisualizer = new MapVisualizer(this);

    RectangularMap(int width, int height) {
        upperRightCorner = new Vector2d(width-1, height-1);
        lowerLeftCorner = new Vector2d(0, 0);
        animals = new ArrayList<>();
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return ((position.follows(lowerLeftCorner))&&(position.precedes(upperRightCorner))&&!isOccupied(position));
    }

    @Override
    public boolean place(Animal animal) {

       if (animal.getPosition().precedes(lowerLeftCorner.add(new Vector2d(-1, -1))) ||
               animal.getPosition().follows(upperRightCorner.add(new Vector2d(1,1)))) {
           throw new IllegalArgumentException("\nAnimal must be put on the map! \nWanted position : "
                   + animal.getPosition() + ",\n  Map borders by by lower Left and upper right corners: "
                   + lowerLeftCorner + " ; " + upperRightCorner);
       }
       if(isOccupied(animal.getPosition())) {
           return false;
       }
       animals.add(animal);
       return true;
    }

    @Override
    public void run(MoveDirection[] directions) {
        int j = 0;
        for (MoveDirection direction : directions) {
            if (j == animals.size()) j = 0;
            animals.get(j++).move(direction);
        }
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        for(Animal animal : animals) {
            if(animal.getPosition().equals(position)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object objectAt(Vector2d position) {
        for(Animal animal : animals) {
            if(animal.getPosition().equals(position)) {
                return animal;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return mapVisualizer.draw(lowerLeftCorner, upperRightCorner);
    }
}
