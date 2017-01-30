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

        Background test = new Background(screenWidth,screenWidth);
        test.setShape(ShapeEnum.TRIANGLE);
        test.setObjectDim(width,height);
        test.setColors(new Color(0,64,17), new Color(16,95,37),
                new Color(42,127,64), new Color(79,159,100), new Color(127,191,144));

        test.setOutlineRange(10);
        test.setDistortion(10);
        test.generate();
        test.export();


    }
}
