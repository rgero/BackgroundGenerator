package net.roymond;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {


    public static void exportFile(Graphics2D graphic, BufferedImage image){
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
        List<Triangle> triangleList = new ArrayList<>();
        int startsWith = 2;
        boolean oddLine = true;
        int screenWidth = 1920;
        int screenHeight = 1080;
        int posX = 0;
        int posY = 0;

        int numberTrianglesX = 20;
        int numberTrianglesY = 20;

        int width = screenWidth / numberTrianglesX;
        int height = screenHeight / numberTrianglesY;

        for(int i = 0; i < numberTrianglesX; i++){
            for( int j = 0; j < numberTrianglesY; j++){
                triangleList.add(new Triangle(startsWith,posX, posY,width, height));
                if(startsWith < 4){
                    startsWith++;
                } else {
                    startsWith = 1;
                }
                if( j % 2 == 0){
                    posY += height;
                }
            }
            posX += width;
            posY = 0;
            if (oddLine){
                startsWith = 1;
            } else {
                startsWith = 2;
            }
            oddLine = !oddLine;
        }

        BufferedImage image = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphic = image.createGraphics();
        graphic.setColor( Color.GRAY );
        graphic.fillRect(0,0, screenWidth, screenHeight);

        int colorCounter = 0;
        Color[] colorCho = new Color[]{Color.BLACK, Color.GREEN, Color.YELLOW, Color.BLUE};

        for(Triangle i : triangleList){
            graphic.setColor(colorCho[colorCounter]);
            graphic.fill(new Polygon(i.getXPoints(), i.getYPoints(), i.getXPoints().length));
            if (colorCounter < colorCho.length - 1){
                colorCounter++;
            } else {
                colorCounter=0;
            }
        }
        exportFile(graphic, image);

    }
}
