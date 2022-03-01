package frc.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Shooter extends SubsystemBase {

    public CANSparkMax shooterMotor; //Neo 
    private CANSparkMax indexerMotor; //Falcon 500

    private CANSparkMax turretMotor; //NEO 550 Motor
    public RelativeEncoder turretEncoder;

    // The number of rotations that the turret can turn in either direction 
    private final double TURRET_RANGE_OF_MOTION = 0.35;
    
    // 8 rotations of the motor is one rotation of the turret
    private final double MOTOR_TURRET_CONVERSION_FACTOR = 1/8;

    private final double MAX_TURRET_POWER = 0.3;

    private PIDController turretPID;
    private final double kP = 0;
    private final double kI = 0;
    private final double kD = 0;

    private PIDController flywheelPID;


    public Shooter() {
        // Instantiate shooter and indexer motors with ports from RobotMap
        shooterMotor = new CANSparkMax(RobotMap.SHOOTER_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);
        indexerMotor = new CANSparkMax(RobotMap.INDEXER_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);

        // Instantiate turret motor as brushless motor with port from RobotMap
        turretMotor = new CANSparkMax(RobotMap.TURRET_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);
        turretMotor.setIdleMode(IdleMode.kBrake);
        turretEncoder = turretMotor.getEncoder();
        turretEncoder.setPositionConversionFactor(MOTOR_TURRET_CONVERSION_FACTOR);
        turretEncoder.setPosition(0);

        // Instantiate turretPid with kP, kI, kD
        turretPID = new PIDController(kP, kI, kD);

        // Flywheel PID controller
        flywheelPID = new PIDController(0.00001, 0, 0);
    }
    
    public void setTurretPower(double power) {
        // Make sure that the turret power isn't too fast by limiting it from -MAX_TURRET_POWER to MAX_TURRET_POWER
        power = Math.max(-MAX_TURRET_POWER, Math.min(MAX_TURRET_POWER, power));

        // Only run the turret if it won't overrotate and break
        // If the turret is too far over the range only allow negative motion, and vice versa
        if(turretEncoder.getPosition() >= TURRET_RANGE_OF_MOTION && power > 0) {
            turretMotor.set(0);
            return;
        }
        if(turretEncoder.getPosition() <= TURRET_RANGE_OF_MOTION && power < 0) {
            turretMotor.set(0);
            return;
        }

        turretMotor.set(power);
    }

    public void setShooterPercent(double percent) {
        shooterMotor.set(percent);
    }

    public PIDController getFlywheelPID() {
        return flywheelPID;
    }
    // speed in RPM
    public void setFlywheelSpeed(double speed) {
        flywheelPID.setSetpoint(speed);
    }

    public void stopShooter() {
        shooterMotor.set(0);
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

    public double getRPM() {
        return shooterMotor.getEncoder().getVelocity();
    }
}
