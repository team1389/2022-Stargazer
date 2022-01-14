package frc.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.subsystems.Intake;


public class RunIntake extends CommandBase {
    private Intake intake;


    public RunIntake() {
        intake = Robot.intake;
        addRequirements(intake);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        intake.setIntakePercent(1);
    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()

        return false;
    }

    @Override
    public void end(boolean interrupted) {
        intake.stopIntake();
    }
}
