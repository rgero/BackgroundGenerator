package net.roymond.BackgroundGenerator;

import javax.swing.*;
import javax.swing.text.PlainDocument;
import java.awt.*;

/**
 * The Color Panel.
 * Created by Roymond on 2/16/2017.
 */
class ColorPanel extends JPanel {

    private JLabel colorName;
    private JLabel red;
    private JLabel green;
    private  JLabel blue;
    private JTextField redTextField;
    private JTextField greenTextField;
    private JTextField blueTextField;

    ColorPanel(String colorName){

        setLayout(new FlowLayout());
        this.colorName = new JLabel(colorName);
        red = new JLabel("Red");
        redTextField = new JTextField();
        redTextField.setSize(50,20);
        redTextField.setPreferredSize(new Dimension(50,20));

        green = new JLabel("Green");
        greenTextField = new JTextField();
        greenTextField.setSize(50,20);
        greenTextField.setPreferredSize(new Dimension(50,20));
        blue = new JLabel("Blue");
        blueTextField = new JTextField();
        blueTextField.setSize(50,20);
        blueTextField.setPreferredSize(new Dimension(50,20));

        PlainDocument redField = (PlainDocument) redTextField.getDocument();
        redField.setDocumentFilter(new IntFilter());
        PlainDocument greenField = (PlainDocument) greenTextField.getDocument();
        greenField.setDocumentFilter(new IntFilter());
        PlainDocument blueField = (PlainDocument) blueTextField.getDocument();
        blueField.setDocumentFilter(new IntFilter());

        this.add(this.colorName);
        this.add(this.red);
        this.add(redTextField);
        this.add(green);
        this.add(greenTextField);
        this.add(blue);
        this.add(blueTextField);

    }


}
