package net.roymond.BackgroundGenerator;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;


public class Main {

    public static void main2(String[] args) {
        //Screen Resolution
        int screenWidth = 1920;
        int screenHeight = 1080;

        //Properties of the shape
        int width = 16*4;
        int height = 16*4;

        Background test = new Background(screenWidth,screenHeight);
        test.setShape(ShapeEnum.DIAMOND);
        test.setObjectDim(width,height);
        test.setColors(new Color(49,57,117), new Color(8,14,59),
                new Color(24,31,88), new Color(83,90,146), new Color(125,131,176));


        test.setDistortion(20);
        //test.generate();
        //test.export();


        // Trying the outline functionality on a real image.
        File inputFile = new File("C:\\Users\\gero\\Downloads\\Test.png");
        try {
            BufferedImage sourceImage = ImageIO.read(inputFile);
            Background importTest = new Background();
            importTest.setBaseImage(sourceImage);
            importTest.setShape(ShapeEnum.NONE);
            importTest.setObjectDim(width,height);
            importTest.setDistortion(150);
            importTest.generate();
            importTest.export();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("BackgroundGenerator");
        frame.setTitle("Roy's Background Generator");
        frame.setContentPane(new SetupWindow().SetupPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();

        JMenu menu = new JMenu("Help");
        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription("The About lives here");

        Action aboutAction = new AbstractAction("About Menu") {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame aboutDisplay = new JFrame("AboutDisplay");
                aboutDisplay.setTitle("About Roy's Chord Drawer");
                aboutDisplay.setContentPane(new AboutDisplay().About);
                aboutDisplay.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                aboutDisplay.pack();
                aboutDisplay.setResizable(false);
                aboutDisplay.setVisible(true);
            }
        };

        JMenuItem menuItem = new JMenuItem();
        menuItem.setAction(aboutAction);
        menu.add(menuItem);
        menuBar.add(menu);


        frame.setJMenuBar(menuBar);


        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);


    }

}
