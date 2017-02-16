package net.roymond.BackgroundGenerator;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.PlainDocument;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roymond on 2/16/2017.
 */
public class SetupWindow {
    JPanel SetupPanel;
    private JPanel dimensionsPanel;
    private JTextField widthInputField;
    private JTextField heightInputField;
    private JPanel colorsPanel;
    private JRadioButton squareRadio;
    private JRadioButton triangleRadio;
    private JPanel shapePanel;
    private JRadioButton diamondShape;
    private JSpinner numberOfColorsField;
    private JPanel color1;
    private JTextField c1red;
    private JTextField c1green;
    private JTextField c1blue;

    private List<JPanel> colorPanels;

    SetupWindow(){


        numberOfColorsField.setValue(1);
        numberOfColorsField.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if ( (int)numberOfColorsField.getValue() <= 0 ){
                    numberOfColorsField.setValue(1);
                } else if ( (int)numberOfColorsField.getValue() >= 5 ){
                    numberOfColorsField.setValue(5);
                }
            }
        });


    }
}
