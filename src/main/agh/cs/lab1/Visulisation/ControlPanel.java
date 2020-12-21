package agh.cs.lab1.Visulisation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel implements ActionListener {

    private SimulationLauncher launcher;
    private JButton button;

    public ControlPanel(SimulationLauncher launcher) {

        this.launcher = launcher;
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        button = new JButton("Start/Stop button");
        button.setFocusable(false);
        button.setBounds(650, 10, 200, 30);
        button.addActionListener(this);
        this.setBounds(0, 0, 1500, 50);
        this.add(button);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        launcher.startStop();

    }
}
