package agh.cs.lab1;

public class Animal{

    private MapDirection orientation;
    private Vector2d position;
    private final IWorldMap map;


    public Animal(IWorldMap map){
        orientation = MapDirection.NORTH;
        position = new Vector2d(2, 2);
        this.map = map;
        if(!this.map.place(this)) {
            throw new IllegalStateException("Position " + position + " is occupied!");
        }
    }

    public Animal(IWorldMap map, Vector2d initialPosition){
        orientation = MapDirection.NORTH;
        position = initialPosition;
        this.map = map;
        if(!this.map.place(this)) {
            throw new IllegalStateException("Position " + position + " is occupied!");
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
        switch(direction){

            case RIGHT:
                orientation = orientation.next();
                break;

            case LEFT:
                orientation = orientation.previous();
                break;

            case FORWARD:
                Vector2d tmp1 = position.add(orientation.toUnitVector()); // temporary position
                if(map.canMoveTo(tmp1)){
                    position = tmp1;
                }
                break;

            case BACKWARD:
                Vector2d tmp2 = position.add((orientation.toUnitVector()).opposite()); // temporary position
                if(map.canMoveTo(tmp2)){
                    position = tmp2;
                }
                break;
            default: throw new IllegalArgumentException();

        }
    }

}
