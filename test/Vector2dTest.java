package test;   // powinien być pakiet jak w main, a test to folder

import main.lab1.Vector2d;

import org.junit.*;



public class Vector2dTest {
    //equalsTest

    @Test
    public void equalTest1(){   // lepiej testy jednej metody "produkcyjnej" trzymać w jednej metodzie
        Assert.assertEquals(new Vector2d(1,2), new Vector2d(1, 2));
    }

    @Test
    public void equalTest2(){
        Assert.assertEquals(new Vector2d(-5,-4), new Vector2d(-5, -4));
    }

    @Test
    public void toStringTest1(){
        Vector2d x = new Vector2d(1,2);
        Assert.assertEquals("(1, 2)", x.toString());
    }

    @Test
    public void toStringTest2(){
        Vector2d x = new Vector2d(0,-11);
        Assert.assertEquals("(0, -11)", x.toString());
    }

    @Test
    public void precedesTest1(){
        Vector2d x  = new Vector2d(1, 3);
        Vector2d y = new Vector2d(4, 8);
        Assert.assertTrue(x.precedes(y));
    }

    @Test
    public void precedesTest2(){
        Vector2d x  = new Vector2d(0, 0);
        Vector2d y = new Vector2d(0, 1);
        Assert.assertTrue(x.precedes(y));
    }

    @Test
    public void precedesTest3(){
        Vector2d x  = new Vector2d(1, 3);
        Vector2d y = new Vector2d(-1, 3);
        Assert.assertFalse(x.precedes(y));
    }

    @Test
    public void followsTest1(){
        Vector2d x  = new Vector2d(8, 8);
        Vector2d y = new Vector2d(4, 8);
        Assert.assertTrue(x.follows(y));
    }

    @Test
    public void followsTest2(){
        Vector2d x  = new Vector2d(0, 0);
        Vector2d y = new Vector2d(-1, 0);
        Assert.assertTrue(x.follows(y));
    }

    @Test
    public void followsTest3(){
        Vector2d x  = new Vector2d(8, 8);
        Vector2d y = new Vector2d(41, 8);
        Assert.assertFalse(x.follows(y));
    }

    @Test
    public void upperRightTest1(){
        Vector2d x  = new Vector2d(8, 3);
        Vector2d y = new Vector2d(4, 8);
        Assert.assertEquals(new Vector2d(8, 8), x.upperRight(y));
    }

    @Test
    public void upperRightTest2(){
        Vector2d x  = new Vector2d(-1, 0);
        Vector2d y = new Vector2d(-2, -8);
        Assert.assertEquals(new Vector2d(-1, 0), x.upperRight(y));
    }

    @Test
    public void lowerLeftTest1(){
        Vector2d x  = new Vector2d(8, 3);
        Vector2d y = new Vector2d(4, 8);
        Assert.assertEquals(new Vector2d(4, 3), x.lowerLeft(y));
    }

    @Test
    public void lowerLeftTest2(){
        Vector2d x  = new Vector2d(-8, 0);
        Vector2d y = new Vector2d(-7, 12);
        Assert.assertEquals(new Vector2d(-8, 0), x.lowerLeft(y));
    }

    @Test
    public void addTest1(){
        Vector2d x  = new Vector2d(8, 3);
        Vector2d y = new Vector2d(4, 8);
        Assert.assertEquals(new Vector2d(12, 11), x.add(y));
    }

    @Test
    public void addTest2(){
        Vector2d x  = new Vector2d(-8, 0);
        Vector2d y = new Vector2d(-2, 0);
        Assert.assertEquals(new Vector2d(-10, 0), x.add(y));
    }

    @Test
    public void subtractTest1(){
        Vector2d x  = new Vector2d(8, 3);
        Vector2d y = new Vector2d(4, 8);
        Assert.assertEquals(new Vector2d(4, -5), x.subtract(y));
    }

    @Test
    public void subtractTest2(){
        Vector2d x  = new Vector2d(0, 1);
        Vector2d y = new Vector2d(-4, -11);
        Assert.assertEquals(new Vector2d(4, 12), x.subtract(y));
    }

    @Test
    public void oppositeTest1(){
        Vector2d x  = new Vector2d(8, 3);
        Assert.assertEquals(new Vector2d(3, 8), x.opposite());
    }

    @Test
    public void oppositeTest2(){
        Vector2d x  = new Vector2d(-11, 0);
        Assert.assertEquals(new Vector2d(0, -11), x.opposite());
    }
}
