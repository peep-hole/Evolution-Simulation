package agh.cs.lab1;

import agh.cs.lab1.Visulisation.SimulationLauncher;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class World {


    private static JsonParse parser;

    public static void main(String[] args) {

        // PARSING AND GETTING PARAMETERS

        parser = null;
        try {
            parser = new JsonParse("parameters.json");
        }catch (IOException | ParseException e) {
            e.getStackTrace();
            System.exit(-1);
        }

        // ONE OR TWO MAPS OPTION CHOOSER SETTINGS

        JFrame frame = new JFrame("Run Options");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(2, 1));
        frame.setBounds(800, 500, 300, 100);

        JButton singleButton = new JButton("Run Single Map");
        singleButton.setBounds(50, 20, 200, 30);
        singleButton.addActionListener(e -> {
            frame.dispose();
            runSingle();
        });

        JButton doubleButton = new JButton("Run Two Maps");
        doubleButton.setBounds(50, 60, 200, 30);
        doubleButton.addActionListener(e -> {
            frame.dispose();
            runDouble();
        });

        frame.add(singleButton);
        frame.add(doubleButton);

        frame.setVisible(true);





    }

    public static void runSingle() {
        SimulationLauncher launcher = new SimulationLauncher(parser,1);
    }

    public static void runDouble() {
        SimulationLauncher launcher1 = new SimulationLauncher(parser,1);
        SimulationLauncher launcher2 = new SimulationLauncher(parser,2);
    }


}
