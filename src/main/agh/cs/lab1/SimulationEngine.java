package agh.cs.lab1;

import java.util.ArrayList;
import java.util.List;

public class SimulationEngine implements IEngine{

    private final MoveDirection[] directions;
    private final List<Animal> animals;

    public SimulationEngine(MoveDirection[] directions, AbstractWorldMap map, Vector2d[] positions) {
        this.directions = directions;
        animals = new ArrayList<>();

        for(Vector2d position : positions) {
            animals.add(new Animal(map, position));
        }
    }

    @Override
    public void run() {
        int j = 0;
        for (MoveDirection direction : directions) {
            if (j == animals.size()) j = 0;
            animals.get(j++).move(direction);
        }
    }

    public Vector2d whereIs(int animalNumber) {
        return animals.get(animalNumber).getPosition();
    }

}
