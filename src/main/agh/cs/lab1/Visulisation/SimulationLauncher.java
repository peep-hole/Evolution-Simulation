package agh.cs.lab1.Visulisation;

import agh.cs.lab1.JsonParse;
import agh.cs.lab1.Simulation.SimulationEngine;
import agh.cs.lab1.Utilities.Vector2d;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class SimulationLauncher {

    private final SimulationEngine engine;

    private final Timer timer;
    private final StatPanel statPanel;
    private final MapPanel mapPanel;
    private final FollowedAnimalPanel followedAnimalPanel;
    private boolean isStopped;
    private boolean isFollowing;
    private final int mapNumber;

    private static final double animalStartRatio = 0.1;

    public SimulationLauncher(JsonParse parsedValues, int mapNumber) {




        int animalsOnStart = (int)(parsedValues.height* parsedValues.width * animalStartRatio);
        this.engine = new SimulationEngine((int)parsedValues.height,
                (int)parsedValues.width,
                (float)parsedValues.jungleRatio,
                animalsOnStart,
                (int)parsedValues.moveEnergy,
                (int)parsedValues.plantEnergy,
                (int)parsedValues.startEnergy);

        isFollowing = false;
        this.mapNumber = mapNumber;



        // LAUNCHER COMPONENTS

        // TIMER

        timer= new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                engine.runDay();
                if((engine.getFollowedAnimal() == null)&&(isFollowing)) {
                    isFollowing = false;
                    mapPanel.unsetFieldClicked();
                    timer.stop();
                    isStopped = !isStopped;
                }
                else {
                    followedAnimalPanel.passAnimal(engine.getFollowedAnimal());
                    followedAnimalPanel.repaint();
                }

                statPanel.repaint();
                mapPanel.repaint();

            }
        });

        // MAIN FRAME

        JFrame frame = new JFrame("Evolution Simulator");
        frame.setResizable(false);
        frame.setSize(1500, 1050);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // MAP PANEL

        mapPanel = new MapPanel(500, 50, 1000, 1000, engine.getMap(), this);
        mapPanel.setSize(1, 1);

        // STAT PANEL

        statPanel = new StatPanel(0, 50, 500, 500, engine.getStats(), this);
        statPanel.setSize(1, 1);

        // FOLLOWED ANIMAL PANEL

        followedAnimalPanel = new FollowedAnimalPanel(0, 550, 500, 500);
        followedAnimalPanel.setSize(1, 1);

        // CONTROL PANEL

        JPanel controlPanel = new JPanel();
        controlPanel.setBounds(0, 0, 1500, 50);

        JButton startStop = new JButton("Start/Stop");
        startStop.setBounds(500, 20, 500, 30);
        startStop.setFocusable(false);
        startStop.addActionListener(e -> startStop());

        controlPanel.add(startStop);

        // MAIN FRAME ADDING COMPONENTS

        frame.add(mapPanel);
        frame.add(statPanel);
        frame.add(controlPanel);
        frame.add(followedAnimalPanel);

        frame.setVisible(true);

        // ANIMATION STOP AT BEGINNING

        isStopped = true;
    }

    // START/STOP BUTTON ACTION

    public void startStop() {
        isStopped = !isStopped;
        if(!isStopped) {
            timer.start();
        }
        else {
            timer.stop();
        }
    }

    // ANIMAL TO FOLLOW CHOOSER ACTION

    public void startFollowingAnimalAt(Vector2d position) {

        if((!isFollowing)&&(engine.getMap().listOfAnimalsAt(position) != null)&&(engine.getMap().listOfAnimalsAt(position).size() != 0)) {


            if(!isStopped) {
                timer.stop();
                isStopped = true;
            }

            JFrame optionFrame = new JFrame("Animal at position");
            optionFrame.setBounds(800,  400 , 400, 400);
            optionFrame.setLayout(new GridLayout(6,1));
            optionFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            optionFrame.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent e) {

                }

                @Override
                public void windowClosing(WindowEvent e) {
                    mapPanel.unsetFieldClicked();
                    optionFrame.dispose();
                }

                @Override
                public void windowClosed(WindowEvent e) {

                }

                @Override
                public void windowIconified(WindowEvent e) {

                }

                @Override
                public void windowDeiconified(WindowEvent e) {

                }

                @Override
                public void windowActivated(WindowEvent e) {

                }

                @Override
                public void windowDeactivated(WindowEvent e) {

                }
            });

            JLabel genotypeLabel = new JLabel("Genotype: " + engine.getMap().getStrongestAnimalAt(position).getGenotype());
            genotypeLabel.setBounds(50, 20, 300, 50);

            JLabel infoLabel = new JLabel("For how long to follow this animal?");
            infoLabel.setBounds(50,120, 100, 50);

            JTextField textField = new JTextField();
            textField.setBounds(50, 220, 100, 50);

            JButton followButton = new JButton("Follow");
            followButton.setBounds(20, 320, 120, 50);
            followButton.addActionListener(e -> {
                int followEpochNumber = Integer.parseInt(textField.getText());

                engine.followAnimalFor(followEpochNumber, engine.getMap().getStrongestAnimalAt(position).getID());
                isFollowing = true;
                followedAnimalPanel.passAnimal(engine.getFollowedAnimal());
                followedAnimalPanel.repaint();
                optionFrame.dispose();
                isStopped = false;
                timer.start();
            });

            JButton cancelButton = new JButton("Cancel");
            cancelButton.setBounds(260, 320, 120, 50);
            cancelButton.addActionListener(e -> {
                mapPanel.unsetFieldClicked();
                optionFrame.dispose();
            });

            optionFrame.add(genotypeLabel);
            optionFrame.add(infoLabel);
            optionFrame.add(textField);
            optionFrame.add(followButton);
            optionFrame.add(cancelButton);

            optionFrame.setVisible(true);
        }

        else mapPanel.unsetFieldClicked();

    }

    // SHOW DOMINATING ANIMALS BUTTON ACTION

    public void showDominating() {

        mapPanel.setUnsetShowDominating();
        mapPanel.repaint();
    }

    // GET STATS TXT BUTTON ACTION

    public void generateStatsTxt() {
        engine.saveStatsTxt(mapNumber);
    }
}
