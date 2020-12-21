package agh.cs.lab1;

import agh.cs.lab1.Simulation.SimulationEngine;
import agh.cs.lab1.Visulisation.SimulationLauncher;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class World {

    public static void main(String[] args) {

        JsonParse parser = null;
        try {
            parser = new JsonParse("parameters.json");
        }catch (IOException e) {
            System.out.print(e);
            System.exit(-1);
        }catch (ParseException e) {
            System.out.println(e);
            System.exit(-1);
        }

        System.out.println(parser.width + " " + parser.height + " " + parser.startEnergy + " " + parser.moveEnergy + " " + parser.plantEnergy + " " + parser.jungleRatio);

        SimulationLauncher launcher = new SimulationLauncher(parser);
    }


}
