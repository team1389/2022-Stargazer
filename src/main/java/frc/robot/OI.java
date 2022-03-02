package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.commands.ExtendIntake;
import frc.commands.RunHopper;
import frc.commands.RunIndexer;
import frc.commands.RunIntake;
import frc.commands.SetShooterRPM;
import frc.commands.TurretTracking;
import frc.commands.Shoot;
import frc.commands.ShootWithSensors;
import frc.commands.TestSystem;
import frc.util.TwoButtonTrigger;


public class OI {
    public ExtendIntake extendIntake;


    public XboxController driveController, manipController;
    private JoystickButton manipXBtn, manipABtn, manipRBumper, manipLBumper, manipLTrigger, manipRTrigger, manipUpDPad, manipDownDPad; 
    public JoystickButton driveRBumper, driveLBumper;


    public OI() {
        initControllers();
        
        //Robot.drivetrain.setDefaultCommand(new TeleOpDrive());
        //Robot.shooter.setDefaultCommand(new RunIndexer());
        //Robot.shooter.setDefaultCommand(new InstantCommand(() -> Robot.shooter.setTurretPower(0.1)));
        //Robot.intake.setDefaultCommand(new RunIntake());
        //Robot.shooter.setDefaultCommand(new SetShooterRPM(100));

        //Robot.hopper.setDefaultCommand(new RunHopper()); //Good
        //Robot.shooter.setDefaultCommand(new SetShooterRPM(1500)); //Good
        //Robot.shooter.setDefaultCommand(new RunIndexer());
        //Robot.intake.setDefaultCommand(new RunIntake());
        //Robot.shooter.setDefaultCommand(new TestSystem());
        //Robot.shooter.setDefaultCommand(new RunIndexer());

        extendIntake = new ExtendIntake();
        extendIntake.schedule();


    }

    /**
     * Initialize JoystickButtons and Controllers
     */
    private void initControllers() {
        //driveController = new XboxController(0);
        manipController = new XboxController(1);

        // // Hold Manip A Button --> Run Intake
        //manipABtn = new JoystickButton(manipController, XboxController.Button.kA.value);
        // manipABtn.whenHeld(new RunIntake(true));
        //shootXBtn = new JoystickButton(manipController, XboxController.Button.kX.value);
        //shootXBtn.whenHeld(new Shoot());

        


    }

    public double getDriverLeftX() {
        return driveController.getLeftX();
    }
    public double getDriverLeftY() {
        return driveController.getLeftY();
    }
    public double getDriverRightX() {
        return driveController.getRightX();
    }


}