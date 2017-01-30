package net.roymond.BackgroundGenerator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {


    /***
     * Exports the file
     */
    private static void exportFile(Graphics2D graphic, BufferedImage image){
        long currentTime = System.currentTimeMillis();
        String fileExtension = "png";
        String fileName;
        File outputFile;

        fileName = String.valueOf(currentTime) + "." + fileExtension;
        outputFile = new File(fileName);
        graphic.drawImage(image, null, 0, 0);
        try {
            ImageIO.write(image, fileExtension, outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /***
     * Generates a background image based on Triangles
     * @param width - the width of the triangle
     * @param height - the height of the triangle
     * @param screenWidth - the image width
     * @param screenHeight - the image height
     * @return A list of Triangles used to generate the image.
     */
    private static List<Shape> processTriangleList(int width, int height, int screenWidth, int screenHeight){
        List<Shape> triangleList = new ArrayList<>();
        int posX=0;
        int posY=0;
        int triangleNumber = 1;

        // If the line is odd, start with 1
        // Else it'll be 3. (see graphic in readme)
        boolean oddLine = true;

        while( posX < screenWidth ) {
            while ( posY < screenHeight ){
                triangleList.add( new Triangle( triangleNumber,posX, posY, width, height));

                //Establishes the next triangle
                // Right now, there are only four of them. When it is greater than 4
                // It resets.
                if(triangleNumber > 4){
                    triangleNumber = 1;
                    posY += height;
                } else {
                    triangleNumber++;
                    if (triangleNumber == 3 ){
                        posY += height;
                    }
                }
            }
            posX += width;
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

    /***
     * Generates a background image based on Squares
     * @param sideSize - width of the square
     * @param screenWidth - image width
     * @param screenHeight - image height
     * @return A list of Squares used to generate the image.
     */
    private static List<Shape> processSquareList(int sideSize, int screenWidth, int screenHeight){
        List<Shape> squareList = new ArrayList<>();
        int posX=0;
        int posY=0;
        while( posX < screenWidth ) {
            while (posY < screenHeight) {
                squareList.add( new Square(posX, posY, sideSize) );
                posY += sideSize;
            }
            posX += sideSize;
            posY = 0;
        }
        return squareList;
    }

    /***
     * Generates a background image based on Diamonds
     * @param width - diamond width
     * @param height - diamond height
     * @param screenWidth - image width
     * @param screenHeight - image height
     * @return A list of Diamonds used to generate the image.
     */
    private static List<Shape> processDiamondList(int width, int height, int screenWidth, int screenHeight){
        List<Shape> diamondList = new ArrayList<>();
        int posX=0;
        int posY=0;
        boolean nextLineOdd = true;

        // I need to account for the fact that diamonds don't fit in rectangles
        // I do this by adding one unit of width and height to the while loop.
        int targetWidth = screenWidth + width;
        int targetHeight = screenHeight + height;
        while( posX < targetWidth ) {
            while (posY < targetHeight) {
                diamondList.add( new Diamond(posX,posY,width,height) );
                posY += height;
            }
            posX += width/2;

            // This check allows for the program to offset the correct distance
            // for odd lines.
            if (nextLineOdd){
                posY = height/2;
            } else {
                posY = 0;
            }
            nextLineOdd = !nextLineOdd;
        }
        return diamondList;
    }

    /***
     * This is my attempt at generating noise in the images.
     * @param r - the Random object
     * @param img - the image being edited
     * @param maxDistortion - the maximum distortion value
     * @return The edited image.
     */
    private static BufferedImage addNoise(Random r, BufferedImage img, int maxDistortion){
        // If maxDistortion is less than 0, there is no point then going through this function
        // So bail.
        if (maxDistortion <= 0 ){
            return img;
        }

        int width = img.getWidth();
        int height = img.getHeight();
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                Color pixelColor = new Color(img.getRGB(i,j));
                int red = pixelColor.getRed();
                int green = pixelColor.getGreen();
                int blue = pixelColor.getBlue();

                int[] colorValues = new int[]{red,green,blue};
                for(int k=0; k < colorValues.length; k++){
                    int cV = colorValues[k];
                    if (r.nextBoolean()) {
                        cV += r.nextInt(maxDistortion);
                    } else {
                        cV -= r.nextInt(maxDistortion);
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
        return img;
    }

    private static BufferedImage addOutlines(BufferedImage img, int acceptableRange) {
        int width = img.getWidth();
        int height = img.getHeight();
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

                    boolean r1T = Math.abs(red - r2) > acceptableRange;
                    boolean g1T = Math.abs(green - g2) > acceptableRange;
                    boolean b1T = Math.abs(blue - b2) > acceptableRange;
                    boolean r2T = Math.abs(red - r2) > acceptableRange;
                    boolean g2T = Math.abs(green - g3) > acceptableRange;
                    boolean b2T = Math.abs(blue - b3) > acceptableRange;

                    if (r1T | g1T | b1T | r2T | g2T | b2T){
                        img.setRGB(i,j,Color.BLACK.getRGB());
                    }
                }
            }
        }
        return img;
    }

    public static void main(String[] args) {
        Random rand = new Random();

        //Screen Resolution
        int screenWidth = 1920;
        int screenHeight = 1080;

        //Screen Position
        int posX = 0;
        int posY = 0;

        Color previousColor =  Color.white;
        Color currentColor = Color.white;

        //Properties of the shape
        int width = 16*4;
        int height = 16*4;

        //Here is a triangle example
        //List<Shape> objectList = processTriangleList(width, height,screenWidth, screenHeight);
        List<Shape> objectList = processSquareList(width, screenWidth, screenHeight);
        //List<Shape> objectList = processDiamondList(width, height, screenWidth, screenHeight);

        BufferedImage image = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphic = image.createGraphics();


        graphic.setColor( Color.WHITE );
        graphic.fillRect(0,0, screenWidth, screenHeight);

        Color[] colorCho = new Color[]{new Color(0,64,17), new Color(16,95,37),
                new Color(42,127,64), new Color(79,159,100), new Color(127,191,144)};

        for(Shape i : objectList){
            while( currentColor.equals(previousColor) ){
                currentColor = colorCho[rand.nextInt(colorCho.length)];
            }
            graphic.setColor( currentColor );
            graphic.fill(new Polygon(i.getXPoints(), i.getYPoints(), i.getXPoints().length));
            previousColor = currentColor;
        }

        image = addOutlines( image, 10);
        exportFile(graphic, image);

        /*image = addNoise(rand, image, 35);
        exportFile(graphic, image);*/

    }
}
