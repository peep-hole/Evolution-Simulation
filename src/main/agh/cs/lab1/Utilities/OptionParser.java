package agh.cs.lab1.Utilities;

import agh.cs.lab1.Enums.MoveDirection;

import java.util.ArrayList;
import java.util.List;

public class OptionParser {

    public static MoveDirection[] parse(String[] commands) throws IllegalArgumentException {

        List<MoveDirection> path = new ArrayList<>();

        for (String command : commands) {
            switch (command) {
                case "f":

                case "forward":
                    path.add(MoveDirection.FORWARD);
                    break;

                case "r":

                case "right":
                    path.add(MoveDirection.RIGHT);
                    break;

                case "b":

                case "backward":
                    path.add(MoveDirection.BACKWARD);
                    break;


                case "l":

                case "left":
                    path.add(MoveDirection.LEFT);
                    break;

                default:
                    throw new IllegalArgumentException(command + " is not legal move specification");

            }
        }

        return  path.toArray(new MoveDirection[0]);
    }
}
