package net.roymond.BackgroundGenerator;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The setup window for the background generator.
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

    private List<JPanel> listOfColors;

    SetupWindow(){

        colorsPanel.setLayout(new BoxLayout(colorsPanel, BoxLayout.PAGE_AXIS));
        listOfColors = new ArrayList<>();

        numberOfColorsField.setSize(70,5);

        numberOfColorsField.setValue(1);
        updateColorList();
        numberOfColorsField.addChangeListener(e -> {
            if ( (int)numberOfColorsField.getValue() <= 0 ){
                numberOfColorsField.setValue(1);
            } else if ( (int)numberOfColorsField.getValue() >= 5 ){
                numberOfColorsField.setValue(5);
            }
            updateColorList();

        });
    }

    private void updateColorList(){
        int desiredColors = (int)numberOfColorsField.getValue();
        while ( listOfColors.size() < desiredColors){
            System.out.println("Hello");
            ColorPanel newColor = new ColorPanel(String.format("Color %d", listOfColors.size()+1));
            listOfColors.add(newColor);
            colorsPanel.add(newColor);
        }
        while ( listOfColors.size() > desiredColors){
            colorsPanel.remove(listOfColors.get(listOfColors.size()-1));
            listOfColors.remove(listOfColors.size()-1);
        }
        colorsPanel.updateUI();
    }
}
