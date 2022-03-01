package frc.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Robot;

public class ShootWithSensors extends SequentialCommandGroup {
    private double targetRPM;

    //TODO: Find this time
    // Time from the indexer starting to the last ball being shot
    private final double SHOOT_TIME = 5;

    private Timer timer;

    private double distanceToTarget;
    private final double[] lookupTable = {0, 100, 200, 300};
    public ShootWithSensors() {
        addRequirements(Robot.shooter);
        
        timer = new Timer();
        timer.reset();

        distanceToTarget = SmartDashboard.getNumber("Distance To Target", 0);
        // // TODO: lookup table for rpm
        // if (distanceToTarget > 40) {
        //     targetRPM = lookupTable[lookupTable.length-1];
        // } else {
        //     targetRPM = lookupTable[(int)(distanceToTarget/10)];
        // }
        targetRPM = 500;
        
        // To shoot, first spin up the flywheel while turning to the target
        // When facing the target and at speed, run the indexer and hopper to feed balls to the flywheel and shoot
        addCommands(
            new ParallelCommandGroup(new SetShooterRPM(targetRPM), new TurretTracking()),

            new InstantCommand(() -> timer.start()),
            //run indexer and hopper:
            new RunIndexer(), new RunHopper()
        );

    }

    @Override
    public void initialize() {
        super.initialize();
        Robot.shooter.setFlywheelSpeed(targetRPM);
    }

    @Override
    public boolean isFinished() {
        return timer.hasElapsed(SHOOT_TIME);
    }

    @Override
    public void end(boolean interrupted) {
        Robot.shooter.stopShooter();
    }
}
