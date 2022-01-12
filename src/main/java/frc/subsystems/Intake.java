package frc.subsystems;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Intake extends SubsystemBase {

    // With eager singleton initialization, any static variables/fields used in the
    // constructor must appear before the "INSTANCE" variable so that they are initialized
    // before the constructor is called when the "INSTANCE" variable initializes.
    CANSparkMax frontIntakeMotor;
    CANSparkMax backIntakeMotor;


    public Intake() {
        frontIntakeMotor = new CANSparkMax(RobotMap.FRONT_INTAKE_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);
        backIntakeMotor = new CANSparkMax(RobotMap.BACK_INTAKE_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);
    }

    public void setIntakePercent(double percent) {
        frontIntakeMotor.set(percent);
        backIntakeMotor.set(percent);
    }

    public void stopIntake() {
        frontIntakeMotor.set(0);
        backIntakeMotor.set(0);
    }
}

