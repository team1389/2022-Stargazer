package frc.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.subsystems.Hopper;
import frc.subsystems.Intake;


public class RunIntake extends CommandBase {
    private Intake intake;
    private Hopper hopper;


    public RunIntake() {
        intake = Robot.intake;
        hopper = Robot.hopper;
        addRequirements(intake);
    }

    @Override
    public void execute() {
        intake.setIntakePercent(1);
        hopper.setSpeed(0.2);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        intake.stopIntake();
    }
}
