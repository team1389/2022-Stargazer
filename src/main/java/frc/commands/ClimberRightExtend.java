package frc.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.subsystems.Climber;


public class ClimberRightExtend extends CommandBase {
    
    private Climber climber;

    public ClimberRightExtend() {
        climber = Robot.climber;
        addRequirements(climber);
    }

    @Override
    public void execute() {
        climber.winchRightUp();
    }

    @Override
    public boolean isFinished() {
        // Stopper here
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        climber.stopRightClimber();
    }
}
