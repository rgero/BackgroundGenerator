package net.roymond.BackgroundUI;

import net.roymond.BackgroundGenerator.Background;
import net.roymond.BackgroundGenerator.ShapeEnum;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * The setup window for the background generator.
 * Created by Roymond on 2/16/2017.
 */
class SetupWindow {
    JPanel SetupPanel;
    private JPanel dimensionsPanel;
    private JTextField widthInputField;
    private JTextField heightInputField;
    private JPanel colorsPanel;
    private JRadioButton rectangleRadio;
    private JRadioButton triangleRadio;
    private JPanel shapeParentPanel;
    private JRadioButton diamondShape;
    private JSpinner numberOfColorsField;
    private JPanel ColorManagerPanel;
    private JPanel colorParent;
    private JPanel ExportPanel;
    private JTextField directoryTextField;
    private JButton browseButton;
    private JTextField fileNameField;
    private JButton clearButton;
    private JButton exportButton;
    private JPanel shapeDimensionPanel;
    private JTextField shapeHeightField;
    private JTextField shapeWidthField;
    private JPanel shapePanel;
    private JTextField distortionTextField;
    private JTextField outlineToleranceTextField;
    private JPanel customImagePanel;
    private JCheckBox loadCustom;
    private JPanel customDetails;
    private JTextField imageTextField;
    private JButton imageBrowse;
    private ButtonGroup shapeGroup;

    private String exportDir;
    private String exportFileName;

    private List<ColorPanel> listOfColorPanels;

