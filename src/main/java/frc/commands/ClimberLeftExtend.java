package frc.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.subsystems.Climber;


public class ClimberLeftExtend extends CommandBase {

    private Climber climber;

    public ClimberLeftExtend() {
        climber = Robot.climber;
        addRequirements(climber);
    }

    @Override
    public void execute() {
        climber.pistonExtendLeft();
    }

    @Override
    public boolean isFinished() {
        // Stopper here
        return true;
    }

}
