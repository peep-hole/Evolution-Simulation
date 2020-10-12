package agh.cs.lab1;
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
    static void run(Directions[] variables){
        for(Directions direction : variables){
            switch(direction){
                case LEFT:
                    out.println("Zwierzak skręca w lewo");
                    break;
                case RIGHT:
                    out.println("Zwierzak skręca w prawo");
                    break;
                case FORWARD:
                    out.println("Zwierzak idzie do przodu");
                    break;
                case BACKWARD:
                    out.println("Zwierzak idzie do tyłu");
                    break;
            }
        }
    }
    static Directions[] convert(String[] input){
        int i = 0;
        for(String isValid: input){
            if(isValid.equals("f")||isValid.equals("b")||isValid.equals("l")||isValid.equals("r")){
                i++;
            }
        }
        Directions[] route = new Directions[i];
        i = 0;
        for(String dokadTuptaNocaJez: input){
            switch(dokadTuptaNocaJez){
                case "f":
                    route[i++] = Directions.FORWARD;
                    break;
                case "b":
                    route[i++] = Directions.BACKWARD;
                    break;
                case "l":
                    route[i++] = Directions.LEFT;
                    break;
                case "r":
                    route[i++] = Directions.RIGHT;
                    break;
            }
        }
        return route;
    }
    public static void main(String[] args){
        out.println("Start");
//        String[] tmp = {"f", "l"};
//        run(tmp);
        Directions[] route = convert(args);
        run(route);
        out.print("Stop");
    }
}
