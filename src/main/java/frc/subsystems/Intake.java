package frc.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.util.SlowSubsystem;

public class Intake extends SlowSubsystem {

    CANSparkMax intakeMotor; // NEO 550
    DoubleSolenoid leftExtender;
    DoubleSolenoid rightExtender;

    boolean intakeExtended = false;

    public Intake() {
        // Instantiate intake motor as brushless motor with port from RobotMap
        intakeMotor = new CANSparkMax(RobotMap.INTAKE_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);
        intakeMotor.setInverted(true);

        // Instantiate piston double solenoids with the Pneumatics Hub port and the
        // solenoid ports from RobotMap
        // leftExtender = new DoubleSolenoid(PneumaticsModuleType.REVPH,
        // RobotMap.LEFT_INTAKE_FORWARD_SOLENOID,
        // RobotMap.LEFT_INTAKE_REVERSE_SOLENOID);
        // rightExtender = new DoubleSolenoid(PneumaticsModuleType.REVPH,
        // RobotMap.RIGHT_INTAKE_FORWARD_SOLENOID,
        // RobotMap.RIGHT_INTAKE_REVERSE_SOLENOID);
        // retractIntake();

    }

    public void setIntakePercent(double percent) {
        intakeMotor.set(percent * slow);
    }

    public void stopIntake() {
        intakeMotor.set(0);
    }

    public void extendIntake() {
        // leftExtender.set(DoubleSolenoid.Value.kReverse);
        // rightExtender.set(DoubleSolenoid.Value.kReverse);

        // Get the solenoid state and invert it
        // << moves the `1` into the correct place in the number and the `|` combines
        // them
        // the ones in the bottom turn on and the ones listed in the top but not in the
        // bottom turn off
        Robot.pneumaticHub.setSolenoids(
                1 << RobotMap.RIGHT_INTAKE_REVERSE_SOLENOID | 1 << RobotMap.LEFT_INTAKE_REVERSE_SOLENOID |
                        1 << RobotMap.RIGHT_INTAKE_FORWARD_SOLENOID | 1 << RobotMap.LEFT_INTAKE_FORWARD_SOLENOID,
                1 << RobotMap.RIGHT_INTAKE_REVERSE_SOLENOID | 1 << RobotMap.LEFT_INTAKE_REVERSE_SOLENOID);
        intakeExtended = true;
        // leftExtender.set()
    }

    public void retractIntake() {

        // Get the solenoid state and invert it
        // << moves the `1` into the correct place in the number and the `|` combines
        // them
        // the ones in the bottom turn on and the ones listed in the top but not in the
        // bottom turn off
        Robot.pneumaticHub.setSolenoids(
                1 << RobotMap.RIGHT_INTAKE_REVERSE_SOLENOID | 1 << RobotMap.LEFT_INTAKE_REVERSE_SOLENOID |
                        1 << RobotMap.RIGHT_INTAKE_FORWARD_SOLENOID | 1 << RobotMap.LEFT_INTAKE_FORWARD_SOLENOID,
                1 << RobotMap.RIGHT_INTAKE_FORWARD_SOLENOID | 1 << RobotMap.LEFT_INTAKE_FORWARD_SOLENOID);
        intakeExtended = false;
        // leftExtender.set(DoubleSolenoid.Value.kForward);
        // rightExtender.set(DoubleSolenoid.Value.kForward);
    }

    public void toggleIntakePistons() {
        if (intakeExtended) {
            retractIntake();
        } else {
            extendIntake();
        }
    }
}
