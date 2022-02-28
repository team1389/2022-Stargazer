package frc.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.subsystems.Climber;


public class ClimberLeftRetract extends CommandBase {

    private Climber climber;

    public ClimberLeftRetract() {
        climber = Robot.climber;
        addRequirements(climber);
    }

    @Override
    public void execute() {
        climber.pistonRetractLeft();
    }

    @Override
    public boolean isFinished() {
        // Stopper here
        return true;
    }

}
