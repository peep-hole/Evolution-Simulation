package agh.cs.lab1.Simulation;

import agh.cs.lab1.Maps.EvolutionGeneratorMap;
import agh.cs.lab1.Visulisation.MapFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulationLauncher implements ActionListener {

    private boolean isStopped;
    Timer timer;

    private JFrame optionFrame;
    private JButton okButton;

    JTextField[] textFields = new JTextField[7];

    JLabel[] lineLabel = new JLabel[8];

    private MapFrame actualFrame;

    private SimulationEngine engine;

    private int width;
    private int height;

    public SimulationLauncher(){};

    public void Launch() throws IllegalArgumentException{

        optionFrame = new JFrame();
        optionFrame.setSize(400,700);
        optionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        optionFrame.setResizable(false);
        optionFrame.setLayout(new GridLayout(14, 1));

        lineLabel[0] = new JLabel("Map Options");
        lineLabel[0].setHorizontalAlignment(JLabel.CENTER);

        lineLabel[1] = new JLabel("Width");
        lineLabel[1].setHorizontalAlignment(JLabel.CENTER);

        lineLabel[2] = new JLabel("Height");
        lineLabel[2].setHorizontalAlignment(JLabel.CENTER);

        lineLabel[3] = new JLabel("Start Energy");
        lineLabel[3].setHorizontalAlignment(JLabel.CENTER);

        lineLabel[4] = new JLabel("Move Energy");
        lineLabel[4].setHorizontalAlignment(JLabel.CENTER);

        lineLabel[5] = new JLabel("Plant Energy");
        lineLabel[5].setHorizontalAlignment(JLabel.CENTER);

        lineLabel[6] = new JLabel("Jungle Ratio (FLOAT)");
        lineLabel[6].setHorizontalAlignment(JLabel.CENTER);

        lineLabel[7] = new JLabel("Amount of Animals On Start");
        lineLabel[7].setHorizontalAlignment(JLabel.CENTER);

        for(int i = 0; i < 7; i++) {
            textFields[i] = new JTextField();
            textFields[i].setPreferredSize(new Dimension(300, 5));
        }

        optionFrame.add(lineLabel[0]);

        optionFrame.add(new JLabel());

        for(int i = 1; i < 8; i++) {
            optionFrame.add(lineLabel[i]);
            optionFrame.add(textFields[i-1]);
        }

        okButton = new JButton();
        okButton.setText("OK");
        okButton.setFocusable(false);
        okButton.addActionListener(this);

        optionFrame.add(okButton);

        optionFrame.setVisible(true);



    }

    private void runMap(int width, int height, int startEnergy, int moveEnergy,
                        int plantEnergy, float jungleRatio, int amountOfAnimalOnStart) {
        engine = new SimulationEngine(height, width, jungleRatio,
                amountOfAnimalOnStart, moveEnergy, plantEnergy, startEnergy);

        isStopped = true;


        actualFrame = new MapFrame(engine.getMap(), engine.getStats(), engine.getFollowedAnimal(), width, height, engine.getEpoch(), this);

        timer = new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                engine.runDay();
                actualFrame.repaintMapPanel();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) throws NumberFormatException {

        int startEnergy;
        int moveEnergy;
        int plantEnergy;
        float jungleRatio;
        int amountOfAnimalOnStart;

        if(e.getSource()==okButton) {
            width = Integer.parseInt(textFields[0].getText());
            height = Integer.parseInt(textFields[1].getText());
            startEnergy = Integer.parseInt(textFields[2].getText());
            moveEnergy = Integer.parseInt(textFields[3].getText());
            plantEnergy = Integer.parseInt(textFields[4].getText());
            jungleRatio = Float.parseFloat(textFields[5].getText());
            amountOfAnimalOnStart = Integer.parseInt(textFields[6].getText());

            optionFrame.dispose();
            runMap(width, height, startEnergy, moveEnergy, plantEnergy, jungleRatio, amountOfAnimalOnStart);

        }

    }

    public void startSimulation() throws InterruptedException {
        isStopped = !isStopped;
        if(!isStopped) {
            timer.start();
        }
        else {
            timer.stop();
        }
    }

    public void stopSimulation() {
        isStopped = !isStopped;
        if(!isStopped) {
            timer.start();
        }
        else {
            timer.stop();
        }
    }

    public void getStats() {

    }

    public EvolutionGeneratorMap getMap() {
        return engine.getMap();
    }
}
