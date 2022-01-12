package frc.subsystems;

import com.ctre.phoenix.sensors.CANCoder;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class SwerveWheel extends SubsystemBase {
    private CANSparkMax driveMotor, rotateMotor;
    private CANCoder driveEncoder;
    private CANEncoder rotateEncoder;

    public SwerveWheel(int driveMotorPort, int rotateMotorPort, int driveEncoderPort) {
        //Instantiate motors
        driveMotor = new CANSparkMax(driveMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);
        rotateMotor = new CANSparkMax(rotateMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);

        //Instantiate encoders
        driveEncoder = new CANCoder(driveEncoderPort);
        rotateEncoder = new CANEncoder(rotateMotor);

    }

    public void setSpeed(int speed) {
        driveMotor.set(speed);
    }


}
