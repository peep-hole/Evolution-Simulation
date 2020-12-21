package agh.cs.lab1.Visulisation;

import agh.cs.lab1.MapElements.Animal;
import agh.cs.lab1.MapElements.Grass;
import agh.cs.lab1.Maps.EvolutionGeneratorMap;
import agh.cs.lab1.Utilities.Genes;
import agh.cs.lab1.Utilities.Vector2d;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

public class MapPanel extends JPanel implements MouseListener {

    private int x;
    private int y;
    private int width;
    private int height;
    private EvolutionGeneratorMap map;
    private SimulationLauncher launcher;
    private boolean showDominating;
    private int mapWidth;
    private int mapHeight;

    private int xRatio;
    private int yRatio;

    private boolean isFieldClicked = false;

    public MapPanel(int x, int y, int width, int height, EvolutionGeneratorMap map, SimulationLauncher launcher) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.map = map;
        this.launcher = launcher;
        showDominating = false;

        mapWidth = map.getUpperRightCorner().x - map.getLowerLeftCorner().x;
        mapHeight = map.getUpperRightCorner().y - map.getLowerLeftCorner().y;

        xRatio = width/mapWidth;
        yRatio = height/mapHeight;

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        this.setBounds(x, y, width, height);


        int jungleWidth = map.getJungleUpperRight().x - map.getJungleLowerLeft().x;
        int jungleHeight = map.getJungleUpperRight().y - map.getJungleLowerLeft().y;

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);


        g.setColor(new Color(0xDBDA48));
        g.fillRect(0, 0, mapWidth*xRatio, mapHeight*yRatio);



        g.setColor(new Color(0x00AA57));
        g.fillRect(map.getJungleLowerLeft().x * xRatio, map.getJungleLowerLeft().y * yRatio, jungleWidth* xRatio, jungleHeight * yRatio);


        g.setColor(new Color(0x87FF48));
        for(Grass grass : map.getListGrass()) {
            g.fillRect(grass.getPosition().x * xRatio, grass.getPosition().y * yRatio, xRatio, yRatio);
        }

        g.setColor(new Color(0xFF9D9B));
        for(Animal animal : map.getListAnimal()) {
            g.fillRect(animal.getPosition().x * xRatio, animal.getPosition().y * yRatio, xRatio, yRatio);

        }

        if((showDominating)&&(map.getLeadingGenes().size() == 1)) {
            g.setColor(Color.BLACK);
            for(Animal animal : map.getDominatingGenotypeAnimals()) {
                g.fillRect(animal.getPosition().x * xRatio, animal.getPosition().y * yRatio, xRatio, yRatio);
            }

        }

        this.addMouseListener(this);


    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if(!isFieldClicked) {
            int x = e.getX() / xRatio;
            int y = e.getY() / yRatio;

            isFieldClicked = true;
            launcher.startFollowingAnimalAt(new Vector2d(x, y));

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {


    }

    @Override
    public void mouseReleased(MouseEvent e) {


    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void unsetFieldClicked() {
        isFieldClicked = false;
    }

    public void setUnsetShowDominating() {

        showDominating = !showDominating;
    }
}
