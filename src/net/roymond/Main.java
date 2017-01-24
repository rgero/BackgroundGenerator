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


    public static void main(String[] args) {
        Random rand = new Random();

        List<Triangle> triangleList = new ArrayList<>();
        int triangleNumber = 1;

        // If the line is odd, start with 1
        // Else it'll be 3. (see graphic in readme)
        boolean oddLine = true;

        //Screen Resolution
        int screenWidth = 1920;
        int screenHeight = 1080;

        //Screen Position
        int posX = 0;
        int posY = 0;

        Color previousColor =  Color.white;
        Color currentColor = Color.white;

        //Properties of the Triangle
        int triangleWidth = 3*16;
        int triangleHeight = 3 * 9;

        while( posX < screenWidth ) {
            while ( posY < screenHeight ){
                triangleList.add( new Triangle( triangleNumber,posX, posY,triangleWidth, triangleHeight ));

                //Establishes the next triangle
                // Right now, there are only four of them. When it is greater than 4
                // It resets.
                if(triangleNumber > 4){
                    triangleNumber = 1;
                    posY += triangleHeight;
                } else {
                    triangleNumber++;
                    if (triangleNumber == 3 ){
                        posY += triangleHeight;
                    }
                }
            }
            posX += triangleWidth;
            posY = 0;

            if (oddLine){
                triangleNumber = 1;
            } else {
                triangleNumber = 3;
            }
            oddLine = !oddLine;
        }

        BufferedImage image = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphic = image.createGraphics();
        graphic.setColor( Color.GRAY );
        graphic.fillRect(0,0, screenWidth, screenHeight);

        int colorCounter = 0;
        Color[] colorCho = new Color[]{new Color(60,79,133), new Color(46,67,118),
                new Color(31,48,89), new Color(18,31,70), new Color(11,9,46)};



        for(Triangle i : triangleList){
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
