package frc.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.CAN;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Shooter extends SubsystemBase {

    public CANSparkMax flywheelMotor; //Neo 
    public CANSparkMax indexerMotor; //Falcon 500

    private CANSparkMax turretMotor; //NEO 550 Motor
    private RelativeEncoder turretEncoder;

    // The number of rotations that the turret can turn in either direction 
    //-56 47
    
    // 8 rotations of the motor is one rotation of the turret
    private final double MOTOR_TURRET_CONVERSION_FACTOR = 1/8;

    private final double MAX_TURRET_POWER = 0.3;

    private PIDController turretPID;
    private final double kP = 0.025;
    private final double kI = 0;
    private final double kD = 0;

    public double targetRPM = 5000;

    private PIDController flywheelPID;


    public Shooter() {
        // Instantiate shooter and indexer motors with ports from RobotMap
        flywheelMotor = new CANSparkMax(RobotMap.SHOOTER_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);
        indexerMotor = new CANSparkMax(RobotMap.INDEXER_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);

        indexerMotor.setInverted(false);

        // Instantiate turret motor as brushless motor with port from RobotMap
        turretMotor = new CANSparkMax(RobotMap.TURRET_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);
        turretMotor.setIdleMode(IdleMode.kBrake);
        turretEncoder = turretMotor.getEncoder();
        turretEncoder.setPositionConversionFactor(MOTOR_TURRET_CONVERSION_FACTOR);
        turretEncoder.setPosition(0);

        // Instantiate turretPid with kP, kI, kD
        turretPID = new PIDController(kP, kI, kD);

        flywheelPID = new PIDController(0, 0, 0);

        flywheelPID.setP(0.00003);
        flywheelPID.setI(0.00000);
        flywheelPID.setD(0.00000);
        flywheelMotor.setIdleMode(IdleMode.kCoast);
    }
    
    public void setTurretPower(double power) {
        // Make sure that the turret power isn't too fast by limiting it from -MAX_TURRET_POWER to MAX_TURRET_POWER
        power = Math.max(-MAX_TURRET_POWER, Math.min(MAX_TURRET_POWER, power));

        // Only run the turret if it won't overrotate and break
        // If the turret is too far over the range only allow negative motion, and vice versa
        if(turretEncoder.getPosition() >= 65 && power > 0) {
            turretMotor.set(0);
            return;
        }
        if(turretEncoder.getPosition() <= -73 && power < 0) {
            turretMotor.set(0);
            return;
        }

        // SmartDashboard.putNumber("Turret power", power);

        turretMotor.set(power);
    }

    public RelativeEncoder getTurretEncoder() {
        return turretEncoder;
    }

    public void setShooterPercent(double percent) {
        flywheelMotor.set(percent);
    }

    // // speed in RPM
    // public void setFlywheelSpeed(double speed) {
    //     flywheelPID.setReference(speed, ControlType.kVelocity);
    // }

    public void stopShooter() {
        flywheelMotor.set(0);
    }

    public void runIndexer(double power) {
        indexerMotor.set(power);
    }

    

    public void stopIndexer() {
        indexerMotor.set(0);
    }

    public void stopTurret() {
        turretMotor.set(0);
    }

    public PIDController getTurretPID() {
        return turretPID;
    }

    public PIDController getFlywheelPID() {
        return flywheelPID;
    }

    public double getRPM() {
        return flywheelMotor.getEncoder().getVelocity();
    }

    public void resetTurretPosition() {
        turretEncoder.setPosition(0);
    }
}
