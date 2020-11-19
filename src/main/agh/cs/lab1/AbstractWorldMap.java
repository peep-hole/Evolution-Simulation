package agh.cs.lab1;

import java.util.List;

abstract class AbstractWorldMap implements IWorldMap {

    protected List<Animal> animals; // to pole może być finalne

    protected MapVisualizer mapVisualizer = new MapVisualizer(this);    // to pole może być finalne i prywatne

    @Override
    public boolean place(Animal animal) {

        if (!canMoveTo(animal.getPosition())) {
            return false;
        }
        animals.add(animal);
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
        for (Animal animal : animals) {
            if (animal.getPosition().equals(position)) {
                return animal;
            }
        }
        return  null;
    }


}
