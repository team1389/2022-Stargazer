package frc.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.subsystems.Climber;
import frc.subsystems.Hopper;


public class ClimberLeftExtend extends CommandBase {
    private Climber climber;



    public ClimberLeftExtend() {
        climber = Robot.climber;

        addRequirements(climber);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {

        climber.extendLeftClimber();
    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()

        return false;
    }

    @Override
    public void end(boolean interrupted) {
        climber.stopLeftClimber();
    }
}
