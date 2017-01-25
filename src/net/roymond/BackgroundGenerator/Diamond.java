package net.roymond.BackgroundGenerator;

import java.util.ArrayList;

/**
 * Diamond Class
 * Created by gero on 1/25/2017.
 */
class Diamond extends Shape {

    Diamond(int posX, int posY, int width, int height){

        super.width = width;
        super.height = height;
        super.boxX = posX;
        super.boxY = posY;
        super.pointList = new ArrayList<>();
        calculatePoints();
    }

    private void calculatePoints(){
        Point p1 = new Point( boxX - (width/2), boxY );
        Point p2 = new Point( boxX, boxY - (height/2));
        Point p3 = new Point( boxX + (width/2), boxY );
        Point p4 = new Point( boxX, boxY + (height/2));
        super.addPoint(p1,p2,p3,p4);


    }


}
