package frc.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.subsystems.Drivetrain;

public class TeleOpDrive extends CommandBase {
    public Drivetrain drivetrain;
    public final double JOYSTICK_DEADZONE = 0.05;

    public TeleOpDrive() {
        drivetrain = Robot.drivetrain;
        addRequirements(drivetrain);

    }

    @Override
    public void execute() {
        // Gets the driver's controller inputs
        double x = Robot.oi.getDriverRightX();
        double y = Robot.oi.getDriverLeftY();
        double rotation = Robot.oi.getDriverRightX();
        double triggerValue = Robot.oi.getDriverLeftTrigger();
        double slowFactor = 1;

        if (triggerValue > 0.05) {
            slowFactor = 0.5;
        }

        if (Math.abs(x) > JOYSTICK_DEADZONE || Math.abs(y) > JOYSTICK_DEADZONE
                || Math.abs(rotation) > JOYSTICK_DEADZONE) {
            Robot.drivetrain.drive(y * slowFactor, x * slowFactor);
        } else {
            Robot.drivetrain.stop();
        }

    }

}