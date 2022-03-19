package frc.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import frc.util.Angle;

public class Drivetrain extends SubsystemBase {
    private CANSparkMax frontLeftDriveMotor;
    private CANSparkMax frontRightDriveMotor;
    private CANSparkMax backLeftDriveMotor;
    private CANSparkMax backRightDriveMotor;

    private AHRS gyro = new AHRS(SerialPort.Port.kMXP);

    public Drivetrain() {
        frontLeftDriveMotor = new CANSparkMax(RobotMap.DRIVE_ENCODER_FRONT_LEFT, MotorType.kBrushless);
        frontRightDriveMotor = new CANSparkMax(RobotMap.DRIVE_ENCODER_FRONT_RIGHT, MotorType.kBrushless);
        backLeftDriveMotor = new CANSparkMax(RobotMap.DRIVE_ENCODER_BACK_LEFT, MotorType.kBrushless);
        backRightDriveMotor = new CANSparkMax(RobotMap.DRIVE_ENCODER_BACK_RIGHT, MotorType.kBrushless);
    }

    public void power(double leftPower, double rightPower) {
        frontLeftDriveMotor.set(leftPower);
        backLeftDriveMotor.set(leftPower);

        frontRightDriveMotor.set(rightPower);
        backRightDriveMotor.set(rightPower);
    }

    /**
     * 
     * @param power
     * @param turnPower value from -1 to 1, -1 is left, 1 is right
     */
    public void drive(double power, double turnPower) {
        var leftPower = Math.max(power - turnPower, 1);
        var rightPower = Math.max(power + turnPower, 1);
        power(leftPower, rightPower);
    }

    public Angle getAngle() {
        return Angle.fromDegrees(gyro.getAngle());
    }

    public void setGyro(Angle angle) {
        // Gyro offset is changed in this method ± 90 because intake is considered front side
        gyro.reset();
        gyro.setAngleAdjustment(angle.toAbsDegrees() + 90);
    }
    public void stop() {
        power(0, 0);
    }

}
