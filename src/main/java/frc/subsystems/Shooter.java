package frc.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Shooter extends SubsystemBase {

    private TalonFX shooterMotor; //Falcon 500
    private TalonFX indexerMotor; //Falcon 500

    private CANSparkMax turretMotor; //NEO 550 Motor

    private PIDController turretPID;
    private final double kP = 0;
    private final double kI = 0;
    private final double kD = 0;


    public Shooter() {
        //Instantiate shooter and indexer motors with ports from RobotMap
        shooterMotor = new TalonFX(RobotMap.SHOOTER_MOTOR);
        indexerMotor = new TalonFX(RobotMap.INDEXER_MOTOR);

        //Instantiate turret motor as brushless motor with port from RobotMap
        turretMotor = new CANSparkMax(RobotMap.TURRET_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);

        //Instantiate turretPid with kP, kI, kD
        turretPID = new PIDController(kP, kI, kD);

    }

    public void setShooterPercent(double percent) {
        shooterMotor.set(ControlMode.PercentOutput, percent);
    }

    public void stopShooter() {
        shooterMotor.set(ControlMode.PercentOutput, 0);
    }

    public void runIndexer() {
        indexerMotor.set(ControlMode.PercentOutput, 1);
    }

    public void stopIndexer() {
        indexerMotor.set(ControlMode.PercentOutput, 0);
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

}

