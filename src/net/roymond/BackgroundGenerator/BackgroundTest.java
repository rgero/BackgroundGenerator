package net.roymond.BackgroundGenerator;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.Assert.*;

/**
 * These are the automated tests for the background generator.
 * Created by Roymond on 3/7/2017.
 */
public class BackgroundTest {
    @org.junit.Test
    public void setShape() throws Exception {

        Background bgTest = new Background(1920, 1080);
        bgTest.setShape(ShapeEnum.DIAMOND);
        assert(bgTest.getShape() == ShapeEnum.DIAMOND);

    }

    @org.junit.Test
    public void diamondList() throws Exception {
        int acceptedCount = 42074;
        Background bgTest = new Background(1920, 1080);
        bgTest.setShape(ShapeEnum.DIAMOND);
        bgTest.setObjectDim(10,10);
        assertEquals(bgTest.processDiamondList().size(), acceptedCount);
    }

    @org.junit.Test
    public void squareList() throws Exception {
        int acceptedCount = 20736;
        Background bgTest = new Background(1920, 1080);
        bgTest.setShape(ShapeEnum.RECTANGLE);
        bgTest.setObjectDim(10,10);
        assertEquals(bgTest.processRectangleList().size(), acceptedCount);
    }
    @org.junit.Test
    public void triangleList() throws Exception {
        int acceptedCount = 51840;
        Background bgTest = new Background(1920, 1080);
        bgTest.setShape(ShapeEnum.TRIANGLE);
        bgTest.setObjectDim(10,10);
        assertEquals(bgTest.processTriangleList().size(), acceptedCount);
    }
    @org.junit.Test
    public void inputValidation() throws Exception {
        Background bgTest = new Background(1920, 1080);
        bgTest.setBaseImage(new BufferedImage(1920,1080,BufferedImage.TYPE_INT_ARGB));

        assertTrue(bgTest.validateValues()); // This should be true because there is a base image.

        bgTest.clearBaseImage();
        assertFalse(bgTest.validateValues()); //This should be false because there is no base image, object width or colors

        bgTest.setColors(Color.black, Color.blue);
        assertFalse(bgTest.validateValues()); // This should be false because there are no object dimensions

        bgTest.clearColors();

        bgTest.setObjectDim(10,10);
        assertFalse(bgTest.validateValues()); // This should bef false because there are no colors

        bgTest.setColors(Color.black, Color.blue);
        assertTrue(bgTest.validateValues()); // This should be true because there is object dimensions and colors


    }


}