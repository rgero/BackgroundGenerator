package net.roymond.BackgroundGenerator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Random;

/**
 * This is the background class.
 * Created by gero on 1/30/2017.
 */

enum ShapeEnum {
    DIAMOND,RECTANGLE,TRIANGLE,NONE
}

class Background {

    private int width;
    private int height;
    private int objWidth;
    private int objHeight;
    private BufferedImage img;
    private boolean baseImage;
    private Graphics2D graphic;
    private List<Color> colorList;
    private ShapeEnum chosenShape;
    private int outlineAR;
    private int maxDistortion;
    private Random rand;

    /***
     * This is the public constructor the Background class.
     * @param w - the width of the image
     * @param h - the height of the image
     */
    public Background(int w, int h){
        if (w > 0) {
            this.width = w;
        } else {
            this.width = 1920;
        }
        if (h > 0) {
            this.height = h;
        } else {
            this.height = 1080;
        }
        colorList = new ArrayList<>();
        outlineAR = 0;
        maxDistortion = 0;

        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        graphic = img.createGraphics();
        rand = new Random();
        chosenShape = ShapeEnum.NONE;
        baseImage = false;
    }

    public Background(){
        this(1920,1080);
    }

    /***
     * Allows the user to set the color palette of the background
     * @param colors - the colors in the palette
     */
    public void setColors(Color... colors){
        Collections.addAll(colorList, colors);
    }

    /***
     * Allows the user to set the desired shpae
     * @param s - the enumerated shape, current values are SQUARE, TRIANGLE, DIAMOND, NONE
     */
    public void setShape( ShapeEnum s ){
        chosenShape = s;
    }

    /***
     * Sets the outline acceptable range. Look at "addOutlines"
     * @param AR - the acceptable range. Anything under this value will not have an outline
     */
    public void setOutlineRange(int AR){
        this.outlineAR = AR;
    }

    /***
     * Sets the distortion range - Look at addNoise
     * @param d - the maximum value the rgb values can differentiate from their parent.
     */
    public void setDistortion(int d){
        this.maxDistortion = d;
    }

    /***
     * Sets the object width and height
     * @param w - width
     * @param h - height
     */
    public void setObjectDim(int w, int h){
        this.objWidth = w;
        this.objHeight = h;
    }

    /**
     * This allows the user to set a base image. This image will be used as a color map;
     * @param loadedImage - The loaded image.
     */
    public void setBaseImage(BufferedImage loadedImage){
        this.img = loadedImage;
        this.graphic = img.createGraphics();
        this.width = loadedImage.getWidth();
        this.height = loadedImage.getHeight();
        baseImage = true;
    }

    /**
     * Clears the base image.
     */
    public void clearBaseImage(){
        this.img = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
        this.graphic = img.createGraphics();
        baseImage = false;
    }

