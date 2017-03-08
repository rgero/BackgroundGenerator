package net.roymond.BackgroundGenerator;

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


}