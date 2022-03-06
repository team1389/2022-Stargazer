package frc.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.subsystems.Hopper;
import frc.subsystems.Intake;

public class RunIntake extends CommandBase {
    private Intake intake;
    private Hopper hopper;
    Timer timer;
    double timeout;

    private boolean forwards;

    public RunIntake() {
        intake = Robot.intake;
        hopper = Robot.hopper;
        forwards = true;
        timeout = 1000;
        timer = new Timer();

        addRequirements(intake);

    }

    public RunIntake(double timeout) {
        intake = Robot.intake;
        hopper = Robot.hopper;
        forwards = true;
        this.timeout = timeout;
        timer = new Timer();
        addRequirements(intake);

    }

    public RunIntake(boolean forwards) {
        intake = Robot.intake;
        hopper = Robot.hopper;
        this.forwards = forwards;
        timeout = 1000;

        timer = new Timer();


        addRequirements(intake);

    }

    @Override
    public void initialize() {
        super.initialize();
        timer.reset();
        timer.start();
    }

    @Override
    public void execute() {
        if (forwards) {
            intake.setIntakePercent(0.5);
        } else {
            intake.setIntakePercent(-0.5);
        }
        hopper.setSpeed(0.2);
    }

    @Override
    public boolean isFinished() {
        return timer.hasElapsed(timeout);
    }

    @Override
    public void end(boolean interrupted) {
        intake.stopIntake();
        hopper.stopHopper();
    }
}
