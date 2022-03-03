package frc.robot;

import java.util.Arrays;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.subsystems.*;
import frc.util.Functions;
import frc.util.SlowSubsystem;

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


    // How subsystem maps to subsystem IDs, this is also the order it will limit power in
    // Drivetrain -> 0
    public static final int DRIVETRAIN_ID = 0; 
    // Intake -> 1
    public static final int INTAKE_ID = 1; 
    // Hopper -> 2
    public static final int HOPPER_ID = 2; 
    // Climber -> 3
    public static final int CLIMBER_ID = 3; 
    // Shooter -> 4
    public static final int SHOOTER_ID = 4; 

    static SlowSubsystem[] slow = new SlowSubsystem[5];
    static final double MAX_CURRENT = 220;
    static double[] slowValues = {
            1.0, 
            1.0, 
            1.0, 
            1.0, 
            1.0 
    };

    // Always create oi after all subsystems
    public static OI oi = new OI();

    @Override
    public void robotInit() {
        phCompressor = new Compressor(RobotMap.PNEUMATICS_HUB, PneumaticsModuleType.REVPH);
        slow[DRIVETRAIN_ID] = drivetrain;
        slow[INTAKE_ID] = intake;
        slow[HOPPER_ID] = hopper;
        slow[CLIMBER_ID] = climber;
        slow[SHOOTER_ID] = shooter;
    }

    /**
     * This function is called every robot packet, no matter the mode. Use
     * this for items like diagnostics that you want ran during disabled,
     * autonomous, teleoperated and test.
     */
    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
        limitCurrent();
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

    private void limitCurrent() {
        double currentCurrent = powerDistributionHub.getTotalCurrent();

        if (currentCurrent < MAX_CURRENT) {
            // No need to limit power.
            return;
        }


        System.err.println("Over current limit, limiting power");

        

        double[] currentArr = {
                0.0,
                0.0,
                0.0,
                0.0,
                0.0
        };

        // Group power by subsystem:
        //
        // How PDH maps to subsystem
        // 0 -> CAN 10 -> Drivetrain
        currentArr[DRIVETRAIN_ID] += powerDistributionHub.getCurrent(0);
        // 1 -> CAN 9 -> Drivetrain
        currentArr[DRIVETRAIN_ID] += powerDistributionHub.getCurrent(1);
        // 2 -> CAN ? -> Climber
        currentArr[CLIMBER_ID] += powerDistributionHub.getCurrent(2);
        // 3 -> N/A
        // 4 -> N/A
        // 5 -> N/A
        // 6 -> N/A
        // 7 -> N/A
        // 8 -> CAN 8 -> Drivetrain
        currentArr[DRIVETRAIN_ID] += powerDistributionHub.getCurrent(8);
        // 9 -> CAN 7 -> Drivetrain
        currentArr[DRIVETRAIN_ID] += powerDistributionHub.getCurrent(9);
        // 10 -> CAN 3 -> Drivetrain
        currentArr[DRIVETRAIN_ID] += powerDistributionHub.getCurrent(10);
        // 11 -> CAN 4 -> Drivetrain
        currentArr[DRIVETRAIN_ID] += powerDistributionHub.getCurrent(11);
        // 12 -> CAN 19 -> Shooter
        currentArr[SHOOTER_ID] += powerDistributionHub.getCurrent(12);
        // 13 -> CAN 18 -> Hopper
        currentArr[HOPPER_ID] += powerDistributionHub.getCurrent(13);
        // 14 -> CAN 17 -> Shooter
        currentArr[SHOOTER_ID] += powerDistributionHub.getCurrent(14);
        // 15 -> CAN 16 -> Shooter
        currentArr[SHOOTER_ID] += powerDistributionHub.getCurrent(15);
        // 16 -> CAN 15 -> Intake
        currentArr[INTAKE_ID] += powerDistributionHub.getCurrent(16);
        // 17 -> CAN 5 -> Drivetrain
        currentArr[DRIVETRAIN_ID] += powerDistributionHub.getCurrent(17);
        // 18 -> CAN 6 -> Drivetrain
        currentArr[DRIVETRAIN_ID] += powerDistributionHub.getCurrent(18);
        // 19 -> CAN ? -> Climber
        currentArr[CLIMBER_ID] += powerDistributionHub.getCurrent(19);

        // See extra current being consumed by other things on the robot:
        double sum = Functions.sum(currentArr);
        double extraCurrent = currentCurrent - sum;
        if (extraCurrent > MAX_CURRENT) {
            // Somehow, the remaining unaccounted for current (should be at most 80 amps if
            // the breakers work) is making the robot dangerously close to browning out.
            System.err.println(
                    "How on earth are ports 20-23 on the PDH taking so much current, this literally shouldn't be possible.");
            return;
        }

        // After got the current by subsystem or something else, limit the current

        // calculate theoretical currents given values in `slowValues`, assuming current
        // scales linearly and further assuming none of the values in `slowValues` are 0
        double[] theoreticalCurrent = new double[currentArr.length];
        for (int x = 0; x < currentArr.length; x++) {
            theoreticalCurrent[x] = currentArr[x] / slowValues[x];
        }
        // reset slowValues to all ones
        Arrays.fill(slowValues, 1);

        // Summing currents, and if its still over max current, you have to reduce more.
        sum = Functions.sum(theoreticalCurrent) + extraCurrent;

        for (int i = 0; i < currentArr.length; i++) {
            // We want to solve the equation `sum + extraCurrent - x = maxCurrent`, for x
            // Therefore x = sum + extraCurrent - maxCurrent
            double x = sum + extraCurrent - MAX_CURRENT;
            // x is the amount of current we need to remove now

            // if it is more than the current we can currently remove, pass it on to the
            // next loop iteration, while removing as much as possible
            if (x > theoreticalCurrent[i]) {
                // this is 10% and not 0% because of divide by zero errors and the y-intercept of the motor power
                slowValues[i] = 0.1;
                theoreticalCurrent[i] *= 0.1;

            } else {
                double current = theoreticalCurrent[i];
                // solving for percent: x - (current * percent) = 0
                // percent = x / current
                double percent = x / current;
                slowValues[i] = percent;
                theoreticalCurrent[i] *= percent;
                // we're done because there's no more current to remove
                break;

            }

            // Don't forget to recalculate the sum.
            sum = Functions.sum(theoreticalCurrent) + extraCurrent;
        }

        // now to apply the calculated slow values:
        for (int x = 0; x < slowValues.length; x++) {
            slow[x].slow = slowValues[x];
        }

    }
}