    SetupWindow(){

        ColorManagerPanel.setLayout(new BoxLayout(ColorManagerPanel, BoxLayout.Y_AXIS));
        listOfColorPanels = new ArrayList<>();

        numberOfColorsField.setSize(70,5);

        numberOfColorsField.setValue(2);
        updateColorList();
        numberOfColorsField.addChangeListener(e -> {
            if ( (int)numberOfColorsField.getValue() <= 2 ){
                numberOfColorsField.setValue(2);
            } else if ( (int)numberOfColorsField.getValue() >= 5 ){
                numberOfColorsField.setValue(5);
            }
            updateColorList();

        });

        customDetails.setVisible(false); //This is false by default because the checkbox is not selected.
        loadCustom.addActionListener(e -> {
            if (loadCustom.isSelected()){
                customDetails.setVisible(true);
                toggleReadOnlyOptions();

            } else {
                customDetails.setVisible(false);
                toggleReadOnlyOptions();
            }
        });

        //Setting up the custom details panel
        ImageIcon imgIcon = new ImageIcon(new ImageIcon(ClassLoader.getSystemResource("FolderIcon.png")).getImage().getScaledInstance(16,16,Image.SCALE_DEFAULT));
        imageBrowse.setIcon(imgIcon);
        imageBrowse.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "PNG and JPG Files", "png", "jpg");
            chooser.setFileFilter(filter);
            chooser.setAcceptAllFileFilterUsed(false);
            int returnVal = chooser.showOpenDialog(SetupPanel);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                imageTextField.setText( chooser.getSelectedFile().getAbsolutePath() );
            }
        });


        PlainDocument shapeHeightDoc = (PlainDocument) shapeHeightField.getDocument();
        shapeHeightDoc.setDocumentFilter(new IntFilter());
        PlainDocument shapeWidthDoc = (PlainDocument) shapeWidthField.getDocument();
        shapeWidthDoc.setDocumentFilter(new IntFilter());
        PlainDocument distortionDoc = (PlainDocument) distortionTextField.getDocument();
        distortionDoc.setDocumentFilter(new IntFilter());
        PlainDocument outlineDoc = (PlainDocument) outlineToleranceTextField.getDocument();
        outlineDoc.setDocumentFilter(new IntFilter());

        browseButton.setIcon(imgIcon);
        browseButton.addActionListener(e -> {
            JFileChooser exportDirChooser = new JFileChooser();
            exportDirChooser.setCurrentDirectory(new File("."));
            exportDirChooser.setDialogTitle("Select an export directory");
            exportDirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            exportDirChooser.setAcceptAllFileFilterUsed(false);
            int returnVal = exportDirChooser.showOpenDialog(SetupPanel);
            if(returnVal == JFileChooser.APPROVE_OPTION) {

                exportDir = exportDirChooser.getSelectedFile().getAbsolutePath();
                directoryTextField.setText(exportDir);
            }
        });

        exportButton.addActionListener(e -> generateBackground());

        clearButton.addActionListener(e -> {
            widthInputField.setText("");
            heightInputField.setText("");
            shapeGroup.clearSelection();
            shapeHeightField.setText("");
            shapeWidthField.setText("");
            distortionTextField.setText("");
            outlineToleranceTextField.setText("");
            directoryTextField.setText("");
            fileNameField.setText("");
            for( ColorPanel i : listOfColorPanels){
                i.clear();
            }
        });
    }

    private void generateBackground(){

        ShapeEnum shape;
        int width = 0, shapeWidth = 0;
        int height = 0, shapeHeight = 0;
        int maxDistortion = 0, outlineTolerance = 0;
        List<Color> listOfColorValues = new ArrayList<>();
        String directoryPath;
        String fileName;

        boolean errors = false;
        String errorMessage = "";

        // If loadCustom is selected, load image.
        // else clause - verify image dimensions and selected colors.
        BufferedImage sourceImage = null;
        if ( loadCustom.isSelected() ){
            try {
                if (imageTextField.getText() != null){
                    File file = new File(imageTextField.getText());
                    if (file.isFile()) {
                        sourceImage = ImageIO.read(file);
                    } else {
                        throw new Exception("Custom Image - Input file could not be found.");
                    }
                } else {
                    throw new Exception("Custom Image - Input file not specified");
                }

            } catch( Exception e ){
                errorMessage += e.getMessage();
                errors = true;
            }

        } else {

            // Verify Width and Height
            try {
                width = Integer.valueOf( widthInputField.getText() );
                if (width <= 0 ){
                    throw new Exception("Invalid value");
                }
            } catch (Exception e){
                errorMessage += "The width of your image must be greater than zero.\n";
                errors = true;
            }
            try {
                height = Integer.valueOf( heightInputField.getText() );
                if (height <= 0 ){
                    throw new Exception("Invalid value");
                }
            } catch (Exception e){
                errorMessage += "The height of your image must be greater than zero.\n";
                errors = true;
            }

            // Verify Colors.
            for (ColorPanel i : listOfColorPanels) {
                Color c = i.getColorValues();
                if (c == null) {
                    errorMessage += String.format("%s - Issue with RGB values. Please check.\n", i.getName());
                    errors = true;
                } else {
                    listOfColorValues.add(c);
                }
            }
        }

        // Verify Inputs
        if (diamondShape.isSelected()){
            shape = ShapeEnum.DIAMOND;
        } else if (rectangleRadio.isSelected()){
            shape = ShapeEnum.RECTANGLE;
        } else if (triangleRadio.isSelected()){
            shape = ShapeEnum.TRIANGLE;
        } else {
            shape = ShapeEnum.NONE;
        }



        // Verify Distortion
        try {
            maxDistortion = Integer.valueOf( distortionTextField.getText() );
            if (maxDistortion < 0 ){
                throw new Exception("Invalid value");
            }
        } catch (Exception e){
            errorMessage += "The distortion of your image must be greater than or equal to zero.\n";
            errors = true;
        }

        //Verify Outline Tolerance
        try {
            outlineTolerance = Integer.valueOf( outlineToleranceTextField.getText() );
            if (outlineTolerance < 0 ){
                throw new Exception("Invalid value");
            }
        } catch (Exception e){
            errorMessage += "The outline tolerance of your image must be greater than or equal to zero.\n";
            errors = true;
        }

        // Verify Shape Width and Height;
        if (shape != ShapeEnum.NONE) {
            try {
                shapeWidth = Integer.valueOf(shapeWidthField.getText());
                if (shapeWidth <= 0) {
                    throw new Exception("Invalid value");
                }
            } catch (Exception e) {
                errorMessage += "The width of your shape must be greater than zero.\n";
                errors = true;
            }
            try {
                shapeHeight = Integer.valueOf(shapeHeightField.getText());
                if (shapeHeight <= 0) {
                    throw new Exception("Invalid value");
                }
            } catch (Exception e) {
                errorMessage += "The height of your shape must be greater than zero.\n";
                errors = true;
            }

            if( sourceImage == null){
                errorMessage += "Shape Type Error - None is selected, but no source image is loaded.\n";
                errors = true;
            }
        }

        // Verify the export directory.
        directoryPath = directoryTextField.getText();
        fileName = fileNameField.getText();

        if (!errors) {
            Background bg;
            if ( sourceImage != null ){
                width = sourceImage.getWidth();
                height = sourceImage.getHeight();
                bg = new Background(width, height);
                bg.setBaseImage(sourceImage);
            } else {
                bg = new Background(width, height);
                bg.setColors(listOfColorValues);
            }
            bg.setShape(shape);
            bg.setExportDir(directoryPath);
            bg.setFileName(fileName);
            bg.setObjectDim(shapeWidth, shapeHeight);
            bg.setDistortion(maxDistortion);
            bg.setOutlineRange(outlineTolerance);
            bg.generate();
            bg.export();
        } else {
            JOptionPane.showMessageDialog(null, errorMessage, "Error(s) when attempting export", JOptionPane.ERROR_MESSAGE);
        }





    }

    private void toggleReadOnlyOptions(){

        widthInputField.setEnabled( !widthInputField.isEnabled() );
        heightInputField.setEnabled( !heightInputField.isEnabled() );
        numberOfColorsField.setEnabled( !numberOfColorsField.isEnabled() );
        for (ColorPanel i : listOfColorPanels ){
            i.setEnabled( !i.isEnabled() );
        }

    }


    private void updateColorList(){
        int desiredColors = (int)numberOfColorsField.getValue();
        while ( listOfColorPanels.size() < desiredColors){
            ColorPanel newColor = new ColorPanel(String.format("Color %d", listOfColorPanels.size()+1));
            listOfColorPanels.add(newColor);
            ColorManagerPanel.add(newColor);
        }
        while ( listOfColorPanels.size() > desiredColors){
            ColorManagerPanel.remove(listOfColorPanels.get(listOfColorPanels.size()-1));
            listOfColorPanels.remove(listOfColorPanels.size()-1);
        }
        ColorManagerPanel.updateUI();
    }

}
