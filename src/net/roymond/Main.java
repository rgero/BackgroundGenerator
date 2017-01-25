package net.roymond;

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
        int width = 3*16;
        int height = 3 * 9;

        //Here is a triangle example
        List<Shape> objectList = processTriangleList(width, height,screenWidth, screenHeight);

        BufferedImage image = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphic = image.createGraphics();
        graphic.setColor( Color.GRAY );
        graphic.fillRect(0,0, screenWidth, screenHeight);

        Color[] colorCho = new Color[]{new Color(70,99,37), new Color(49,45,7),
                new Color(195,193,137), new Color(131,131,114), new Color(11,11,7)};

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
