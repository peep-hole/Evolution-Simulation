package agh.cs.lab1.Visulisation;

import agh.cs.lab1.MapElements.Animal;
import agh.cs.lab1.Maps.EvolutionGeneratorMap;
import agh.cs.lab1.Simulation.FollowedAnimalStats;
import agh.cs.lab1.Simulation.SimulationLauncher;
import agh.cs.lab1.Simulation.Statistics;
import agh.cs.lab1.Utilities.Vector2d;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;


public class MapFrame extends JFrame implements ActionListener {

    private final EvolutionGeneratorMap map;
    private final Statistics stats;
    private final FollowedAnimalStats followedAnimal;

    private MapPanel mapPanel;
    private JPanel followedAnimalPanel;
    private JPanel statPanel;

    private JButton startButton;
    private JButton stopButton;
    private JButton getStatButton;

    private MapLabel[][] mapLabels;

    private int height;
    private int width;

    private SimulationLauncher launcher;

    public MapFrame(EvolutionGeneratorMap map, Statistics stats, FollowedAnimalStats followedAnimal, int width, int height, int epoch, SimulationLauncher launcher) {

        this.map = map;
        this.stats = stats;
        this.followedAnimal = followedAnimal;

        mapLabels = new MapLabel[width][height];

        this.height = height;
        this.width = width;

        this.launcher = launcher;



        // CONTROL PANEL

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(null);
        controlPanel.setBounds(0, 0, 1500, 100);
        controlPanel.setBackground(Color.LIGHT_GRAY);

        startButton = new JButton();
        startButton.setBounds(100, 25, 300, 50);
        startButton.addActionListener(this);
        startButton.setText("Start Simulation");
        startButton.setFocusable(false);

        stopButton = new JButton();
        stopButton.setBounds(500, 25, 300, 50);
        stopButton.addActionListener(this);
        stopButton.setText("Stop Simulation");
        stopButton.setFocusable(false);

        getStatButton = new JButton();
        getStatButton.setBounds(900, 25, 300, 50);
        getStatButton.addActionListener(this);
        getStatButton.setText("Get stats.txt");
        getStatButton.setFocusable(false);

        JLabel epochText = new JLabel("Epoch: " + epoch);
        epochText.setBounds(1300, 25, 200, 50);


        controlPanel.add(startButton);
        controlPanel.add(stopButton);
        controlPanel.add(getStatButton);
        controlPanel.add(epochText);

        // MAP PANEL

        mapPanel = new MapPanel(500, 100, 1000, 1000, launcher);



//        mapPanel = new JPanel();
//        mapPanel.setLayout(new GridLayout(height, width, 1, 1));
//        mapPanel.setBackground(Color.BLACK);
//        mapPanel.setBounds(500, 100, 1000, 1000);
//        mapPanel.setVisible(true);

        // FOLLOWED ANIMAL PANEL

        followedAnimalPanel = new JPanel();
        followedAnimalPanel.setBackground(Color.GRAY);
        followedAnimalPanel.setLayout(new GridLayout(5,1));
        followedAnimalPanel.setBounds(0, 100, 500, 500);

        JLabel infoLabel = new JLabel("Followed Animal Stats");
        infoLabel.setHorizontalAlignment(JLabel.CENTER);

        LinkedList<JLabel> followedLabel = new LinkedList<>();

        if(followedAnimal == null) {
            followedLabel.add(new JLabel());
            followedLabel.get(0).setText("--Click on animal, to start following it--");
            followedLabel.get(0).setHorizontalAlignment(JLabel.CENTER);
        }
        else {
            followedLabel.add(new JLabel());
            followedLabel.add(new JLabel());
            followedLabel.add(new JLabel());
            followedLabel.add(new JLabel());
            followedLabel.get(0).setText("Genome: " + followedAnimal.genotype);
            followedLabel.get(0).setHorizontalAlignment(JLabel.CENTER);

            followedLabel.get(1).setText("Amount of children after n epoch: " + followedAnimal.childrenAmount);
            followedLabel.get(1).setHorizontalAlignment(JLabel.CENTER);

            followedLabel.get(2).setText("Amount of descendants after n epoch: " + followedAnimal.descendantsAmount);
            followedLabel.get(2).setHorizontalAlignment(JLabel.CENTER);

            if(followedAnimal.deathEpoch == null) {
                followedLabel.get(3).setText("Still Alive!");
            }
            else {
                followedLabel.get(3).setText("Epoch of death: " + followedAnimal.deathEpoch);
            }
            followedLabel.get(3).setHorizontalAlignment(JLabel.CENTER);

        }

        followedAnimalPanel.add(infoLabel);
        for(JLabel label : followedLabel) {
            followedAnimalPanel.add(label);
        }
        followedAnimalPanel.setVisible(true);

        // STAT PANEL

        statPanel = new JPanel();
        statPanel.setLayout(new GridLayout(7,1));
        statPanel.setBackground(Color.WHITE);
        statPanel.setBounds(0, 600, 500, 500);
        
        JLabel titleLabel = new JLabel("Statistics");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        
        LinkedList<JLabel> statLabels = new LinkedList<>();
        
        if(stats != null) {
            statLabels.add(new JLabel());
            statLabels.add(new JLabel());
            statLabels.add(new JLabel());
            statLabels.add(new JLabel());
            statLabels.add(new JLabel());
            statLabels.add(new JLabel());
            statLabels.get(0).setText("Animal amount: " + stats.getTotalAnimalAmount());
            statLabels.get(0).setHorizontalAlignment(JLabel.CENTER);

            statLabels.get(1).setText("Grass amount: " + stats.getTotalGrassAmount());
            statLabels.get(1).setHorizontalAlignment(JLabel.CENTER);

            statLabels.get(2).setText("Dominating genotype: " );
            statLabels.get(2).setHorizontalAlignment(JLabel.CENTER);

            statLabels.get(3).setText("Average energy level: " + stats.getAverageEnergy());
            statLabels.get(3).setHorizontalAlignment(JLabel.CENTER);

            statLabels.get(4).setText("Average lifetime: " + stats.getAverageLifeLength());
            statLabels.get(4).setHorizontalAlignment(JLabel.CENTER);

            statLabels.get(5).setText("Average children amount : " + stats.getAverageChildAmount());
            statLabels.get(5).setHorizontalAlignment(JLabel.CENTER);
        }
        
        statPanel.add(titleLabel);
        for(JLabel label : statLabels) {
            statPanel.add(label);
        }
        
        
        
        // FRAME SETTINGS

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1500, 1120);
        this.setResizable(false);
        this.setLayout(new BorderLayout());

