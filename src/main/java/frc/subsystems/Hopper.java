package frc.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import frc.robot.RobotMap;
import frc.util.SlowSubsystem;

public class Hopper extends SlowSubsystem {

    private CANSparkMax hopperMotor; //NEO 550


    public Hopper() {
        //Instantiate hopper motor as brushless motor with port from RobotMap
        hopperMotor = new CANSparkMax(RobotMap.HOPPER_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);
    }

    public void setSpeed(double percent) {
        hopperMotor.set(percent * slow);
    }

    public void stopHopper() {
        hopperMotor.set(0);
    }

}