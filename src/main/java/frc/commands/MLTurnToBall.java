package frc.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class MLTurnToBall extends CommandBase {
    double kP;
    double kI;
    double kD;
    PIDController pid;


    public MLTurnToBall() {
        addRequirements(Robot.ml);
        pid = new PIDController(0.5, 0.05, 0);
    }
    @Override
    public void execute() {
        double error = Robot.ml.movement();
        double power = pid.calculate(error,0);
        SmartDashboard.putNumber("ML error", error);
        //TODO: convert drivetrain.set to work with swerve
        //Robot.drivetrain.set(-power,power);
    }

    @Override
    public void end(boolean interrupted) {
        // Robot.drivetrain.
    }
}