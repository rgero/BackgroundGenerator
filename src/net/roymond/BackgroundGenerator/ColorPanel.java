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
        red = new JLabel("R");
        redTextField = new JTextField();
        redTextField.setSize(50,20);
        redTextField.setPreferredSize(new Dimension(50,20));

        green = new JLabel("G");
        greenTextField = new JTextField();
        greenTextField.setSize(50,20);
        greenTextField.setPreferredSize(new Dimension(50,20));
        blue = new JLabel("B");
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

    Color getColorValues(){
        int red, green, blue;
        try {
            red = Integer.valueOf( redTextField.getText() );
            green = Integer.valueOf( greenTextField.getText() );
            blue = Integer.valueOf( blueTextField.getText() );
            if (red > 255 | red < 0 ){
                throw new Exception (String.format( "%s - Your red value must be between 0 and 255", this.colorName));
            }
            if (green > 255 | green < 0 ){
                throw new Exception (String.format( "%s - Your green value must be between 0 and 255", this.colorName));
            }
            if (blue > 255 | blue < 0 ){
                throw new Exception (String.format( "%s - Your blue value must be between 0 and 255", this.colorName));
            }
        } catch (Exception e) {
            return null;
        }
        return new Color(red, green, blue);

    }

    @Override
    public String getName(){
        return colorName.getText();
    }


}
