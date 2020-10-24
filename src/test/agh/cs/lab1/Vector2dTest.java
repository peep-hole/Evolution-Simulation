package agh.cs.lab1;

import agh.cs.lab1.Vector2d;

import org.junit.*;



public class Vector2dTest {
    //equalsTest

    @Test
    public void equalTest() {

        //test1

        Assert.assertEquals(new Vector2d(1, 2), new Vector2d(1, 2));

        //test2

        Assert.assertEquals(new Vector2d(-5, -4), new Vector2d(-5, -4));
    }


    @Test
    public void toStringTest() {

        // test1

        Vector2d x = new Vector2d(1, 2);
        Assert.assertEquals("(1, 2)", x.toString());

        //test2

        Vector2d y = new Vector2d(0, -11);
        Assert.assertEquals("(0, -11)", y.toString());
    }


    @Test
    public void precedesTest() {

        // test1

        Vector2d x1 = new Vector2d(1, 3);
        Vector2d y1 = new Vector2d(4, 8);
        Assert.assertTrue(x1.precedes(y1));

        // test2

        Vector2d x2 = new Vector2d(0, 0);
        Vector2d y2 = new Vector2d(0, 1);
        Assert.assertTrue(x2.precedes(y2));

        // test3

        Vector2d x3 = new Vector2d(1, 3);
        Vector2d y3 = new Vector2d(-1, 3);
        Assert.assertFalse(x3.precedes(y3));
    }

    @Test
    public void followsTest() {

        // test1

        Vector2d x1 = new Vector2d(8, 8);
        Vector2d y1 = new Vector2d(4, 8);
        Assert.assertTrue(x1.follows(y1));

        // test2

        Vector2d x2 = new Vector2d(0, 0);
        Vector2d y2 = new Vector2d(-1, 0);
        Assert.assertTrue(x2.follows(y2));

        // test3

        Vector2d x3 = new Vector2d(8, 8);
        Vector2d y3 = new Vector2d(41, 8);
        Assert.assertFalse(x3.follows(y3));
    }

    @Test
    public void upperRightTest() {

        // test1

        Vector2d x1 = new Vector2d(8, 3);
        Vector2d y1 = new Vector2d(4, 8);
        Assert.assertEquals(new Vector2d(8, 8), x1.upperRight(y1));

        // test2

        Vector2d x2 = new Vector2d(-1, 0);
        Vector2d y2 = new Vector2d(-2, -8);
        Assert.assertEquals(new Vector2d(-1, 0), x2.upperRight(y2));
    }

    @Test
    public void lowerLeftTest() {

        // test1

        Vector2d x1 = new Vector2d(8, 3);
        Vector2d y1 = new Vector2d(4, 8);
        Assert.assertEquals(new Vector2d(4, 3), x1.lowerLeft(y1));

        // test2

        Vector2d x2 = new Vector2d(-8, 0);
        Vector2d y2 = new Vector2d(-7, 12);
        Assert.assertEquals(new Vector2d(-8, 0), x2.lowerLeft(y2));
    }

    @Test
    public void addTest() {

        // test1

        Vector2d x1 = new Vector2d(8, 3);
        Vector2d y1 = new Vector2d(4, 8);
        Assert.assertEquals(new Vector2d(12, 11), x1.add(y1));

        // test2

        Vector2d x2 = new Vector2d(-8, 0);
        Vector2d y2 = new Vector2d(-2, 0);
        Assert.assertEquals(new Vector2d(-10, 0), x2.add(y2));
    }

    @Test
    public void subtractTest() {

        // test1

        Vector2d x1 = new Vector2d(8, 3);
        Vector2d y1 = new Vector2d(4, 8);
        Assert.assertEquals(new Vector2d(4, -5), x1.subtract(y1));

        // test2

        Vector2d x2 = new Vector2d(0, 1);
        Vector2d y2 = new Vector2d(-4, -11);
        Assert.assertEquals(new Vector2d(4, 12), x2.subtract(y2));
    }

    @Test
    public void oppositeTest() {

        // test1

        Vector2d x = new Vector2d(8, 3);
        Assert.assertEquals(new Vector2d(-8, -3), x.opposite());

        // test2

        Vector2d y = new Vector2d(-11, 0);
        Assert.assertEquals(new Vector2d(11, 0), y.opposite());
    }

}
