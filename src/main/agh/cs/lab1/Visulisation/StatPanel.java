package agh.cs.lab1.Visulisation;

import agh.cs.lab1.Simulation.Statistics;
import agh.cs.lab1.Utilities.Genes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class StatPanel extends JPanel implements ActionListener {

    private int x;
    private int y;
    private int width;
    private int height;
    private Statistics stats;
    private JButton getStatsButton;
    private JButton showDominanceButton;
    private SimulationLauncher launcher;



    public StatPanel(int x, int y, int width, int height, Statistics stats, SimulationLauncher launcher) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.stats = stats;
        this.launcher = launcher;

        getStatsButton = new JButton("Get stats.txt");
        getStatsButton.setFocusable(false);
        getStatsButton.setBounds(20, 100, 150, 20);
        getStatsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                launcher.generateStatsTxt();
            }
        });

        showDominanceButton = new JButton("Show animals with dominating genotype");
        showDominanceButton.setFocusable(false);
        showDominanceButton.setBounds(200, 100, 280, 20);
        showDominanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                launcher.showDominating();
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        this.setBounds(x, y, width, height);

        g.setFont(new Font(null, Font.BOLD, 20));
        g.drawString("Statistics", 200, 150);

        this.add(getStatsButton);
        this.add(showDominanceButton);

        g.setFont(new Font(null, Font.BOLD, 15));

        g.drawString("Epoch: " + stats.getEpoch(), 20, 200);

        g.drawString("Total animal amount: " + stats.getTotalAnimalAmount(), 20, 230);

        g.drawString("Total grass amount: " + stats.getTotalGrassAmount(), 20, 260);

        LinkedList<Genes> dominatingGenes = stats.getDominatingGenes();
        if(dominatingGenes.size() == 1) {
            g.drawString("Dominating Genes : " + dominatingGenes.get(0), 20, 290);
        }
        else g.drawString("Dominating Genes : None" , 20, 290);

        g.drawString("Average energy: " + stats.getAverageEnergy(), 20, 320);

        g.drawString("Average life length: " + stats.getAverageLifeLength(), 20, 350);

        g.drawString("Average children amount: " + stats.getAverageChildAmount(), 20, 380);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
