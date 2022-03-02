package frc.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.subsystems.*;

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
    public Compressor phCompressor;
    public static PowerDistribution powerDistributionHub = new PowerDistribution();

    // Always create oi after all subsystems
    public static OI oi = new OI();

    @Override
    public void robotInit() {
        phCompressor = new Compressor(RobotMap.PNEUMATICS_HUB, PneumaticsModuleType.REVPH);
    }

    /**
     * This function is called every robot packet, no matter the mode. Use
     * this for items like diagnostics that you want ran during disabled,
     * autonomous, teleoperated and test.
     */
    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
        double currentVoltage = powerDistributionHub.getVoltage();
        double voltage = RobotController.getBrownoutVoltage();

        if (currentVoltage < (voltage - 0.4)) {
            return;
        }

        double max = Double.NEGATIVE_INFINITY;
        Object[] subsystems = {};
        int maxIndex = -1;

        for (int i = 0; i < 20; i++) {
            double current = powerDistributionHub.getCurrent(i);
            if (current > max) {
                max = current;
                maxIndex = i;
            }
        }
        
    }

    @Override
    public void autonomousInit() {
        // Example of setting auto: Scheduler.getInstance().add(YOUR AUTO);

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

    /**
     * This function is called periodically during test mode.
     */
    @Override
    public void testPeriodic() {
    }
}