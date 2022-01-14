package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.SparkMaxPIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SwerveWheel extends SubsystemBase {
    private CANSparkMax rotateMotor;
    private TalonSRX driveMotor;
    private SparkMaxPIDController rotatePIDController, drivePIDController;

    //Multiplied by the native output units (-1 to 1) to find position
    //UPDATE THIS WITH GEAR RATIOS OF FINISHED ROBOT
    private final double ROTATION_POSITION_CONVERSION_FACTOR = 5.33 * 7 * 180;


    //Create PID coefficients
    public double rotateP = 1;
    public double rotateI = 0;
    public double rotateD = 0;

    public double driveP = 1;
    public double driveI = 0;
    public double driveD = 0;

    public SwerveWheel(int driveMotorPort, int rotateMotorPort, int driveEncoderPort) {
        //Instantiate motors
        driveMotor = new TalonSRX(driveMotorPort);
        rotateMotor = new CANSparkMax(rotateMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);

        //Assign PID controllers' parameters
        rotatePIDController = rotateMotor.getPIDController();

        rotatePIDController.setP(rotateP);
        rotatePIDController.setI(rotateI);
        rotatePIDController.setD(rotateD);
        rotateMotor.getEncoder().setPositionConversionFactor(ROTATION_POSITION_CONVERSION_FACTOR);

        //Reset rotation encoder to 0 rotations
        rotateMotor.getEncoder().setPosition(0);

    }

    public void setSpeed(double speed) {
        driveMotor.set(ControlMode.PercentOutput, speed);
    }

    //Angle should be measured in degrees
    public void setAngle(double angle) {
        rotatePIDController.setReference(angle, CANSparkMax.ControlType.kPosition);
    }


}
