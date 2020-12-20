package agh.cs.lab1.Visulisation;

import agh.cs.lab1.Maps.EvolutionGeneratorMap;
import agh.cs.lab1.Simulation.SimulationEngine;
import agh.cs.lab1.Simulation.SimulationLauncher;
import agh.cs.lab1.Utilities.Vector2d;

import javax.swing.*;
import java.awt.*;

public class MapPanel extends JPanel {

    private SimulationLauncher launcher;
    private final int x;
    private final int y;
    private int width;
    private int height;
    private final int mapWidth;
    private final int mapHeight;
    private final int xRatio;
    private final int yRatio;
    private EvolutionGeneratorMap map;

    private MapLabel[][] labels;

    public MapPanel(int x, int y, int width, int height, SimulationLauncher launcher) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.launcher = launcher;

        map = launcher.getMap();

        this.setBounds(x, y, width, height);

        mapWidth = map.getUpperRightCorner().x - map.getLowerLeftCorner().x;
        mapHeight = map.getUpperRightCorner().y - map.getLowerLeftCorner().y;

        xRatio = width / mapWidth;
        yRatio = height / mapHeight;

        labels = new MapLabel[mapWidth][mapHeight];

        for(int xPos = 0; xPos < mapWidth; xPos++){
            for(int yPos = 0; yPos < mapHeight; yPos++) {
                Vector2d position = new Vector2d(xPos, yPos);
                labels[xPos][yPos] = new MapLabel(xPos*xRatio, yPos*yRatio, mapWidth, mapHeight,
                        map.getStrongestAnimalAt(position), map.isInJungle(position), map.isGrassAt(position));
            }
        }
        this.repaint();
    }

    public void repaintLabels() {
        for(int xPos = 0; xPos < mapWidth; xPos++){
            for(int yPos = 0; yPos < mapHeight; yPos++) {
                Vector2d position = new Vector2d(xPos, yPos);
                labels[xPos][yPos].updateState(map.getStrongestAnimalAt(position), map.isInJungle(position), map.isGrassAt(position) );
                labels[xPos][yPos].repaint();
                System.out.println("#######################################################################");
            }
        }
    }
}