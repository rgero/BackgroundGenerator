package net.roymond.BackgroundUI;

import net.roymond.BackgroundUI.AboutDisplay;
import net.roymond.BackgroundUI.SetupWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


class Main {

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
        menu.getAccessibleContext().setAccessibleDescription("Help Menu");

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
