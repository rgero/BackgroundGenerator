package net.roymond.BackgroundGenerator;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;


public class Main {

    public static void main(String[] args) {
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
        test.generate();
        test.export();


    }
}
