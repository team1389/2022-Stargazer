package frc.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.subsystems.Shooter;

public class RunIndexer extends CommandBase {
    private Shooter shooter;

    private boolean forwards;

    public RunIndexer(boolean forwards) {
        shooter = Robot.shooter;
        this.forwards = forwards;

        addRequirements();

    }

    public RunIndexer() {
        shooter = Robot.shooter;
        this.forwards = true;
        
        addRequirements();
        
    }

    @Override
    public void execute() {
        if (forwards) {
            shooter.runIndexer(0.75);
        } 
        else {
            shooter.runIndexer(-0.75);
        }

    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        shooter.stopIndexer();
    }
}
