package agh.cs.lab1.Visulisation;

import agh.cs.lab1.Simulation.FollowedAnimalStats;

import javax.swing.*;
import java.awt.*;

public class FollowedAnimalPanel extends JPanel {

    private FollowedAnimalStats animal;
    private final int x;
    private final int y;
    private final int width;
    private final int height;

    public FollowedAnimalPanel(int x, int y, int width, int height ) {

        this.animal = null;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;


    }

    public void passAnimal(FollowedAnimalStats animal) {
        this.animal = animal;


    }

    @Override
    public void paintComponent(Graphics g) {


        super.paintComponent(g);

        this.setBounds(x, y, width, height);

        g.setFont(new Font(null, Font.BOLD, 15));

        g.drawString("Followed Animal", 200, 15);

        if(animal == null) {
            g.drawString("--Click Animal On Map To Start Following It--", 100, 45);
        }
        else {
            g.drawString("Children after " + animal.epoch + " epoch: " + animal.childrenAmount, 20, 60);

            g.drawString("Descendants after " + animal.epoch + " epoch: " + animal.descendantsAmount, 20, 90);

            if(animal.deathEpoch == null) {
                g.drawString("Still alive", 20, 120);
            }
            else {
                g.drawString("Died: " + animal.deathEpoch, 20, 120);
            }
        }
    }
}
