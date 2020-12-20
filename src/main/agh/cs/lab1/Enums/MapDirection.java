package agh.cs.lab1.Enums;

import agh.cs.lab1.Utilities.Vector2d;

import java.util.Map;
import java.util.Random;

public enum MapDirection {
    NORTH("Północ", new Vector2d(0,1)),
    NORTH_WEST("Północny-wschód", new Vector2d(1,1)),
    WEST("Zachód", new Vector2d(-1,0)),
    SOUTH_WEST("Południowy-wschód", new Vector2d(1,-1)),
    SOUTH("Południe", new Vector2d(0,-1)),
    SOUTH_EAST("Południowy-wschód", new Vector2d(-1, -1)),
    EAST("Wschód", new Vector2d(1,0)),
    NORTH_EAST("Północny-zachód", new Vector2d(-1,1));



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
            case NORTH:
                return NORTH_EAST;
            case NORTH_EAST:
                return EAST;
            case EAST:
                return SOUTH_EAST;
            case SOUTH_EAST:
                return SOUTH;
            case SOUTH:
                return SOUTH_WEST;
            case SOUTH_WEST:
                return WEST;
            case WEST:
                return NORTH_WEST;
            case NORTH_WEST:
                return NORTH;
        }
        throw new IllegalArgumentException();
    }
    public MapDirection previous(){
        switch(this){
            case NORTH:
                return NORTH_WEST;
            case NORTH_WEST:
                return WEST;
            case WEST:
                return SOUTH_WEST;
            case SOUTH_WEST:
                return SOUTH;
            case SOUTH:
                return SOUTH_EAST;
            case SOUTH_EAST:
                return EAST;
            case EAST:
                return NORTH_EAST;
            case NORTH_EAST:
                return NORTH;
        }
        throw new IllegalArgumentException();
    }
    public Vector2d toUnitVector(){
        return this.unitVector;
    }




}
