package frc.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.subsystems.Shooter;
import frc.util.SizeLimitedQueue;

public class TurretTracking extends CommandBase {
    Shooter shooter;
    PIDController pid;

    private double turretPower;

    //Limelight values
    private double tv, tx, ty, ta;
    private SizeLimitedQueue recentErrors;
    private Timer timer;

    public TurretTracking() {
        shooter = Robot.shooter;
        recentErrors = new SizeLimitedQueue(15);
        timer = new Timer();
        pid = shooter.getTurretPID();
        
        addRequirements();
    }

    @Override
    public void initialize() {
        super.initialize();
        //Setup limelight through NetworkTables
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3);

        tv = 0; //# of Targets found
        tx = 0; //Vertical offset from -27 degrees to 27
        ty = 0; //Horizontal offset from -20.5 degrees to 20.5
        ta = 0; //Target Area from 0% of image to 100% of image

        timer.reset();
        timer.start();
    }

    @Override
    public void execute() {
        // Updates the limelight values
        fetchValues();
        

        if(tv >= 1) {
            // Sets the pid controller's reference point to the ty and the setpoint to 0
            turretPower = pid.calculate(ty - 6, 0);
            recentErrors.addElement(ty);
        }
        else {

            System.out.print("No target");
        }

        // Sets the shooter to the turret power from PID
        //Robot.shooter.setTurretPower(turretPower);
        SmartDashboard.putNumber("turret power", turretPower);
        SmartDashboard.putString("Shoot status", "turret tracking");
    }

    @Override
    public void end(boolean interrupted) {
        // Stop turret movement and turn off limelight
        Robot.shooter.setTurretPower(0);
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);
    }

    private void fetchValues() {
        tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
        tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
        ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
        ta = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);
    }
    
    @Override
    public boolean isFinished() {
        //TODO: change timeout value
        return Math.abs(recentErrors.getAverage()) < 0.5 || timer.get() > 2;
    }
}
