package frc.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.subsystems.Shooter;


public class RunIndexer extends CommandBase {
    private Shooter shooter;



    public RunIndexer() {
        shooter = Robot.shooter;

        addRequirements(shooter);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        shooter.runIndexer();

    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()

        return false;
    }

    @Override
    public void end(boolean interrupted) {
        shooter.stopIndexer();
    }
}
