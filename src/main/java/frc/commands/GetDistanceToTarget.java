package frc.commands;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class GetDistanceToTarget extends CommandBase {
    private double TARGET_HEIGHT_INCHES = 101;
    private double CAMERA_ANGLE_DEGREES = 40;
    private double CAMERA_HEIGHT_INCHES = 25.5;

    // private final double[] lookupTable = {0, 100, 200, 300};

    private double tv, tx, ty, ta;
    private double distanceToTarget = 0;
    private double targetRPM;

    private Timer timer = new Timer();

    public GetDistanceToTarget() {
        addRequirements(Robot.shooter);
    }

    @Override
    public void initialize() {
        super.initialize();

        // Turn on limelight green ring
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3);
        timer.reset();
        timer.start();
    }

    @Override
    public void execute() {
        // Read the target's position from NetworkTables and calculate the distance
        ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(3);
        distanceToTarget = (TARGET_HEIGHT_INCHES - CAMERA_HEIGHT_INCHES)
                / (Math.tan(Math.toRadians(CAMERA_ANGLE_DEGREES + ty)));

        // // TODO: lookup table for rpm
        // if (distanceToTarget > 40) {
        // targetRPM = lookupTable[lookupTable.length-1];
        // } else {
        // targetRPM = lookupTable[(int)(distanceToTarget/10)];
        // }

        // int[][] array = new int[][] { { 1, 0 }, { 1, 0 } };
        // int x = 0, y = 0;
        // Integer m = null;
        // for (int i = 1; i < array.length; i++) {
        //     if (array[i - 1][0] > distanceToTarget) {
        //         m = (array[i][1] - array[i - 1][1]) / (array[i][0] - array[i - 1][0]);
        //         x = array[i-1][0];
        //         y = array[i-1][1];
        //         break;
        //     }
        // }

        // if (m == null) {
        //     m = (array[array.length - 1][1] - array[array.length - 2][1])
        //             / (array[array.length - 1][0] - array[array.length - 2][0]);
        // }

        
        // targetRPM = m*(distanceToTarget - x) + y;

        //exp
        //targetRPM = 2177.05*Math.exp(0.00706342*distanceToTarget);

        targetRPM = (Math.pow(distanceToTarget, 3)*0.0291221) + (Math.pow(distanceToTarget, 2)*(-8.73884)) + (distanceToTarget*888.738)-26201.7;

        SmartDashboard.putNumber("Distance To Target", distanceToTarget);
        SmartDashboard.putNumber("ty", ty);
        SmartDashboard.putNumber("Target RPM", targetRPM);
        SmartDashboard.putString("Shoot status", "gettng distance");
    }

    @Override
    public boolean isFinished() {
        // If the limelight has found a target or it's been more than a second, end
        // return distanceToTarget != 0 || timer.get() > 1;
        return timer.get()>1;
    }

    @Override
    public void end(boolean interrupted) {

        // Turn off limelight green ring
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);
    }
}