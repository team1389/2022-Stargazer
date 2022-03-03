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

    static SlowSubsystem[] slow = { drivetrain, intake, hopper, climber, shooter };
    static final double maxCurrent = 220;
    static double[] slowValues = {
            1.0, // Drivetrain
            1.0, // Intake
            1.0, // Hopper
            1.0, // Climber
            1.0 // shooter
    };

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

        if (currentCurrent < maxCurrent) {
            // No need to limit power.
            return;
        }

        // How subsystem maps to subsystem IDs
        // Drivetrain -> 0
        // Intake -> 1
        // Hopper -> 2
        // Climber -> 3
        // Shooter -> 4
        double[] currentArr = {
                0.0, // Drivetrain
                0.0, // Intake
                0.0, // Hopper
                0.0, // Climber
                0.0 // shooter
        };

        // Group power by subsystem:
        //
        // How PDH maps to subsystem
        // 0 -> CAN 10 -> Drivetrain -> 0
        currentArr[0] += powerDistributionHub.getCurrent(0);
        // 1 -> CAN 9 -> Drivetrain -> 0
        currentArr[0] += powerDistributionHub.getCurrent(1);
        // 2 -> CAN ? -> Climber -> 3
        currentArr[3] += powerDistributionHub.getCurrent(2);
        // 3 -> N/A
        // 4 -> N/A
        // 5 -> N/A
        // 6 -> N/A
        // 7 -> N/A
        // 8 -> CAN 8 -> Drivetrain -> 0
        currentArr[0] += powerDistributionHub.getCurrent(8);
        // 9 -> CAN 7 -> Drivetrain -> 0
        currentArr[0] += powerDistributionHub.getCurrent(9);
        // 10 -> CAN 3 -> Drivetrain -> 0
        currentArr[0] += powerDistributionHub.getCurrent(10);
        // 11 -> CAN 4 -> Drivetrain -> 0
        currentArr[0] += powerDistributionHub.getCurrent(11);
        // 12 -> CAN 19 -> Shooter -> 4
        currentArr[4] += powerDistributionHub.getCurrent(12);
        // 13 -> CAN 18 -> Hopper -> 2
        currentArr[2] += powerDistributionHub.getCurrent(13);
        // 14 -> CAN 17 -> Shooter -> 4
        currentArr[4] += powerDistributionHub.getCurrent(14);
        // 15 -> CAN 16 -> Shooter -> 4
        currentArr[4] += powerDistributionHub.getCurrent(15);
        // 16 -> CAN 15 -> Intake -> 1
        currentArr[1] += powerDistributionHub.getCurrent(16);
        // 17 -> CAN 5 -> Drivetrain -> 0
        currentArr[0] += powerDistributionHub.getCurrent(17);
        // 18 -> CAN 6 -> Drivetrain -> 0
        currentArr[0] += powerDistributionHub.getCurrent(18);
        // 19 -> CAN ? -> Climber -> 3
        currentArr[3] += powerDistributionHub.getCurrent(19);

        // See extra current being consumed by other things on the robot:
        double sum = Functions.sum(currentArr);
        double extraCurrent = currentCurrent - sum;
        if (extraCurrent > maxCurrent) {
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
            double x = sum + extraCurrent - maxCurrent;
            // x is the amount of current we need to remove now

            // if it is more than the current we can currently remove, pass it on to the
            // next loop iteration, while removing as much as possible
            if (x > theoreticalCurrent[i]) {
                // this is 1% and not 0% because of divide by zero errors
                slowValues[i] = 0.01;
                theoreticalCurrent[i] *= 0.01;

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