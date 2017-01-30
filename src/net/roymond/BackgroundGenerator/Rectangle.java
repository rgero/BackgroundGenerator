package net.roymond.BackgroundGenerator;

import java.util.ArrayList;

/** Rectangle Class.
 * Created by gero on 1/25/2017.
 */
class Rectangle extends Shape {

    Rectangle(int topLeftX, int topLeftY, int width, int height){
        super.width = width;
        super.height = height;
        super.boxX = topLeftX;
        super.boxY = topLeftY;
        super.pointList = new ArrayList<>();
        calculatePoints();
    }

    private void calculatePoints(){
        Point p1 = new Point(boxX, boxY);
        Point p2 = new Point(boxX+width, boxY+height);
        Point p3 = new Point(boxX, boxY + height);
        Point p4 = new Point(boxX+width, boxY);
        super.addPoint(p1,p3,p2,p4);
    }


}
