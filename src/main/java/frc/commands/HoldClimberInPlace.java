package frc.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class HoldClimberInPlace extends CommandBase{
    
    public enum RightOrLeft {
        left,
        right
    }

    private RightOrLeft leftOrRight;

    private WPI_TalonFX motor;
    private double targetPosition;

    private PIDController pid;

    public HoldClimberInPlace(RightOrLeft leftOrRight) {
        this.leftOrRight = leftOrRight;

        switch (leftOrRight) {
            case left: 
                motor = Robot.climber.getLeftMotor();
                break;
            case right:
                motor = Robot.climber.getRightMotor();
                break;
        }

        pid = new PIDController(0.1, 0, 0);

        addRequirements();
    }

    @Override
    public void initialize() {
        super.initialize();

        // Try to keep the motor wherever it is at the start
        switch (leftOrRight) {
            case left: 
                targetPosition = Robot.climber.getLeftEncoderPosition();
                break;
            case right:
                targetPosition = Robot.climber.getRightEncoderPosition();
                break;
        }
        
        pid.setSetpoint(targetPosition);
    }

    @Override
    public void execute() {
        double currentPosition;

        switch (leftOrRight) {
            case left: 
                currentPosition = Robot.climber.getLeftEncoderPosition();
                break;
            case right:
                currentPosition = Robot.climber.getRightEncoderPosition();
                break;
            default:
                currentPosition = targetPosition;
                break;
        }

        double power = pid.calculate(currentPosition);

        motor.set(ControlMode.PercentOutput, power);
    }

    @Override
    public void end(boolean interrupted) {
        Robot.climber.stopLeftClimber();
        Robot.climber.stopRightClimber();
    }

    
}
