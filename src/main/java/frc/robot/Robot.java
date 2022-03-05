package frc.robot;


import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.autos.OneBallAuto;
import frc.subsystems.*;
import frc.util.SwerveTelemetry;


/**
 * Don't change the name of this class since the VM is set up to run this
 */
public class Robot extends TimedRobot {

    /**
     * Initialize all systems here as public & static.
     * Ex: public static System system = new System();
     */


    public static Drivetrain drivetrain = new Drivetrain();
    public static Intake intake = new Intake();
    public static Hopper hopper = new Hopper();
    public static Climber climber = new Climber();
    public static Shooter shooter = new Shooter();
    public static ML ml = new ML();
    public static PneumaticHub pneumaticHub = new PneumaticHub(RobotMap.PNEUMATICS_HUB);
    public  Compressor phCompressor;
    public static PowerDistribution powerDistributionHub = new PowerDistribution();
    public SequentialCommandGroup autoCommand;

    //For current limiting
    public static boolean isShooting = false;

    //Always create oi after all subsystems
    public static OI oi = new OI();

    @Override
    public void robotInit() {
        //phCompressor = new Compressor(RobotMap.PNEUMATICS_HUB, PneumaticsModuleType.REVPH);
        //pneumaticHub.enableCompressorDigital();
        pneumaticHub.enableCompressorHybrid(100, 120);
        //pneumaticHub.disableCompressor();
        // pneumaticHub.setSolenoids(1 << RobotMap.LEFT_CLIMBER_FORWARD_SOLENOID | 1 << RobotMap.LEFT_CLIMBER_REVERSE_SOLENOID | 1 << RobotMap.RIGHT_CLIMBER_FORWARD_SOLENOID | 1 << RobotMap.RIGHT_CLIMBER_REVERSE_SOLENOID |
        // 1 << RobotMap.RIGHT_INTAKE_FORWARD_SOLENOID | 1 << RobotMap., values);
        CameraServer.startAutomaticCapture();
    }

    /**
     * This function is called every robot packet, no matter the mode. Use
     * this for items like diagnostics that you want ran during disabled,
     * autonomous, teleoperated and test.
     */
    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
        drivetrain.updateOdometry();
    }


    @Override
    public void autonomousInit() {
        //Example of setting auto: Scheduler.getInstance().add(YOUR AUTO);
        autoCommand = new OneBallAuto();
        CommandScheduler.getInstance().schedule(autoCommand);

        Robot.drivetrain.coordinateAbsoluteEncoders();
        Robot.drivetrain.setGyro(0);
        
    }

    /**
     * This function is called periodically during autonomous.
     */
    @Override
    public void autonomousPeriodic() {
    }

    /**
     * This function is called periodically during operator control.
     */
    @Override
    public void teleopPeriodic() {
        Robot.drivetrain.coordinateAbsoluteEncoders();
    }

    @Override
    public void teleopInit() {
        Robot.drivetrain.coordinateAbsoluteEncoders();
        Robot.drivetrain.setGyro(0);

        Robot.drivetrain.fieldOriented = false;

        SwerveTelemetry frontLeftTelemetry = new SwerveTelemetry(Robot.drivetrain.frontLeft);
        //SendableRegistry.add(frontLeftTelemetry, "Swerve");
        SendableRegistry.addLW(frontLeftTelemetry, "FL Swerve");

        // Set to 1 to turn off, 3 to turn on, 2 to ~~unleash death~~ blink
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);

        // Climber
        // pneumaticHub.setSolenoids(1 << 4 | 1 << 11, 1 << 4);
        // pneumaticHub.setSolenoids(
        //     1 << 4 | 1 << 5 |  | 1 << 10 | 1 << 11, 
        //     // 1 << 8 | 1 << 9 | 1 << 10 | 1 << 11
        //     // 1 << 4 | 1 << 5 | 1 << 6 | 1 << 7
        // );
        // 10 is climber ? a
        // 11 is climber ? a
        // 5 is climber ? b
        // 4 is climber ? b 
        

        // 4  top            B left climber forward
        // 5  second highest B right intake forward
        // 6  second lowest  B right climber forward
        // 7  lowest         B left  intake forward
        // 8  lowest         A left  intake reverse
        // 9  second lowest  A right climber reverse
        // 10 second highest A right intake reverse
        // 11 top            A left climber reverse

        // System.err.println("HELLO THIS IS A PNEUMATIC HUB: " + pneumaticHub.getSolenoids());
        pneumaticHub.setSolenoids(
            1 << 4 | 1 << 5 |1 << 6 | 1 << 7 | 1 << 8 | 1 << 9 | 1 << 10 | 1 << 11,
            1 << 11 | 1 << 9 //| //1 << 5 | 1 << 7
        );
        // pneumaticHub.disableCompressor();

    }

    /**
     * This function is called periodically during test mode.
     */
    @Override
    public void testPeriodic() {
    }
}