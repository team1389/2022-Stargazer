package frc.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.SparkMaxPIDController;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Shooter extends SubsystemBase {

    private TalonSRX leftShooterMotor; //Falcon 500
    private TalonSRX rightShooterMotor; //Falcon 500
    private CANSparkMax turretMotor; //NEO Motor
    private CANSparkMax indexerMotor; //NEO motor


    private SparkMaxPIDController turretPID;
    private final double kP = 0;
    private final double kI = 0;
    private final double kD = 0;


    public Shooter() {
        leftShooterMotor = new TalonSRX(RobotMap.LEFT_SHOOTER_MOTOR);
        rightShooterMotor = new TalonSRX(RobotMap.RIGHT_SHOOTER_MOTOR);

        turretMotor = new CANSparkMax(RobotMap.TURRET_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);


        turretPID = turretMotor.getPIDController();
        turretPID.setP(kP);
        turretPID.setI(kI);
        turretPID.setD(kD);

        indexerMotor = new CANSparkMax(RobotMap.INDEXER_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);
    }

    public void setShooterPercent(double percent) {
        leftShooterMotor.set(ControlMode.PercentOutput, percent);
        rightShooterMotor.set(ControlMode.PercentOutput, percent);
    }

    public void stopShooter() {
        leftShooterMotor.set(ControlMode.PercentOutput, 0);
        rightShooterMotor.set(ControlMode.PercentOutput, 0);
    }

    public void runIndexer() {
        indexerMotor.set(1);
    }

    public void stopIndexer() {
        indexerMotor.set(0);
    }

}

