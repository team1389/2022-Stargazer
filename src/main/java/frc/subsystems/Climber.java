package frc.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Climber extends SubsystemBase {

    // With eager singleton initialization, any static variables/fields used in the
    // constructor must appear before the "INSTANCE" variable so that they are initialized
    // before the constructor is called when the "INSTANCE" variable initializes.
    TalonFX leftClimbMotor;
    TalonFX rightClimbMotor;

    public Climber() {
        leftClimbMotor = new TalonFX(RobotMap.CLIMB_MOTOR);

        rightClimbMotor = new TalonFX(RobotMap.CLIMB_MOTOR);

    }

    public void extendClimber() {
        leftClimbMotor.set(ControlMode.PercentOutput, 0.5);
        rightClimbMotor.set(ControlMode.PercentOutput, 0.5);

    }

    public void retractClimber() {
        leftClimbMotor.set(ControlMode.PercentOutput, -0.5);
        rightClimbMotor.set(ControlMode.PercentOutput, -0.5);
    }

    public void stopClimber() {
        leftClimbMotor.set(ControlMode.PercentOutput, 0);
        rightClimbMotor.set(ControlMode.PercentOutput, 0);
    }




}

