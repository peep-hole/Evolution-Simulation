package agh.cs.lab1;

import java.util.List;

abstract class AbstractWorldMap implements IWorldMap {

    protected List<Animal> animals;

    protected Vector2d lowerLeftCorner;
    protected Vector2d upperRightCorner;

    protected MapVisualizer mapVisualizer = new MapVisualizer(this);


    @Override
    public boolean place(Animal animal) {

        if (!canMoveTo(animal.getPosition())) {
            return false;
        }
        animals.add(animal);
        return true;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return(!isOccupied(position));
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) instanceof Animal;
    }

    @Override
    public String toString() {
        return mapVisualizer.draw(lowerLeftCorner, upperRightCorner);
    }

    @Override
    public Object objectAt(Vector2d position) {
        for (Animal animal : animals) {
            if (animal.getPosition().equals(position)) {
                return animal;
            }
        }
        return  null;
    }


}
