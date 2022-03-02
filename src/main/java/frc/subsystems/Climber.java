package frc.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.robot.RobotMap;
import frc.util.SlowSubsystem;

public class Climber extends SlowSubsystem {
    private TalonFX leftClimbMotor; //Falcon 500
    private TalonFX rightClimbMotor; //Falcon 500
    private DoubleSolenoid leftExtender;
    private DoubleSolenoid rightExtender;
    private double climbSpeed = 0.5;

    public Climber() {
        //Instantiate climbers motors with ports from RobotMap
        leftClimbMotor = new TalonFX(RobotMap.LEFT_CLIMB_MOTOR);
        rightClimbMotor = new TalonFX(RobotMap.RIGHT_CLIMB_MOTOR);

        leftClimbMotor.setNeutralMode(NeutralMode.Brake);
        rightClimbMotor.setNeutralMode(NeutralMode.Brake);

        //Instantiate piston double solenoids with the Pneumatics Hub port and the solenoid ports from RobotMap
        leftExtender = new DoubleSolenoid(PneumaticsModuleType.REVPH, RobotMap.LEFT_CLIMBER_FORWARD_SOLENOID,
                RobotMap.LEFT_CLIMBER_REVERSE_SOLENOID);
        rightExtender = new DoubleSolenoid(PneumaticsModuleType.REVPH, RobotMap.RIGHT_CLIMBER_FORWARD_SOLENOID,
                RobotMap.RIGHT_CLIMBER_REVERSE_SOLENOID);


    }

    public void winchLeftUp() {
        //Extends the climber upwards
        leftClimbMotor.set(ControlMode.PercentOutput, climbSpeed * slow);
    }
    public void winchRightUp() {
        rightClimbMotor.set(ControlMode.PercentOutput, climbSpeed * slow);
    }

    public void winchLeftDown() {
        //Descends the climber
        leftClimbMotor.set(ControlMode.PercentOutput, -climbSpeed * slow);
    }
    public void winchRightDown() {
        rightClimbMotor.set(ControlMode.PercentOutput, -climbSpeed * slow);
    }

    public void stopLeftClimber() {
        //Stops the climber
        leftClimbMotor.set(ControlMode.PercentOutput, 0);
    }
    
    public void stopRightClimber() {
        rightClimbMotor.set(ControlMode.PercentOutput, 0);
    }

    //TODO: add the climber piston methods
    public void pistonExtendLeft() {
        leftExtender.set(DoubleSolenoid.Value.kForward);
    }
    public void pistonExtendRight() {
        rightExtender.set(DoubleSolenoid.Value.kForward);
    }

    public void pistonRetractLeft() {
        leftExtender.set(DoubleSolenoid.Value.kReverse);
    }
    public void pistonRetractRight() {
        rightExtender.set(DoubleSolenoid.Value.kReverse);
    }








}

