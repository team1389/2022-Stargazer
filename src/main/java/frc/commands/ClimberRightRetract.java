package frc.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.subsystems.Climber;


public class ClimberRightRetract extends CommandBase {

    private Climber climber;

    public ClimberRightRetract() {
        climber = Robot.climber;
        addRequirements(climber);
    }

    @Override
    public void execute() {
        climber.pistonRetractRight();
    }

    @Override
    public boolean isFinished() {
        // Stopper here
        return true;
    }

}