        this.add(followedAnimalPanel);
        this.add(statPanel);

       // flowPanel.add(mapPanel);
//        addLabels(mapLabels, width, height, map);
//        this.add(mapPanel);
        this.add(controlPanel);
        this.add(mapPanel);





        this.setVisible(true);

    }

//    private void addLabels(MapLabel[][] labels, int width, int height, EvolutionGeneratorMap map) {
//        for(int x = 0; x < width; x++){
//            for(int y = 0; y < height; y++) {
//                Vector2d position = new Vector2d(x, y);
//                labels[x][y] = new MapLabel(x, y, width, height, map.getStrongestAnimalAt(position),
//                        map.isInJungle(position), map.isGrassAt(position), this);
//                mapPanel.add(labels[x][y]);
//            }
//        }
//    }

//    private void addLabels(MapLabel[][] labels, int width, int height) {
//        for(int x = 0; x < width; x++){
//            for(int y = 0; y < height; y++) {
//                labels[x][y] = new MapLabel(x, y, width, height);
//                mapPanel.add(labels[x][y]);
//            }
//        }
//    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==startButton) {
            try {
                launcher.startSimulation();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }

        if(e.getSource()==stopButton) {
            launcher.stopSimulation();
        }

        if(e.getSource()==getStatButton) {
            launcher.getStats();
        }
    }

    public void repaintMapPanel() {
        mapPanel.repaintLabels();
    }
}