    /***
     * This is my attempt at generating noise in the images.
     */
    private void addNoise(){
        // If maxDistortion is less than 0, there is no point then going through this function
        // So bail.
        if (maxDistortion <= 0 ){
            return;
        }
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                Color pixelColor = new Color(img.getRGB(i,j));
                int red = pixelColor.getRed();
                int green = pixelColor.getGreen();
                int blue = pixelColor.getBlue();

                int[] colorValues = new int[]{red,green,blue};
                for(int k=0; k < colorValues.length; k++){
                    int cV = colorValues[k];
                    if (rand.nextBoolean()) {
                        cV += rand.nextInt(maxDistortion);
                    } else {
                        cV -= rand.nextInt(maxDistortion);
                    }
                    if (cV > 255){
                        cV = 255;
                    } else if (cV < 0){
                        cV = 0;
                    }
                    colorValues[k] = cV;
                }
                Color newColor = new Color(colorValues[0],colorValues[1],colorValues[2]);
                img.setRGB(i,j, newColor.getRGB());
            }
        }
    }

    /***
     * Adds the Outlines
     */
    private void addOutlines() {
        for(int i = 0; i < width; i++)
        {
            for(int j = 0; j < height; j++){
                if (i < width-1 && j < height - 1){
                    Color pixelColor = new Color(img.getRGB(i,j));
                    int red = pixelColor.getRed();
                    int green = pixelColor.getGreen();
                    int blue = pixelColor.getBlue();

                    Color rightColor = new Color(img.getRGB(i+1,j));
                    int r2 = rightColor.getRed();
                    int g2 = rightColor.getGreen();
                    int b2 = rightColor.getBlue();

                    Color downColor = new Color(img.getRGB(i,j+1));
                    int r3 = downColor.getRed();
                    int g3 = downColor.getGreen();
                    int b3 = downColor.getBlue();

                    boolean r1T = Math.abs(red - r2) > outlineAR;
                    boolean g1T = Math.abs(green - g2) > outlineAR;
                    boolean b1T = Math.abs(blue - b2) > outlineAR;
                    boolean r2T = Math.abs(red - r3) > outlineAR;
                    boolean g2T = Math.abs(green - g3) > outlineAR;
                    boolean b2T = Math.abs(blue - b3) > outlineAR;

                    if (r1T | g1T | b1T | r2T | g2T | b2T){
                        if (!pixelColor.equals(Color.black) ) {
                            img.setRGB(i, j, Color.BLACK.getRGB());
                        }
                    }
                }
            }
        }
    }

    /***
     * Generates a background image based on Diamonds
     */
    private List<Shape> processDiamondList(){
        List<Shape> diamondList = new ArrayList<>();
        int posX=0;
        int posY=0;
        boolean nextLineOdd = true;

        // I need to account for the fact that diamonds don't fit in rectangles
        // I do this by adding one unit of width and height to the while loop.
        int targetWidth = objWidth + width;
        int targetHeight = objHeight + height;
        while( posX < targetWidth ) {
            while (posY < targetHeight) {
                diamondList.add( new Diamond(posX,posY,objWidth,objHeight) );
                posY += objHeight;
            }
            posX += objWidth/2;

            // This check allows for the program to offset the correct distance
            // for odd lines.
            if (nextLineOdd){
                posY = objHeight/2;
            } else {
                posY = 0;
            }
            nextLineOdd = !nextLineOdd;
        }
        return diamondList;
    }

    //Processes the rectangles
    private List<Shape> processRectangleList(){
        List<Shape> squareList = new ArrayList<>();
        int posX=0;
        int posY=0;
        while( posX < width ) {
            while (posY < height) {
                squareList.add( new Rectangle(posX, posY, objWidth, objHeight) );
                posY += objHeight;
            }
            posX += objWidth;
            posY = 0;
        }
        return squareList;
    }

    //Generates the triangles
    private List<Shape> processTriangleList(){
        List<Shape> triangleList = new ArrayList<>();
        int posX=0;
        int posY=0;
        int triangleNumber = 1;

        // If the line is odd, start with 1
        // Else it'll be 3. (see graphic in readme)
        boolean oddLine = true;

        while( posX < width ) {
            while ( posY < height){
                triangleList.add( new Triangle( triangleNumber,posX, posY, objWidth, objHeight));

                //Establishes the next triangle
                // Right now, there are only four of them. When it is greater than 4
                // It resets.
                if(triangleNumber > 4){
                    triangleNumber = 1;
                    posY += objHeight;
                } else {
                    triangleNumber++;
                    if (triangleNumber == 3 ){
                        posY += objHeight;
                    }
                }
            }
            posX += objWidth;
            posY = 0;

            if (oddLine){
                triangleNumber = 1;
            } else {
                triangleNumber = 3;
            }
            oddLine = !oddLine;
        }
        return triangleList;
    }

    private boolean validateValues(){
        boolean retVal = true;
        if(!baseImage) {
            if (colorList.size() < 2 | objWidth <= 0 | objHeight <= 0) {
                retVal = false;
            }
        }
        return retVal;
    }


    public void generate(){
        if (!validateValues()){
            System.out.println("Invalid Values, Aborting");
            return;
        }
        List<Shape> objectList = new ArrayList<>();
        if( chosenShape == ShapeEnum.DIAMOND ){
            objectList = processDiamondList();
        } else if (chosenShape == ShapeEnum.RECTANGLE){
            objectList = processRectangleList();
        } else if (chosenShape == ShapeEnum.TRIANGLE){
            objectList = processTriangleList();
        }

        Color previousColor =  Color.white;
        Color currentColor = Color.white;

        for(Shape i : objectList){
            if(!baseImage) {
                while (currentColor.equals(previousColor)) {
                    currentColor = colorList.get(rand.nextInt(colorList.size()));
                }
            } else {
                int[] center = i.getCenter();
                int x = center[0];
                if (x >= width) {
                    x = width - 1;
                }
                int y = center[1];
                if (y >= height) {
                    y = height - 1;
                }
                currentColor = new Color(img.getRGB(x, y)); //NEED THE CENTER POINT.

            }
            graphic.setColor( currentColor );
            graphic.fill(new Polygon(i.getXPoints(), i.getYPoints(), i.getXPoints().length));
            previousColor = currentColor;
        }

        if(outlineAR > 0){
            addOutlines();
        }

        if(maxDistortion > 0){
            addNoise();
        }

    }

    public void export(){
        if (!validateValues()){
            System.out.println("Invalid Values, Aborting");
            return;
        }
        long currentTime = System.currentTimeMillis();
        String fileExtension = "png";
        String fileName;
        File outputFile;

        fileName = "Images\\" + String.valueOf(currentTime) + "." + fileExtension;
        outputFile = new File(fileName);
        graphic.drawImage(img, null, 0, 0);
        try {
            ImageIO.write(img, fileExtension, outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
