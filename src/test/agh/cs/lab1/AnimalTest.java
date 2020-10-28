package agh.cs.lab1;

import org.junit.*;

import java.util.Arrays;

public class AnimalTest {

    @Test
    public void OptionParserTest(){

        // Test1

        MoveDirection[] x1 = {MoveDirection.LEFT, MoveDirection.FORWARD, MoveDirection.BACKWARD, MoveDirection.RIGHT};
        String[] y1 = {"l", "forward", "b", "r"};
        MoveDirection[] ny1 = OptionParser.parse(y1);
        for(int i = 0; i < x1.length; i++){ // assertArrayEquals
            Assert.assertEquals(x1[i], ny1[i]);
        }

        // Test 2

        MoveDirection[] x2 = {MoveDirection.RIGHT, MoveDirection.BACKWARD, MoveDirection.RIGHT, null, null, null};
        String[] y2 = {"right", "something", "else", "b", "?", "r"};
        MoveDirection[] ny2 = OptionParser.parse(y2);
        for(int i = 0; i < x2.length; i++){
            Assert.assertEquals(x2[i], ny2[i]);
        }

        // Test 3

        MoveDirection[] x3 = {MoveDirection.RIGHT, MoveDirection.BACKWARD, MoveDirection.RIGHT, null, null, null};
        String[] y3 = {"right", "null", "null", "null", "null", "r"};
        MoveDirection[] ny3 = OptionParser.parse(y3);
        Assert.assertFalse(Arrays.equals(x3,ny3));

    }

    @Test
    public void AnimalMoveAndToStringTests(){

        // Test1

        Animal rabbit1 = new Animal();

        rabbit1.move(MoveDirection.RIGHT);
        rabbit1.move(MoveDirection.FORWARD);
        rabbit1.move(MoveDirection.FORWARD);
        rabbit1.move(MoveDirection.FORWARD);
        rabbit1.move(MoveDirection.LEFT);

        Assert.assertEquals("Position: (4, 2),  Orientation: Północ", rabbit1.toString());

        // Test2

        Animal rabbit2 = new Animal();

        rabbit2.move(MoveDirection.LEFT);
        rabbit2.move(MoveDirection.FORWARD);
        rabbit2.move(MoveDirection.FORWARD);
        rabbit2.move(MoveDirection.FORWARD);
        rabbit2.move(MoveDirection.LEFT);
        rabbit2.move(MoveDirection.FORWARD);
        rabbit2.move(MoveDirection.FORWARD);
        rabbit2.move(MoveDirection.FORWARD);

        Assert.assertEquals("Position: (0, 0),  Orientation: Południe", rabbit2.toString());

        // Test3

        Animal rabbit3 = new Animal();

        rabbit3.move(MoveDirection.LEFT);
        rabbit3.move(MoveDirection.FORWARD);
        rabbit3.move(MoveDirection.BACKWARD);
        rabbit3.move(MoveDirection.FORWARD);
        rabbit3.move(MoveDirection.LEFT);
        rabbit3.move(MoveDirection.LEFT);
        rabbit3.move(MoveDirection.FORWARD);
        rabbit3.move(MoveDirection.LEFT);

        Assert.assertNotEquals("Position: (0, 0),  Orientation: Południe", rabbit3.toString());

    }

    @Test
    public void AnimalMainTests(){

        // Test1

        Animal rat1 = new Animal();
        String[] path1 = {"f", "f", "ff", "forward", "l", "l,left", "left", "b", "f", "f", "forward", "f", "f", "b", "l", "bagwart", "backward"};
        World.moveAlong(path1, rat1);

        Assert.assertEquals("Position: (1, 1),  Orientation: Wschód", rat1.toString());

        // Test2

        Animal rat2 = new Animal();
        String[] path2 = {"f", "i", "f", "right", "f", "r", "back", "backward", "lewt", "left", "ahead", "f", "f","r" ," s" , "f"};
        World.moveAlong(path2, rat2);

        Assert.assertEquals("Position: (4, 3),  Orientation: Południe", rat2.toString());

        // Test3

        Animal rat3 = new Animal();
        String[] path3 = {"l", "l", "l", "l", "l", "l" , "f" ,"l", " l ", "b", "r", "b"};
        World.moveAlong(path3, rat3);

        Assert.assertEquals("Position: (1, 2),  Orientation: Południe", rat3.toString());

        // Test4

        Animal rat4 = new Animal();
        String[] path4 = {"l", "l", "r", "l", "l", "l" , "r" ,"l", " l ", "r", "r", "r"};
        World.moveAlong(path4, rat4);

        Assert.assertNotEquals("Position: (1, 2),  Orientation: Południe", rat4.toString());
    }

}
