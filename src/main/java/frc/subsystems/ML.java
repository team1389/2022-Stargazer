package frc.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.util.BoundingBox;

import static java.lang.Double.parseDouble;

public class ML extends SubsystemBase {
    NetworkTable ml,camera;
    NetworkTableEntry boxes, objectClasses, resolution;
    String string;
    double middleX, middleY;
    public ML() {
        //Grabs the ml and camera tab from NetworkTables
        ml = NetworkTableInstance.getDefault().getTable("ML");
        camera =  NetworkTableInstance.getDefault().getTable("ML"+ NetworkTable.PATH_SEPARATOR+"MLOut");

        //Grabs the boxes, objectClasses, resolution, entry from the ML tab,
        boxes = ml.getEntry("boxes");
        objectClasses = ml.getEntry("object_classes");
        resolution = camera.getEntry("mode");

        string = resolution.getString("320x240 MJPEG 30 fps");

        //TODO: Ash comment this
        middleX = parseDouble(string.split("x",2)[0])/2;
        middleY = parseDouble(string.split("x",2)[1].split(" ")[0])/2;
    }
    public double movement() {
        //TODO: Ash comment this
        double[] inputCords = boxes.getDoubleArray(new double[0]);
        BoundingBox[] boxes = new BoundingBox[inputCords.length/4];
        for (int i = 1; i <= inputCords.length/4; i++) {
            boxes[i-1] = new BoundingBox(inputCords[(i*4)-4], inputCords[(i*4)-3], inputCords[(i*4)-2], inputCords[(i*4)-1]);
        }
        BoundingBox largest = new BoundingBox(middleX, middleY, middleX, middleY);
        for (BoundingBox x : boxes) {
            if (x.area > largest.area) {
                largest = x;
            }
        }
        return (((largest.x1 +largest.x2)/2) - middleX) / middleX;

    }
}




