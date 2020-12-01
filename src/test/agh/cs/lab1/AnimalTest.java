package agh.cs.lab1;

import org.junit.*;

import java.util.Arrays;

public class AnimalTest {

    @Test
    public void optionParserTest(){

        // Test1

        MoveDirection[] x1 = {MoveDirection.LEFT, MoveDirection.FORWARD, MoveDirection.BACKWARD, MoveDirection.RIGHT};
        String[] y1 = {"l", "forward", "b", "r"};
        MoveDirection[] ny1 = OptionParser.parse(y1);
        Assert.assertArrayEquals(x1,ny1);

        // Test 2

        MoveDirection[] x2 = {MoveDirection.RIGHT, MoveDirection.BACKWARD, MoveDirection.RIGHT};
        String[] y2 = {"right", "b", "r"};
        MoveDirection[] ny2 = OptionParser.parse(y2);
        Assert.assertArrayEquals(x2, ny2);

        // Test 3

        MoveDirection[] x3 = {MoveDirection.RIGHT, MoveDirection.BACKWARD, MoveDirection.RIGHT};
        String[] y3 = {"right", "r"};
        MoveDirection[] ny3 = OptionParser.parse(y3);
        Assert.assertFalse(Arrays.equals(x3,ny3));

    }

    @Test
    public void optionParserArgumentIsNotLegalMoveSpecificationExceptionTest() {
        // Attempt no. 1

        boolean thrown1 = false;
        try {
            String[] s1 = {"something"};
            MoveDirection[] parsed1 = OptionParser.parse(s1);
        } catch (IllegalArgumentException e) {
            thrown1 = true;
        }
        Assert.assertTrue(thrown1);

        // Attempt no. 2

        boolean thrown2 = false;
        try {
            String[] s2 = {"l", "f", "r", " l"};
            MoveDirection[] parsed2 = OptionParser.parse(s2);
        } catch (IllegalArgumentException e) {
            thrown2 = true;
        }
        Assert.assertTrue(thrown2);
    }

    @Test
    public void animalMoveAndToStringTests(){

        // Improved tests including map interface

        // Test1

        RectangularMap map1 = new RectangularMap(5, 5);
        Animal rabbit1 = new Animal(map1);

        rabbit1.move(MoveDirection.RIGHT);
        rabbit1.move(MoveDirection.FORWARD);
        rabbit1.move(MoveDirection.FORWARD);
        rabbit1.move(MoveDirection.FORWARD);
        rabbit1.move(MoveDirection.LEFT);

        Assert.assertEquals(new Vector2d(4, 2),rabbit1.getPosition());
        Assert.assertEquals("^",rabbit1.toString());

        // Test2

        RectangularMap map2 = new RectangularMap(5, 5);
        Animal rabbit2 = new Animal(map2);

        rabbit2.move(MoveDirection.LEFT);
        rabbit2.move(MoveDirection.FORWARD);
        rabbit2.move(MoveDirection.FORWARD);
        rabbit2.move(MoveDirection.FORWARD);
        rabbit2.move(MoveDirection.LEFT);
        rabbit2.move(MoveDirection.FORWARD);
        rabbit2.move(MoveDirection.FORWARD);
        rabbit2.move(MoveDirection.FORWARD);

        Assert.assertEquals(new Vector2d(0, 0), rabbit2.getPosition());
        Assert.assertEquals("v",rabbit2.toString());

        // Test3

        RectangularMap map3 = new RectangularMap(5, 5);
        Animal rabbit3 = new Animal(map3);

        rabbit3.move(MoveDirection.LEFT);
        rabbit3.move(MoveDirection.FORWARD);
        rabbit3.move(MoveDirection.BACKWARD);
        rabbit3.move(MoveDirection.FORWARD);
        rabbit3.move(MoveDirection.LEFT);
        rabbit3.move(MoveDirection.LEFT);
        rabbit3.move(MoveDirection.FORWARD);
        rabbit3.move(MoveDirection.LEFT);

        Assert.assertNotEquals(new Vector2d(0, 0), rabbit3.getPosition());
        Assert.assertNotEquals("v",rabbit3.toString());

    }

    @Test
    public void animalMainTests(){

        // Improved tests including map and observer interface

        // Test1

        RectangularMap map1 = new RectangularMap(5, 5);
        Animal rat1 = new Animal(map1);
        rat1.addObserver(map1);
        String[] path1 = {"f", "f", "forward", "l", "left", "b", "f", "f", "forward", "f", "f", "b", "l", "backward"};
        World.moveAlong(path1, rat1);

        Assert.assertEquals(new Vector2d(1, 1), rat1.getPosition());
        Assert.assertEquals(">",rat1.toString());

        // Test2

        RectangularMap map2 = new RectangularMap(5, 5);
        Animal rat2 = new Animal(map2);
        String[] path2 = {"f", "f", "right", "f", "r", "backward", "left", "f", "f","r" , "f"};
        World.moveAlong(path2, rat2);

        Assert.assertEquals(new Vector2d(4, 3), rat2.getPosition());
        Assert.assertEquals("v",rat2.toString());

        // Test3

        RectangularMap map3 = new RectangularMap(5, 5);
        Animal rat3 = new Animal(map3);
        String[] path3 = {"l", "l", "l", "l", "l", "l" , "f" ,"l", "b", "r", "b"};
        World.moveAlong(path3, rat3);

        Assert.assertEquals(new Vector2d(1, 2), rat3.getPosition());
        Assert.assertEquals("v",rat3.toString());

        // Test4

        RectangularMap map4 = new RectangularMap(5, 5);
        Animal rat4 = new Animal(map4);
        String[] path4 = {"l", "l", "r", "l", "l", "l" , "r" ,"l", "r", "r", "r"};
        World.moveAlong(path4, rat4);

        Assert.assertNotEquals(new Vector2d(1, 2), rat4.getPosition());
        Assert.assertNotEquals("v",rat4.toString());

    }

}
