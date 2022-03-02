package frc.subsystems;

import java.util.Arrays;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.utils.BoundingBox;



public class ML extends SubsystemBase {
    NetworkTable ml,camera;
    NetworkTableEntry boxes, objectClasses, res;
//    String string;
    double middleX, middleY;


    public ML() {
        ml = NetworkTableInstance.getDefault().getTable("ML");
        boxes = ml.getEntry("detections");
//        objectClasses = ml.getEntry("object_classes");
//        camera =  NetworkTableInstance.getDefault().getTable("ML"+ NetworkTable.PATH_SEPARATOR+"MLOut");
//        res = camera.getEntry("mode");
//        string = res.getString("320x240 MJPEG 30 fps");
        // TODO: get these from network tables again
        middleX = 120/2;
        middleY = 160/2;
    }
    public double movement() {
        // get array of coords
        // its format is [x1,y1,x2,y2], and repeat this for as many times as possible 
        // length therefore is always a multiple of 4.
        double[] inputCoords = getDoubleArray();

        // because length is a multiple of 4, and divsions round down, this will end up being 1/4 the length of 
        // the `inputCoords` array
        BoundingBox[] boxes = new BoundingBox[inputCoords.length/4];

        // loop through boxes starting at zero and going up to the total length of the array 
        for (int i = 0; i < boxes.length; i++) {

            // i*4 is the index of the first element in a set of coords for example:
            // [0,1,2,3,4,5,6,7]
            //  ^ i=0   ^i=1
            // adding 0,1,2,3 gets the elements of the coords, x1, y1, x2, y2       
            boxes[i] = new BoundingBox(inputCoords[(i*4)+0], inputCoords[(i*4)+1], inputCoords[(i*4)+2], inputCoords[(i*4)+3]);
        }
        // find max bounding box by area, therefore being the closet. Theoretically something could be closer if 
        // it was on the edge but that's annoying to deal with 
        
        // min area element (0)
        BoundingBox largest = new BoundingBox(middleX, middleY, middleX, middleY);
        for (BoundingBox x : boxes) {
            // if new box is larger than the largest weve found so far..
            if (x.area > largest.area) {
                // assign new one to be the current largest
                largest = x;
            }
        }

        // center of bounding box
        double center = (largest.y1 +largest.y2)/2;

        // center - middle y will make a value -middley.. middley
        // dividing by middle y will then make that -1..1
        return ((center) - middleY) / middleY;
    }

    public double[] getDoubleArray() {
        // get boxes, in the form of a string, example:
        // "[1, 2, 3, 4]""
        String boxesString = boxes.getString("[]");

        // remove starting bracket and ending bracket
        // then split on ", " creating a list of numbers in the form of a string:
        // ["1", "2", "3", "4"]
        if (boxesString.length() == 2) {
            double[] arr = {};
            return arr;
        }
        String[] numsString = boxesString.substring(1,boxesString.length()-1).split(", ");

        // make a double array to hold the new doubles 
        double[] nums = new double[numsString.length];

        // loop through the strings..
        for (int x = 0; x < numsString.length; x++) {
            // and assign the parsed versions to the nums array 
            nums[x] = Double.parseDouble(numsString[x]);
        }

        // the array now has the doubles filled and now can be returned. huzzah!
        System.out.println(Arrays.toString(nums));
        return nums;

    }

    double kP = 0.5;
    double kI = 0.05;
    double kD = 0;
    PIDController pid;

    public void turn() {

        pid = new PIDController(kP, kI, kD);

        double error = Robot.ml.movement();
        double power = pid.calculate(error, 0);

        SmartDashboard.putNumber("power", power);
        SmartDashboard.putNumber("error", error);

        Robot.drivetrain.set(power, -power);
    }

    
}




