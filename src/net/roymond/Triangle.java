package net.roymond;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roymond on 1/23/2017.
 */
public class Triangle {

    List<Point> pointList;
    int triangleShape;
    int boxX;
    int boxY;
    int width;
    int height;

    public Triangle(int Shape, int topLeftX, int topLeftY, int width, int height){
        pointList = new ArrayList<Point>();
        this.triangleShape = Shape;
        this.boxX = topLeftX;
        this.boxY = topLeftY;
        this.width = width;
        this.height = height;
        calculatePoints();
    }

    public List<Point> getList(){
        return pointList;
    }

    public int[] getXPoints(){
        int[] xPoints = new int[pointList.size()];
        for(int i = 0; i < pointList.size(); i++){
            xPoints[i] = pointList.get(i).x;
        }
        return xPoints;
    }

    public int[] getYPoints(){
        int[] yPoints = new int[pointList.size()];
        for(int i = 0; i < pointList.size(); i++){
            yPoints[i] = pointList.get(i).y;
        }
        return yPoints;
    }

    public int getShape(){
        return triangleShape;
    }

    private void addPoint(Point... points){
        for(Point i : points){
            pointList.add(i);
        }
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
                addPoint(p1,p2,p3);
                break;
            case 2:
                p1 = new Point(boxX, boxY);
                p2 = new Point(boxX + width, boxY);
                p3 = new Point(boxX + width, boxY + height);
                addPoint(p1,p2,p3);
                break;
            case 3:
                p1 = new Point(boxX, boxY);
                p2 = new Point(boxX + width, boxY);
                p3 = new Point(boxX, boxY + height);
                addPoint(p1,p2,p3);
                break;
            case 4:
                p1 = new Point(boxX + width, boxY + height);
                p2 = new Point(boxX + width, boxY);
                p3 = new Point(boxX, boxY + height);
                addPoint(p1,p2,p3);
                break;
            default:
                break;
        }




    }


}
