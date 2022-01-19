package frc.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.subsystems.Hopper;


public class RunHopper extends CommandBase {
    private Hopper hopper;



    public RunHopper() {
        hopper = Robot.hopper;

        addRequirements(hopper);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        hopper.setSpeed(1);
    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()

        return false;
    }

    @Override
    public void end(boolean interrupted) {
        hopper.stopHopper();
    }
}
