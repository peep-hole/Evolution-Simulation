package agh.cs.lab1;

public enum MapDirection {
    NORTH("Północ", new Vector2d(0,1)),
    SOUTH("Południe", new Vector2d(0,-1)),
    EAST("Wschód", new Vector2d(1,0)),
    WEST("Zachód", new Vector2d(-1,0));

    private final Vector2d unitVector;

    private final String direction;

    MapDirection(String stringRepresentation, Vector2d unitVector){
        this.unitVector = unitVector;
        this.direction = stringRepresentation;
    }

    public String toString() {
        return this.direction;
    }
    public MapDirection next(){
        switch(this){
            case WEST:
                return NORTH;
            case SOUTH:
                return WEST;
            case EAST:
                return SOUTH;
            case NORTH:
                return EAST;
        }
        throw new IllegalArgumentException();
    }
    public MapDirection previous(){
        switch(this){
            case WEST:
                return SOUTH;
            case SOUTH:
                return EAST;
            case EAST:
                return NORTH;
            case NORTH:
                return WEST;
        }
        throw new IllegalArgumentException();
    }
    public Vector2d toUnitVector(){
        return this.unitVector;
    }


}
