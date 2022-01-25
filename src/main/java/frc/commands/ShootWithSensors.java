package frc.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Robot;

public class ShootWithSensors extends ParallelCommandGroup {
    private double targetRPM;
    private PIDController shooterPID; //idk which pid controller to use

    private String distance;
    private double indexerSpeed = 0.5;
    private double hopperSpeed = 0.5;

    public ShootWithSensors() {
        addRequirements(Robot.shooter, Robot.hopper);
        double distanceToTarget = SmartDashboard.getNumber("Distance To Target", 0);

        //TODO: lookup table for rpm

        addCommands(new ParallelCommandGroup(new TurretTracking(), new RunHopper()),  new WaitCommand(10));

    }

    @Override
    public void end(boolean interrupted) {
        Robot.hopper.stopHopper();
        Robot.shooter.stopShooter();
        Robot.shooter.stopIndexer();

    }
}


