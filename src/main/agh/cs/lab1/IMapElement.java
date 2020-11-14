package agh.cs.lab1;


/**
 * Interface responsible for appropriate map-element integration
 * Assumes that Vector2d class is defined
 */
public interface IMapElement {

    /**
     * @return returns current position of element.
     */

    Vector2d getPosition();
}
