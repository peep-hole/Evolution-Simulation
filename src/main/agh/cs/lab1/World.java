package agh.cs.lab1;

import agh.cs.lab1.Simulation.SimulationLauncher;
import agh.cs.lab1.Visulisation.MapFrame;

public class World {

    public static void main(String[] args) {

        //MapFrame frame = new MapFrame(null, null, null);

        try {
            SimulationLauncher launcher = new SimulationLauncher();
            launcher.Launch();
        } catch (IllegalArgumentException e) {

        }
    }
}
