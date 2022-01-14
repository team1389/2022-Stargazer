package frc.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.subsystems.Climber;


public class RetractClimber extends CommandBase {
    private Climber climber;



    public RetractClimber() {
        climber = Robot.climber;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        climber.retractClimber();
    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()

        return false;
    }

    @Override
    public void end(boolean interrupted) {
        climber.stopClimber();
    }
}
