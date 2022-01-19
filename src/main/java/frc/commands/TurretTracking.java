package frc.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.subsystems.Shooter;

public class TurretTracking extends CommandBase {
    Shooter shooter;
    PIDController pid;

    private double turretPower;

    //Limelight values
    private double tv, tx, ty, ta;

    public TurretTracking() {
        shooter = Robot.shooter;
        addRequirements(shooter);

        pid = shooter.getTurretPID();
    }

    @Override
    public void initialize() {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3);

        tv = 0;
        tx = 0;
        ty = 0;
        ta = 0;
    }

    @Override
    public void execute() {
        fetchValues();

        if(tv >= 1) {
            //do the pid
            turretPower = pid.calculate(tx, 0);

        }
        else {
            System.out.print("No target");
        }

        Robot.shooter.setTurretPower(turretPower);
    }

    private void fetchValues() {
        tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
        tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
        ta = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);
    }
}
