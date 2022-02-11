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

    // Always create oi after all subsystems
    public static OI oi = new OI();

    @Override
    public void robotInit() {
        Robot.drivetrain.frontLeft.rotateRelativeEncoder.setPosition(0);
        Robot.drivetrain.frontRight.rotateRelativeEncoder.setPosition(0);
        Robot.drivetrain.backLeft.rotateRelativeEncoder.setPosition(0);
        Robot.drivetrain.backRight.rotateRelativeEncoder.setPosition(0);

        Robot.drivetrain.frontLeft.coordinateRelativeEncoder();
        Robot.drivetrain.frontRight.coordinateRelativeEncoder();
        Robot.drivetrain.backLeft.coordinateRelativeEncoder();
        Robot.drivetrain.backRight.coordinateRelativeEncoder();

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

    @Override
    public void teleopInit() {
        SwerveWheel[] arr = {
            Robot.drivetrain.frontLeft,
            Robot.drivetrain.frontRight,
            Robot.drivetrain.backLeft,
            Robot.drivetrain.backRight
        };
        
        for (SwerveWheel wheel: arr) {
            wheel.coordinateRelativeEncoder();
            wheel.gotoAngle(0);
        }
        
        
        
        
    }
}