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

    private static List<Shape> processSquareList(int width, int screenWidth, int screenHeight){
        List<Shape> squareList = new ArrayList<>();
        int posX=0;
        int posY=0;
        while( posX < screenWidth ) {
            while (posY < screenHeight) {
                squareList.add( new Square(posX, posY, width) );
                posY += width;
            }
            posX += width;
            posY = 0;
        }
        return squareList;
    }

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
        int width = 16*3;
        int height = 16*2;

        //Here is a triangle example
        // List<Shape> objectList = processTriangleList(width, height,screenWidth, screenHeight);
        //List<Shape> objectList = processSquareList(width, screenWidth, screenHeight);
        List<Shape> objectList = processDiamondList(width, height, screenWidth, screenHeight);

        BufferedImage image = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphic = image.createGraphics();
        graphic.setColor( Color.WHITE );
        graphic.fillRect(0,0, screenWidth, screenHeight);

        Color[] colorCho = new Color[]{new Color(35,193,227), new Color(59,154,182),
                new Color(44,113,139), new Color(40,64,116), new Color(26,10,77)};

        for(Shape i : objectList){
            while( currentColor.equals(previousColor) ){
                currentColor = colorCho[rand.nextInt(colorCho.length)];
            }
            graphic.setColor( currentColor );
            graphic.fill(new Polygon(i.getXPoints(), i.getYPoints(), i.getXPoints().length));
            previousColor = currentColor;
        }
        exportFile(graphic, image);

    }
}
