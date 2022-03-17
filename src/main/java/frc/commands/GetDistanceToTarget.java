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

    private double targetRPM;

    private double tv, tx, ty, ta;
    private double distanceToTarget = 0;

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


        // Calculate the targetRPM based off of the distance
        targetRPM = 0.183455*Math.pow(Math.E, 0.0689839*distanceToTarget)+4304.84;

        // Limit it to the shooter's actual range
        targetRPM = Math.min(targetRPM, 5640);


        SmartDashboard.putNumber("Distance To Target", distanceToTarget);
        Robot.shooter.targetRPM = targetRPM;
        SmartDashboard.putNumber("Target_RPM", targetRPM);
    }

    @Override
    public boolean isFinished() {
        // If the limelight has found a target or it's been more than a second, end
        return distanceToTarget != 0 || timer.get() > 1;
        // return timer.get()>1;
    }

    @Override
    public void end(boolean interrupted) {

        // Turn off limelight green ring
        //NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);
    }
}