package frc.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.SparkMaxPIDController;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Shooter extends SubsystemBase {

    private CANSparkMax shooterMotor; //Neo 
    private CANSparkMax indexerMotor; //Falcon 500

    private CANSparkMax turretMotor; //NEO 550 Motor

    private PIDController turretPID;
    private final double kP = 0;
    private final double kI = 0;
    private final double kD = 0;

    private SparkMaxPIDController flywheelPID;


    public Shooter() {
        //Instantiate shooter and indexer motors with ports from RobotMap
        shooterMotor = new CANSparkMax(RobotMap.SHOOTER_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);
        indexerMotor = new CANSparkMax(RobotMap.INDEXER_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);

        //Instantiate turret motor as brushless motor with port from RobotMap
        turretMotor = new CANSparkMax(RobotMap.TURRET_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);

        //Instantiate turretPid with kP, kI, kD
        turretPID = new PIDController(kP, kI, kD);

        //Flywheel PID controller
        flywheelPID = shooterMotor.getPIDController();

        flywheelPID.setP(0);
        flywheelPID.setI(0);
        flywheelPID.setD(0);

    }

    public void setShooterPercent(double percent) {
        shooterMotor.set(percent);
    }

    // speed in RPM
    public void setFlywheelSpeed(double speed) {
        flywheelPID.setReference(speed, CANSparkMax.ControlType.kVelocity);
    }

    public void stopShooter() {
        shooterMotor.set(0);
    }

    public void runIndexer() {
        indexerMotor.set(0.5);
    }

    public void stopIndexer() {
        indexerMotor.set(0);
    }

    public void setTurretPower(double percent) {
        turretMotor.set(percent);
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

