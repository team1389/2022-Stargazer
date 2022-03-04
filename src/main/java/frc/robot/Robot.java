package frc.robot;


import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
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

    //Always create oi after all subsystems
    public static OI oi = new OI();

    @Override
    public void robotInit() {
        //phCompressor = new Compressor(RobotMap.PNEUMATICS_HUB, PneumaticsModuleType.REVPH);
        pneumaticHub.enableCompressorDigital();
        //pneumaticHub.
        //pneumaticHub.disableCompressor();

        CameraServer.startAutomaticCapture();

        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3);
    }

    /**
     * This function is called every robot packet, no matter the mode. Use
     * this for items like diagnostics that you want ran during disabled,
     * autonomous, teleoperated and test.
     */
    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
    }


    @Override
    public void autonomousInit() {
        //Example of setting auto: Scheduler.getInstance().add(YOUR AUTO);
        
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
    }

    @Override
    public void teleopInit() {
        SwerveTelemetry frontLeftTelemetry = new SwerveTelemetry(Robot.drivetrain.frontLeft);
        //SendableRegistry.add(frontLeftTelemetry, "Swerve");
        SendableRegistry.addLW(frontLeftTelemetry, "FL Swerve");


        //frontLeftTelemetry.initSendable(builder);
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);
    }

    /**
     * This function is called periodically during test mode.
     */
    @Override
    public void testPeriodic() {
    }
}