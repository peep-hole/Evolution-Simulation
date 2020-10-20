package main.lab1;

public enum MapDirection {
    NORTH, SOUTH, EAST, WEST;

    public String toString() {
        switch (this) {
            case NORTH:
                return "Północ";
            case WEST:
                return "Zachód";
            case SOUTH:
                return "Połódnie";
            case EAST:
                return "Wschód";
        }
        return "NoSuchDirection";
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
        return null;
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
        return null;
    }
    public Vector2d toUnitVector(){
        switch(this){
            case WEST:
                return new Vector2d(-1,0);
            case SOUTH:
                return new Vector2d(0,-1);
            case EAST:
                return new Vector2d(1,0);
            case NORTH:
                return new Vector2d(0,1);
        }
        return new Vector2d(0,0);
    }


}
