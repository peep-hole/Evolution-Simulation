package agh.cs.lab1.Visulisation;

import agh.cs.lab1.MapElements.Animal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MapLabel extends JLabel implements MouseListener {

    private Animal animal;
    private boolean isJungle;
    private boolean isGrass;

    private int x;
    private int y;
    private int mapWidth;
    private int mapHeight;

    public MapLabel(int x, int y, int mapWidth, int mapHeight,
                    Animal animal, boolean isJungle, boolean isGrass) {

        this.animal = animal;
        this.isJungle = isJungle;
        this.isGrass = isGrass;

        this.x = x;
        this.y = y;
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;


    }

    public MapLabel(int x, int y, int mapWidth, int mapHeight) {
        this.setBounds(x, y, 1000/mapWidth, 1000/mapHeight);
        if(((x%2 == 0)&&(y%2==0))||((x%2==1)&&(y%2==1))) this.setBackground(Color.GREEN);
        else this.setBackground(Color.BLUE);
        this.setOpaque(true);
    } // temporary constructor

    @Override
    public void mouseClicked(MouseEvent e) {

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

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        this.setBounds(x, y, 1000/mapWidth -1, 1000/mapHeight -1);


        if(animal != null) {
            switch(animal.getEnergyStatus()) {
                case HIGH:
                    this.setBackground(new Color(0xF5C5C7));
                    break;
                case MEDIUM:
                    this.setBackground(new Color(0xE97176));
                    break;
                case LOW:
                    this.setBackground(new Color(0xFF000C));
                    break;
                case DEAD:
                    this.setBackground(new Color(0x000000));
                    break;
                default: throw new IllegalArgumentException("No such energy status!");
            }
        }
        else {
            if (isJungle) {
                if (isGrass) {
                    this.setBackground(new Color(0x0E8818));
                } else this.setBackground(new Color(0x00E914));
            } else {
                if (isGrass) {
                    this.setBackground(new Color(0x96C600));
                } else {
                    this.setBackground(new Color(0xE9E900));
                }
            }
        }
        this.setOpaque(true);

    }

    public void updateState(Animal animal, boolean isJungle, boolean isGrass) {
        this.animal = animal;
        this.isGrass = isGrass;
        this.isJungle = isJungle;
    }
}
