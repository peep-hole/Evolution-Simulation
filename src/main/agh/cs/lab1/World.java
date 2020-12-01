package agh.cs.lab1;
import static java.lang.System.exit;
import static java.lang.System.out;

public class World {
//    static void run(String[] variables){   // run v1
////        out.println("Zwierzak idzie do przodu");
////        for(int i = 0; i<variables.length-1; i++){
////            out.print(variables[i] + ", ");
////        }
////        out.println(variables[variables.length - 1]);
//        for(String var : variables){
//            switch(var){
//                case "f":
//                    out.println("Zwierzak idzie do przodu");
//                    break;
//                case "b":
//                    out.println("Zwierzak idzie do tyłu");
//                    break;
//                case "r":
//                    out.println("Zwierzak skręca w prawo");
//                    break;
//                case "l":
//                    out.println("Zwierzak skręca w lewo");
//                    break;
//            }
//        }
//    }
//    static void run(Directions[] variables){
//        for(Directions direction : variables){
//            switch(direction){
//                case LEFT:
//                    out.println("Zwierzak skręca w lewo");
//                    break;
//                case RIGHT:
//                    out.println("Zwierzak skręca w prawo");
//                    break;
//                case FORWARD:
//                    out.println("Zwierzak idzie do przodu");
//                    break;
//                case BACKWARD:
//                    out.println("Zwierzak idzie do tyłu");
//                    break;
//            }
//        }
//    }
//    static Directions[] convert(String[] input){
//        int i = 0;
//        for(String isValid: input){
//            if(isValid.equals("f")||isValid.equals("b")||isValid.equals("l")||isValid.equals("r")){
//                i++;
//            }
//        }
//        Directions[] route = new Directions[i];
//        i = 0;
//        for(String dokadTuptaNocaJez: input){
//            switch(dokadTuptaNocaJez){
//                case "f":
//                    route[i++] = Directions.FORWARD;
//                    break;
//                case "b":
//                    route[i++] = Directions.BACKWARD;
//                    break;
//                case "l":
//                    route[i++] = Directions.LEFT;
//                    break;
//                case "r":
//                    route[i++] = Directions.RIGHT;
//                    break;
//            }
//        }
//        return route;
//    }

    public static void moveAlong(String[] pathToConvert, Animal beast){
        int i = 0;
        MoveDirection[] path = OptionParser.parse(pathToConvert);
        while((i< path.length)&&(path[i] != null)) {
            beast.move(path[i++]);
        }
    }

    public static void main(String[] args){
        try{//        out.println("Start");
//        String[] tmp = {"f", "l"};
//        run(tmp);
//        Directions[] route = convert(args);
//        run(route);
//        out.println("Stop");
//        Vector2d position1 = new Vector2d(1,2);
//        System.out.println(position1);
//        Vector2d position2 = new Vector2d(-2,1);
//        System.out.println(position2);
//        System.out.println(position1.add(position2));

//        TEST pkt8.

//     MapDirection test1 = MapDirection.NORTH;
//     MapDirection test2 = test1.next();
//     MapDirection test3 = test1.previous();
//     Vector2d testV = test1.toUnitVector();
//     out.println(test1);
//     out.println(test2);
//     out.println(test3);
//     out.println(test1.toString());
//     out.println(testV);
//     out.print(testV.toString());


//        Animal kitty = new Animal();
//        moveAlong(args, kitty);
//        out.println(kitty);


            RectangularMap map = new RectangularMap(2, 2);
            Animal a1 = new Animal(map, new Vector2d(1,1));

            // same place attempt

//            Animal a2 = new Animal(map, new Vector2d(1,1));

            // parsing error attempt

            String[] p = {"l_", "r"};
            moveAlong(p, a1);


        }catch(IllegalArgumentException ex) {
            out.println(ex.getMessage());
            exit(1);
        }
    }
}
