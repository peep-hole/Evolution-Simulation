package agh.cs.lab1;

public class Animal implements IMapElement{

    private MapDirection orientation;
    private Vector2d position;
    private final IWorldMap map;

    private Animal(IWorldMap map, MapDirection direction, Vector2d position) {
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
    }

}
