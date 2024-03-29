package agh.cs.lab1.MapElements;


import agh.cs.lab1.Utilities.Vector2d;

/**
 * Interface that describe a map element and its requirements
 * Assumes that Vector2d is already implemented
 */
public interface IMapElement {


    /**
     * @return actual position of element on the map
     */
    Vector2d getPosition();


    /**
     * @return a String representation of an element
     */
    @Override
    String toString();
}
