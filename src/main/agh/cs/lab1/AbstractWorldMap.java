package agh.cs.lab1;

import java.util.LinkedHashMap;
import java.util.Map;

abstract class AbstractWorldMap implements IWorldMap , IPositionChangeObserver {

    protected Map<Vector2d, Animal> animals = new LinkedHashMap<>();

    private final MapVisualizer mapVisualizer = new MapVisualizer(this);

    @Override
    public void positionChanged(Vector2d oldPosition,Vector2d newPosition) {
        Animal animal = (Animal)objectAt(oldPosition);
        animals.remove(oldPosition);
        animals.put(newPosition, animal);
    }


    @Override
    public boolean place(Animal animal) {

        animal.addObserver(this);

        if (!canMoveTo(animal.getPosition())) {
            return false;
        }
        animals.put(animal.getPosition(), animal);
        return true;
    }

    public abstract Vector2d getUpperRightCorner();
    public abstract Vector2d getLowerLeftCorner();

    @Override
    public boolean canMoveTo(Vector2d position) {
        return(!(objectAt(position) instanceof Animal));
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    @Override
    public String toString() {
      return this.mapVisualizer.draw(getLowerLeftCorner(),getUpperRightCorner());
    }

    @Override
    public Object objectAt(Vector2d position) {
        return animals.get(position);
    }


}
