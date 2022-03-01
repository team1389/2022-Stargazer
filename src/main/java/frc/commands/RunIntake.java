package frc.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.subsystems.Hopper;
import frc.subsystems.Intake;

public class RunIntake extends CommandBase {
    private Intake intake;
    private Hopper hopper;

    private boolean forwards;

    public RunIntake() {
        intake = Robot.intake;
        hopper = Robot.hopper;
        forwards = true;
        addRequirements(intake);

    }

    public RunIntake(boolean forwards) {
        intake = Robot.intake;
        hopper = Robot.hopper;
        this.forwards = forwards;

        addRequirements(intake);

    }

    @Override
    public void execute() {
        if (forwards) {
            intake.setIntakePercent(0.75);
        } else {
            intake.setIntakePercent(-0.5);
        }
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
