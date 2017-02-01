package net.roymond.BackgroundGenerator;

import java.util.Collections;
import java.util.List;

/**
 * This is the Shape class, it will be the one all other shapes inherit from.
 *
 * Created by Roymond on 1/24/2017.
 */
class Shape {

    int boxX;
    int boxY;
    int width;
    int height;
    List<Point> pointList;

    Shape(){}

    public List<Point> getList(){
        return pointList;
    }

    int[] getXPoints(){
        int[] xPoints = new int[pointList.size()];
        for(int i = 0; i < pointList.size(); i++){
            xPoints[i] = pointList.get(i).x;
        }
        return xPoints;
    }

    int[] getYPoints(){
        int[] yPoints = new int[pointList.size()];
        for(int i = 0; i < pointList.size(); i++){
            yPoints[i] = pointList.get(i).y;
        }
        return yPoints;
    }

    void addPoint(Point... points){
        Collections.addAll(pointList, points);
    }

    int[] getCenter(){
        int x = boxX + width/2;
        int y = boxY + height/2;
        return new int[]{x,y};
    }

}
