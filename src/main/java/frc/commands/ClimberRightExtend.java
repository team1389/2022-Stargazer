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
        climber.pistonExtendRight();
    }

    @Override
    public boolean isFinished() {
        // Stopper here
        return true;
    }

}
