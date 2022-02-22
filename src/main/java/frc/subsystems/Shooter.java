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

    public CANSparkMax shooterMotor; //Neo 
    private CANSparkMax indexerMotor; //Falcon 500

    private CANSparkMax turretMotor; //NEO 550 Motor

    private PIDController turretPID;
    private final double kP = 0;
    private final double kI = 0;
    private final double kD = 0;

    private PIDController flywheelPID;


    public Shooter() {
        //Instantiate shooter and indexer motors with ports from RobotMap
        shooterMotor = new CANSparkMax(RobotMap.SHOOTER_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);
        indexerMotor = new CANSparkMax(RobotMap.INDEXER_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);

        //Instantiate turret motor as brushless motor with port from RobotMap
        turretMotor = new CANSparkMax(RobotMap.TURRET_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);

        //Instantiate turretPid with kP, kI, kD
        turretPID = new PIDController(kP, kI, kD);

        //Flywheel PID controller
        flywheelPID = new PIDController(0.00001, 0, 0);
    }

    public void setShooterPercent(double percent) {
        shooterMotor.set(percent);
    }

    public PIDController getFlywheelPID() {
        return flywheelPID;
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

