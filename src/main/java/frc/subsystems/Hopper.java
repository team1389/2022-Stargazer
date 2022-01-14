package frc.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Hopper extends SubsystemBase {

    private CANSparkMax hopperMotor;


    public Hopper() {
        hopperMotor = new CANSparkMax(RobotMap.HOPPER_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);
    }

    public void setSpeed(double percent) {
        hopperMotor.set(percent);
    }

    public void stopHopper() {
        hopperMotor.set(0);
    }

}