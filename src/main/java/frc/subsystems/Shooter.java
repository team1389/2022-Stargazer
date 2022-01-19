package frc.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.SparkMaxPIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Shooter extends SubsystemBase {

    private TalonFX shooterMotor; //Falcon 500
    private TalonFX indexerMotor; //Falcon 500

    private CANSparkMax turretMotor; //NEO 550 Motor

    private SparkMaxPIDController turretPID;
    private final double kP = 0;
    private final double kI = 0;
    private final double kD = 0;


    public Shooter() {
        shooterMotor = new TalonFX(RobotMap.LEFT_SHOOTER_MOTOR);

        turretMotor = new CANSparkMax(RobotMap.TURRET_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);


        turretPID = turretMotor.getPIDController();
        turretPID.setP(kP);
        turretPID.setI(kI);
        turretPID.setD(kD);

        indexerMotor = new TalonFX(RobotMap.INDEXER_MOTOR);
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

}

