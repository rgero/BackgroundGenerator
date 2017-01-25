package net.roymond.BackgroundGenerator;

import java.util.ArrayList;

/**
 * Triangle Class
 *
 * Created by Roymond on 1/23/2017.
 */
class Triangle extends Shape {


    private int triangleShape;

    Triangle(int Shape, int topLeftX, int topLeftY, int width, int height){
        super.pointList = new ArrayList<>();
        this.triangleShape = Shape;
        super.boxX = topLeftX;
        super.boxY = topLeftY;
        super.width = width;
        super.height = height;
        calculatePoints();
    }

    private void calculatePoints(){
        Point p1;
        Point p2;
        Point p3;

        switch(triangleShape){
            case 1:
                p1 = new Point(boxX, boxY);
                p2 = new Point(boxX, boxY + height);
                p3 = new Point(boxX + width, boxY + height);
                super.addPoint(p1,p2,p3);
                break;
            case 2:
                p1 = new Point(boxX, boxY);
                p2 = new Point(boxX + width, boxY);
                p3 = new Point(boxX + width, boxY + height);
                super.addPoint(p1,p2,p3);
                break;
            case 3:
                p1 = new Point(boxX, boxY);
                p2 = new Point(boxX + width, boxY);
                p3 = new Point(boxX, boxY + height);
                super.addPoint(p1,p2,p3);
                break;
            case 4:
                p1 = new Point(boxX + width, boxY + height);
                p2 = new Point(boxX + width, boxY);
                p3 = new Point(boxX, boxY + height);
                super.addPoint(p1,p2,p3);
                break;
            default:
                break;
        }

    }


}
