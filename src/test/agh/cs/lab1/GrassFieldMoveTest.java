package agh.cs.lab1;

import org.junit.*;
import java.lang.Math;

public class GrassFieldMoveTest {

    @Test
    public void grassFieldTest1() {

        GrassField map1 = new GrassField(0);
        String[] path1 = {"r", "l", "f", "f", "f", "f", "f", "f", "l", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
        Vector2d[] animals1 = {new Vector2d(-5, 0), new Vector2d(0, 0)};
        SimulationEngine engine1 = new SimulationEngine(OptionParser.parse(path1), map1, animals1);
        engine1.run();


        Assert.assertNotEquals(engine1.whereIs(0), engine1.whereIs(1));

        Assert.assertEquals(engine1.whereIs(0), new Vector2d(-3, 4));
        Assert.assertEquals(engine1.whereIs(1), new Vector2d(-2, 4));

        // CHECK MAP 1

        GrassField checkMap1 = new GrassField(0);
        String[] checkPath1 = {"l", "l", "l", "l", "l", "l", "l", "l"};
        Vector2d[] checkAnimals1 = {new Vector2d(-3, 4), new Vector2d(-2, 4)};
        SimulationEngine checkEngine1 = new SimulationEngine(OptionParser.parse(checkPath1),
                checkMap1, checkAnimals1);
        checkEngine1.run();

        Assert.assertEquals(engine1.whereIs(0), checkEngine1.whereIs(0));
        Assert.assertEquals(engine1.whereIs(1), checkEngine1.whereIs(1));


    }

    @Test
    public void grassFieldTest2() {

        GrassField map2 = new GrassField(10);
        String[] path2 = {"b", "f", "b", "f", "b", "f", "b", "f", "b", "f", "b", "f", "b", "f", "b", "f", "b", "f"};
        Vector2d[] animals2 = {new Vector2d(0, 0), new Vector2d(10, 10)};
        SimulationEngine engine2 = new SimulationEngine(OptionParser.parse(path2), map2, animals2);
        engine2.run();


        Assert.assertNotEquals(engine2.whereIs(0), engine2.whereIs(1));

        Assert.assertEquals(engine2.whereIs(0), new Vector2d(0, -9));
        Assert.assertEquals(engine2.whereIs(1), new Vector2d(10, 19));

        // CHECK MAP 2

        GrassField checkMap2 = new GrassField(0);
        Vector2d[] checkAnimals2 = {new Vector2d(0, -9), new Vector2d(10, 19)};
        String[] checkPath2 = {"r", "r", "r", "r", "r", "r", "r", "r"};
        SimulationEngine checkEngine2 = new SimulationEngine(OptionParser.parse(checkPath2), checkMap2, checkAnimals2);
        checkEngine2.run();

        Assert.assertEquals(engine2.whereIs(0), checkEngine2.whereIs(0));
        Assert.assertEquals(engine2.whereIs(1), checkEngine2.whereIs(1));
    }

    @Test
    public void grassAmountTest1() {

        int n = 5;
        int range = Math.round((float)Math.sqrt(10*n));
        GrassField testGrassField1 = new GrassField(n);
        int grassAmount1 = 0;
        for(int i = 0; i <= range; i++) {
            for (int j = 0; j <= range; j++) {
                if(testGrassField1.objectAt(new Vector2d(i, j)) instanceof Grass) {
                    grassAmount1++;
                }
            }
        }
        Assert.assertEquals(n, grassAmount1);
    }

    @Test
    public void grassAmountTest2() {

        int n = 25;
        int range = Math.round((float)Math.sqrt(10*n));
        GrassField testGrassField1 = new GrassField(n);
        int grassAmount2 = 0;
        for(int i = 0; i <= range; i++) {
            for (int j = 0; j <= range; j++) {
                if(testGrassField1.objectAt(new Vector2d(i, j)) instanceof Grass) {
                    grassAmount2++;
                }
            }
        }
        Assert.assertEquals(n, grassAmount2);
    }

    @Test
    public void grassAmountCanNotBeNegativeExceptionTest() {

        // Attempt no. 1

        boolean thrown1 = false;
        try {
            GrassField grassField1 = new GrassField(-1);
        } catch (IllegalArgumentException e) {
            thrown1 = true;
        }
        Assert.assertTrue(thrown1);

        // Attempt no. 2

        boolean thrown2 = false;
        try {
            GrassField grassField2 = new GrassField(-100);
        } catch (IllegalArgumentException e) {
            thrown2 = true;
        }
        Assert.assertTrue(thrown2);

    }

    @Test
    public void placeOnMapIsOccupiedExceptionTest() {

        GrassField map = new GrassField(0);

        // Attempt no. 1

        boolean thrown1 = false;
        try {
            Animal Cat = new Animal(map, new Vector2d(100, 100));
            Animal blindCat = new Animal(map, new Vector2d(100, 100));
        } catch (IllegalArgumentException e) {
            thrown1 = true;
        }

        Assert.assertTrue(thrown1);

        // Attempt no. 2

        boolean thrown2 = false;
        try {
            Animal Dog = new Animal(map, new Vector2d(-25, 33));
            Animal blindDog = new Animal(map, new Vector2d(-25, 33));
        } catch (IllegalArgumentException e) {
            thrown2 = true;
        }

        Assert.assertTrue(thrown2);

    }

    @Test
    public void mapSizeTest() {
        GrassField map1 = new GrassField(0);

        // Test 1

        Animal rat1 = new Animal(map1, new Vector2d(15,12));
        Animal cat1 = new Animal(map1, new Vector2d(-21,-18));

        Assert.assertEquals(map1.getUpperRightCorner(),rat1.getPosition());
        Assert.assertEquals(map1.getLowerLeftCorner(), cat1.getPosition());

        // Test 2

        GrassField map2 = new GrassField(0);
        Animal rat2 = new Animal(map2, new Vector2d(70,0));
        Animal cat2 = new Animal(map2, new Vector2d(0,-112));

        Assert.assertEquals(map2.getUpperRightCorner(),rat2.getPosition());
        Assert.assertEquals(map2.getLowerLeftCorner(), cat2.getPosition());

        // Test 3

        GrassField map3 = new GrassField(0);
        Vector2d[] animal = {new Vector2d(0,0)};
        String[] path3 = {"f", "f", "f", "f", "f", "f", "f", "f", "f", "f"};
        SimulationEngine engine3 = new SimulationEngine(OptionParser.parse(path3), map3, animal);

        engine3.run();

        Assert.assertEquals(map3.getUpperRightCorner(),new Vector2d(0,10));
    }

}
