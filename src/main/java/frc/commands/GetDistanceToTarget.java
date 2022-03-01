package frc.commands;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;


public class GetDistanceToTarget extends CommandBase {
    private double TARGET_HEIGHT_INCHES = 104;
    private double CAMERA_ANGLE_DEGREES = 50; 
    private double CAMERA_HEIGHT_INCHES = 22.5;

    private double tv, tx, ty, ta;
    private double distanceToTarget = 0;

    private Timer timer = new Timer();

    @Override
    public void initialize() {
        // Turn on limelight green ring
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3);
        timer.reset();
        timer.start();
    }

    @Override
    public void execute() {
        tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(3);
        distanceToTarget = (TARGET_HEIGHT_INCHES-CAMERA_HEIGHT_INCHES) / (Math.tan(Math.toRadians(CAMERA_ANGLE_DEGREES+tx)));

        SmartDashboard.putNumber("Distance To Target", distanceToTarget);
    }

    @Override
    public boolean isFinished() {
        // If the limelight has found a target or it's been more than a second, end
        return distanceToTarget != 0 || timer.get() > 1;
    }

    @Override
    public void end(boolean interrupted) {
        // Turn off limelight green ring
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);
    }
}