package frc.subsystems;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Conveyer extends SubsystemBase {

    // With eager singleton initialization, any static variables/fields used in the
    // constructor must appear before the "INSTANCE" variable so that they are initialized
    // before the constructor is called when the "INSTANCE" variable initializes.
    CANSparkMax lowerConveyor;
    CANSparkMax upperConveyor;

    public Conveyer() {
        lowerConveyor = new CANSparkMax(RobotMap.LOWER_CONVEYER_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);
        upperConveyor = new CANSparkMax(RobotMap.UPPPER_CONVEYER_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);
    }

    public void setConveyer(double percent) {
        lowerConveyor.set(percent);
        upperConveyor.set(percent);
    }

    public void stopConveyer(double percent) {
        lowerConveyor.set(0);
        upperConveyor.set(0);
    }


}

