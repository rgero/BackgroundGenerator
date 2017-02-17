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

    public static void main(String[] args) {

        Toolkit kit = Toolkit.getDefaultToolkit();
        Image img = kit.createImage(ClassLoader.getSystemResource("net/roymond/Resources/Icon.png"));

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
                aboutDisplay.setTitle("Roy's Background Generator");
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
        frame.setIconImage(img);

    }

}
