package agh.cs.lab1;
import agh.cs.lab1.MapDirection;

import org.junit.*;

public class MapDirectionTest {
    @Test
    public void nextTestNorthEast(){
      Assert.assertEquals(MapDirection.EAST, MapDirection.NORTH.next());
    }

    @Test
    public void nextTestEastSouth(){
        Assert.assertEquals(MapDirection.SOUTH, MapDirection.EAST.next());
    }

    @Test
    public void nextTestSouthWest(){
        Assert.assertEquals(MapDirection.WEST, MapDirection.SOUTH.next());
    }

    @Test
    public void nextTestWestNorth(){
        Assert.assertEquals(MapDirection.NORTH, MapDirection.WEST.next());
    }

    @Test
    public void prevNorthWest(){
        Assert.assertEquals(MapDirection.WEST, MapDirection.NORTH.previous());
    }

    @Test
    public void prevWestSouth(){
        Assert.assertEquals(MapDirection.SOUTH, MapDirection.WEST.previous());
    }

    @Test
    public void prevSouthEast(){
        Assert.assertEquals(MapDirection.EAST, MapDirection.SOUTH.previous());
    }

    @Test
    public void prevEastNorth(){
        Assert.assertEquals(MapDirection.NORTH, MapDirection.EAST.previous());
    }

}
