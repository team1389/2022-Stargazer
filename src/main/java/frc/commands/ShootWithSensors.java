package frc.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.Robot;

public class ShootWithSensors extends ParallelCommandGroup {
    private double targetRPM;

    private double distanceToTarget;
    private final double[] lookupTable = {0, 100, 200, 300};
    public ShootWithSensors() {
        addRequirements(Robot.shooter, Robot.hopper);

        distanceToTarget = SmartDashboard.getNumber("Distance To Target", 0);
        // TODO: lookup table for rpm
        if (distanceToTarget > 40) {
            targetRPM = lookupTable[lookupTable.length-1];
        } else {
            targetRPM = lookupTable[(int)(distanceToTarget/10)];
        }
        

        addCommands(
            new ParallelCommandGroup(new TurretTracking(), new WaitUntilShooterSpeed(targetRPM)),
            new ParallelCommandGroup(new RunHopper()), new RunIndexer()
        );

    }

    @Override
    public void initialize() {
        super.initialize();
        Robot.shooter.setFlywheelSpeed(targetRPM);
    }

    @Override
    public void end(boolean interrupted) {
        Robot.hopper.stopHopper();
        Robot.shooter.stopShooter();
        Robot.shooter.stopIndexer();

    }
}
