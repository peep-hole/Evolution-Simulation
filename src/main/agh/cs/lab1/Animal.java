package agh.cs.lab1;

import java.util.LinkedList;
import java.util.List;

public class Animal {

    private MapDirection orientation;
    private Vector2d position;
    private final IWorldMap map;

    private List<IPositionChangeObserver> observers;

    private Animal(IWorldMap map, MapDirection direction, Vector2d position) {
        observers = new LinkedList<>();
        orientation = direction;
        this.map = map;
        this.position = position;
        if(!this.map.place(this)) {
            throw new IllegalStateException("Position " + position + " is occupied or out of map!");
        }

    }

    public Animal(IWorldMap map){
        this(map, MapDirection.NORTH, new Vector2d(2, 2));
    }

    public Animal(IWorldMap map, Vector2d initialPosition){
        this(map, MapDirection.NORTH, initialPosition);
    }

    public void addObserver(IPositionChangeObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer) {
        observers.remove(observer);
    }

    private void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        for(IPositionChangeObserver observer : observers) {
            observer.positionChanged(oldPosition, newPosition);
        }
    }

    public Vector2d getPosition() {
        return position;
    }

    public String toString(){
        switch (orientation) {
            case NORTH: return "^";
            case EAST: return ">";
            case SOUTH: return "v";
            case WEST: return "<";
        }
        throw new IllegalArgumentException();
    }

    public void move( MoveDirection direction){

        Vector2d oldPosition = position;

        switch(direction){

            case RIGHT:
                orientation = orientation.next();
                break;

            case LEFT:
                orientation = orientation.previous();
                break;

            case FORWARD:
                Vector2d wantedPlace1 = position.add(orientation.toUnitVector());
                if(map.canMoveTo(wantedPlace1)){
                    position = wantedPlace1;
                }
                break;

            case BACKWARD:
                Vector2d wantedPlace2 = position.add((orientation.toUnitVector()).opposite());
                if(map.canMoveTo(wantedPlace2)){
                    position = wantedPlace2;
                }
                break;
            default: throw new IllegalArgumentException();

        }

        Vector2d newPosition = position;
        if(!(oldPosition.equals(newPosition))) positionChanged(oldPosition, newPosition);
    }

    @Override
    public boolean equals(Object other) {
        if (other == null)
            return false;
        if(this == other)
            return true;
        if (!(other instanceof Animal))
            return false;
        Animal that = (Animal) other;
        return (position.x == that.position.x)&&(position.y==that.position.y);
    }

    @Override
    public int hashCode() {
        return 31* position.x + 13 * position.y;
    }

}
