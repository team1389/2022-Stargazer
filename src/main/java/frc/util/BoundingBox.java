package frc.util;


public class BoundingBox {
    public double x1,y1,x2,y2,area;
    public BoundingBox(double x1, double y1, double x2, double y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        area = Math.abs(x1-x2)* Math.abs(y1-y2);
    }
}