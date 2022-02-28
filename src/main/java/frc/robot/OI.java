package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.commands.RunIndexer;
import frc.commands.SetShooterRPM;
import frc.commands.Shoot;
import frc.commands.ShootWithSensors;
import frc.util.TwoButtonTrigger;


public class OI {


    public XboxController driveController, manipController;
    private JoystickButton shootXBtn, intakeYBtn, climbRBumper, climbLBumper, climbABtn, climbBBtn; 


    public OI() {
        initControllers();
        
        //Robot.shooter.setDefaultCommand(new RunIndexer());
    }

    /**
     * Initialize JoystickButtons and Controllers
     */
    private void initControllers() {
        driveController = new XboxController(0);
        manipController = new XboxController(1);


        shootXBtn = new JoystickButton(manipController, XboxController.Button.kX.value);
        shootXBtn.whenHeld(new Shoot());

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