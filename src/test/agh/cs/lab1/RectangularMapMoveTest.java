package agh.cs.lab1;

import org.junit.*;

public class RectangularMapMoveTest {

    @Test
    public void mapTest1() {

        RectangularMap map1 = new RectangularMap(3, 3);
        String[] path1 = {"l", "r", "f", "f", "l", "l", "f", "f", "r", "r", "f", "f", "l", "l", "f", "f", "f", "f"};
        Vector2d[] animals1 = {new Vector2d(2, 2), new Vector2d(0, 0)};
        SimulationEngine engine1 = new SimulationEngine(OptionParser.parse(path1), map1, animals1);
        engine1.run();


        Assert.assertNotEquals(engine1.whereIs(0), engine1.whereIs(1));

        Assert.assertEquals(engine1.whereIs(0), new Vector2d(0, 0));
        Assert.assertEquals(engine1.whereIs(1), new Vector2d(2, 2));

        // CHECK MAP 1
        // Placing and just rotating animals

        RectangularMap checkMap1 = new RectangularMap(3, 3);
        String[] checkPath1 = {"r", "r"};
        Vector2d[] checkAnimals11 = {new Vector2d(0, 0)};
        SimulationEngine checkEngine11 = new SimulationEngine(OptionParser.parse(checkPath1),
                checkMap1, checkAnimals11);
        checkEngine11.run();
        Vector2d[] checkAnimals12 = {new Vector2d(2, 2)};
        SimulationEngine checkEngine12 = new SimulationEngine(null,
                checkMap1, checkAnimals12);

        Assert.assertEquals(checkEngine11.whereIs(0), engine1.whereIs(0));
        Assert.assertEquals(checkEngine12.whereIs(0), engine1.whereIs(1));

        Assert.assertEquals(map1.toString(), checkMap1.toString());

    }

    @Test
    public void mapTest2() {

        RectangularMap map2 = new RectangularMap(8, 8);

        Vector2d[] animals2 = {new Vector2d(1, 6), new Vector2d(0, 2),
                new Vector2d(6, 7), new Vector2d(7, 1)};

        String[] path2 = {"r", "r", "r", "f", "f", "f", "r", "l", "f", "l", "f", "f", "r", "f", "f", "r", "f", "r",
                "f", "f", "l", "f", "f", "l", "f", "f", "f", "f", "f", "f", "f", "r", "f", "f", "f", "f", "r",
                "f", "f", "l", "f", "f", "f", "f", "l", "f", "l", "f", "l", "l", "f", "f"};

        SimulationEngine engine2 = new SimulationEngine(OptionParser.parse(path2), map2, animals2 );

        engine2.run();

        // CHECK MAP 2
        // Placing animals and trying to reach same position as originals with different path

        RectangularMap checkMap2 = new RectangularMap(8,8);

        Vector2d[] checkAnimals2 = {new Vector2d(5, 4), new Vector2d(7, 0),
                new Vector2d(6, 1), new Vector2d(3, 3)};

        Assert.assertNotEquals(engine2.whereIs(0), checkAnimals2[0]);
        Assert.assertNotEquals(engine2.whereIs(1), checkAnimals2[1]);
        Assert.assertNotEquals(engine2.whereIs(2), checkAnimals2[2]);
        Assert.assertNotEquals(engine2.whereIs(3), checkAnimals2[3]);


        String[] checkPath2 = {"r", "f", "b", "f", "f", "f", "r", "l", "l", "f", "f", "f"};

        SimulationEngine checkEngine2 = new SimulationEngine(OptionParser.parse(checkPath2),
                checkMap2, checkAnimals2);

        checkEngine2.run();

        Assert.assertEquals(engine2.whereIs(0), checkEngine2.whereIs(0));
        Assert.assertEquals(engine2.whereIs(1), checkEngine2.whereIs(1));
        Assert.assertEquals(engine2.whereIs(2), checkEngine2.whereIs(2));
        Assert.assertEquals(engine2.whereIs(3), checkEngine2.whereIs(3));

    }

    @Test
    public void outOfMapExceptionTest() {


        RectangularMap map = new RectangularMap(5, 5);

        // Attempt no. 1

        boolean thrown1 = false;
        try {
            Animal curiousCat = new Animal(map, new Vector2d(-1, 2));
        } catch (IllegalStateException e) {
            thrown1 = true;
        }

        Assert.assertTrue(thrown1);

        // Attempt no. 2

        boolean thrown2 = false;
        try {
            Animal curiousDog = new Animal(map, new Vector2d(7, 3));
        } catch (IllegalStateException e) {
            thrown2 = true;
        }

        Assert.assertTrue(thrown2);


        // Attempt no. 3

        boolean thrown3 = false;
        try {
            Animal curiousRat = new Animal(map, new Vector2d(20, -20));
        } catch (IllegalStateException e) {
            thrown3 = true;
        }

        Assert.assertTrue(thrown3);
    }

    @Test
    public void placeOnMapIsOccupiedExceptionTest() {

        RectangularMap map = new RectangularMap(3,3);

        // Attempt no. 1

        boolean thrown1 = false;
        try {
            Animal Cat = new Animal(map, new Vector2d(2, 2));
            Animal blindCat = new Animal(map, new Vector2d(2, 2));
        } catch (IllegalStateException e) {
            thrown1 = true;
        }

        Assert.assertTrue(thrown1);

        // Attempt no. 2

        boolean thrown2 = false;
        try {
            Animal Dog = new Animal(map, new Vector2d(0, 1));
            Animal blindDog = new Animal(map, new Vector2d(0, 1));
        } catch (IllegalStateException e) {
            thrown2 = true;
        }

        Assert.assertTrue(thrown2);

    }
}

