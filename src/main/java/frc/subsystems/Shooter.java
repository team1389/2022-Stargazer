package frc.subsystems;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Shooter extends SubsystemBase {

    // With eager singleton initialization, any static variables/fields used in the 
    // constructor must appear before the "INSTANCE" variable so that they are initialized 
    // before the constructor is called when the "INSTANCE" variable initializes.
    CANSparkMax shooterMotor;


    public Shooter() {
        shooterMotor = new CANSparkMax(RobotMap.SHOOTER_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);
    }

    public void setShooterVoltage(double percent) {
        shooterMotor.set(percent);
    }

    public void stopShooter() {
        shooterMotor.set(0);
    }
}

