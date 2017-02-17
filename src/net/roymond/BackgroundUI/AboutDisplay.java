package net.roymond.BackgroundUI;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * The projects Help > About
 * Created by Roymond on 2/14/2017.
 */
public class AboutDisplay {
    JPanel About;
    private JEditorPane editPane;
    private JButton sourceCodeButton;

    AboutDisplay(){
        try {
            editPane.setContentType("text/html");
            editPane.setPage(ClassLoader.getSystemResource("about.html"));
        } catch (IOException e) {
            editPane.setContentType("text/html");
            editPane.setText("<html>Could not load about document.</html>");
        }

        sourceCodeButton.addActionListener(e -> {
            try {
                URL webPage = new URL("https://github.com/rgero/BackgroundGenerator_2017");
                Desktop.getDesktop().browse(webPage.toURI());
            } catch (URISyntaxException | IOException e1) {
                e1.printStackTrace();
            }


        });



    }
}
