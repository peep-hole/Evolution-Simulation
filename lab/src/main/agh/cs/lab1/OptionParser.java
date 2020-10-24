package agh.cs.lab1;

public class OptionParser {

    public static MoveDirection[] parse(String[] commands){

        MoveDirection[] path = new MoveDirection[commands.length];
        int i, j = 0;

        for(i=0; i< commands.length; i++){
            switch(commands[i]){
                case "f":

                case "forward":
                    path[j++] = MoveDirection.FORWARD;
                  break;

                case "r":

                case "right":
                    path[j++] = MoveDirection.RIGHT;
                    break;

                case "b":

                case "backward":
                    path[j++] = MoveDirection.BACKWARD;
                    break;


                case "l":

                case "left":
                    path[j++] = MoveDirection.LEFT;
                    break;

            }
        }
        return path;
    }
}
