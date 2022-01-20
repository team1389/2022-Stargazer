package frc.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Climber extends SubsystemBase {
    private TalonFX leftClimbMotor;
    private TalonFX rightClimbMotor;

    //TODO: Add piston objects

    private double climbSpeed = 0.5;

    public Climber() {
        leftClimbMotor = new TalonFX(RobotMap.CLIMB_MOTOR);

        rightClimbMotor = new TalonFX(RobotMap.CLIMB_MOTOR);

    }

    public void winchUp() {
        leftClimbMotor.set(ControlMode.PercentOutput, climbSpeed);
        rightClimbMotor.set(ControlMode.PercentOutput, climbSpeed);

    }

    public void winchDown() {
        leftClimbMotor.set(ControlMode.PercentOutput, -climbSpeed);
        rightClimbMotor.set(ControlMode.PercentOutput, -climbSpeed);
    }

    public void stopClimber() {
        leftClimbMotor.set(ControlMode.PercentOutput, 0);
        rightClimbMotor.set(ControlMode.PercentOutput, 0);
    }




}

