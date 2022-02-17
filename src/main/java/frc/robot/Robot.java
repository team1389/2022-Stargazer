package frc.robot;


import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.autos.OnemAuto;
import frc.autos.TwoBallAuto;
import frc.subsystems.*;
import frc.util.AutoChooser;

/**
 * Don't change the name of this class since the VM is set up to run this
 */
public class Robot extends TimedRobot {

    /**
     * Initialize all systems here as public & static.
     * Ex: public static System system = new System();
     */


    public static Drivetrain drivetrain = new Drivetrain();
    

    public AutoChooser autoChooser;
    public SequentialCommandGroup autoCommand;
    //Always create oi after all subsystems
    public static OI oi = new OI();

    @Override
    public void robotInit() {
        autoChooser = new AutoChooser();

    }

    /**
     * This function is called every robot packet, no matter the mode. Use
     * this for items like diagnostics that you want ran during disabled,
     * autonomous, teleoperated and test.
     */
    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
        drivetrain.field.setRobotPose(drivetrain.getPose());
        drivetrain.updateOdometry();
    }


    @Override
    public void autonomousInit() {
        //Example of setting auto: Scheduler.getInstance().add(YOUR AUTO);
        Robot.drivetrain.coordinateAbsoluteEncoders();
        Robot.drivetrain.setGyro(0);

        autoCommand = autoChooser.getAutoCommand();

        if(autoCommand != null) {
            CommandScheduler.getInstance().schedule(autoCommand);
        }

        
        PathPlannerTrajectory currentTrajectory = PathPlanner.loadPath("1m Auto", 1, 0.5);

        drivetrain.setPose(currentTrajectory.sample(0).poseMeters);
        drivetrain.field.getObject("traj").setTrajectory(currentTrajectory);
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
        Robot.drivetrain.coordinateAbsoluteEncoders();
        Robot.drivetrain.setGyro(0);




    }
}