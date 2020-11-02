package agh.cs.lab1;

import org.junit.*;

public class MapMoveTest {

    @Test
    public void mapTests() {

        // TEST 1
        // MAP 1

        RectangularMap map1 = new RectangularMap(3,3);
        Animal cat1 = new Animal(map1, new Vector2d(2, 2));
        Animal dog1 = new Animal(map1, new Vector2d(0, 0));

        String[] path1 = {"l","r","f","f","l","l","f","f","r","r","f","f","l","l","f","f","f","f"};

        map1.run(OptionParser.parse(path1));

        Assert.assertNotEquals(dog1.getPosition(), cat1.getPosition());

        Assert.assertEquals(cat1.getPosition() + cat1.toString(), new Vector2d(0,0) + "v");
        Assert.assertEquals(dog1.getPosition() + dog1.toString(), new Vector2d(2,2) + "^");

        // CHECK MAP 1
        // Placing and just rotating animals

        RectangularMap checkMap1 = new RectangularMap(3,3);
        Animal checkCat1 = new Animal(checkMap1, new Vector2d(0, 0));
        String[] checkPath1 = {"r","r"};
        checkMap1.run(OptionParser.parse(checkPath1));
        Animal checkDog1 = new Animal(checkMap1, new Vector2d(2, 2));

        Assert.assertEquals(map1.toString(), checkMap1.toString());

        // TEST 2
        // MAP 2

        RectangularMap map2 = new RectangularMap(8,8);
        Animal cat2 = new Animal(map2, new Vector2d(1, 6));
        Animal dog2 = new Animal(map2, new Vector2d(0, 2));
        Animal mouse2 = new Animal(map2, new Vector2d(6, 7));
        Animal parrot2 = new Animal(map2, new Vector2d(7, 1));

        String[] path2 = {"r","r","r","f","f","f","r","l","f","l","f","f", "r","f","f","r","f","r",
                "f","f","l","f", "f","l","f", "f","f", "f","f","f","f","r","f","f","f","f", "r",
                "f","f","l","f","f","f","f","l","f","l","f","l","l","f","f"};


        map2.run(OptionParser.parse(path2));

        // CHECK MAP 2
        // Placing animals and trying to reach same position as originals with different path

        RectangularMap checkMap2 = new RectangularMap(8,8);
        Animal checkCat2 = new Animal(checkMap2, new Vector2d(5, 4));
        Animal checkDog2 = new Animal(checkMap2, new Vector2d(7, 0));
        Animal checkMouse2 = new Animal(checkMap2, new Vector2d(6, 1));
        Animal checkParrot2 = new Animal(checkMap2, new Vector2d(3, 3));

        Assert.assertNotEquals(cat2.getPosition(), checkCat2.getPosition());
        Assert.assertNotEquals(dog2.getPosition(), checkDog2.getPosition());
        Assert.assertNotEquals(mouse2.getPosition(), checkMouse2.getPosition());
        Assert.assertNotEquals(parrot2.getPosition(), checkParrot2.getPosition());

        String[] checkPath2 = {"r", "f", "b", "f", "f", "f", "r", "l", "l", "f", "f", "f"};
        checkMap2.run(OptionParser.parse(checkPath2));

        Assert.assertEquals(cat2.getPosition(), checkCat2.getPosition());
        Assert.assertEquals(dog2.getPosition(), checkDog2.getPosition());
        Assert.assertEquals(mouse2.getPosition(), checkMouse2.getPosition());
        Assert.assertEquals(parrot2.getPosition(), checkParrot2.getPosition());

        Assert.assertEquals(cat2.toString(), checkCat2.toString());
        Assert.assertEquals(dog2.toString(), checkDog2.toString());
        Assert.assertEquals(mouse2.toString(), checkMouse2.toString());
        Assert.assertEquals(parrot2.toString(), checkParrot2.toString());

        Assert.assertEquals(map2.toString(), checkMap2.toString());


    }
}
