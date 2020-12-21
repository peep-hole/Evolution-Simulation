package agh.cs.lab1.MapElements;

import agh.cs.lab1.Utilities.Vector2d;

public class Grass implements IMapElement {

    private final Vector2d position;
    private final int nutritionalValue;

    public Grass(Vector2d position, int nutritionalValue) {
        this.position = position;
        this.nutritionalValue = nutritionalValue;
    }

    public Vector2d getPosition() {
        return position;
    }

    public int getNutritionalValue() {
        return nutritionalValue;
    }

    @Override
    public String toString() {
        return "*";
    }



}
