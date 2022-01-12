package frc.subsystems;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Climber extends SubsystemBase {

    // With eager singleton initialization, any static variables/fields used in the
    // constructor must appear before the "INSTANCE" variable so that they are initialized
    // before the constructor is called when the "INSTANCE" variable initializes.
    CANSparkMax climbMotor;

    public Climber() {
        climbMotor = new CANSparkMax(RobotMap.CLIMB_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);
        climbMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
    }

    public void extendClimber() {
        climbMotor.set(0.5);
    }

    public void retractClimber() {
        climbMotor.set(-0.5);
    }

    public void stop() {
        climbMotor.set(0);
    }




}

