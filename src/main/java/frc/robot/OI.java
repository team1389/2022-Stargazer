package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.commands.SetShooterRPM;
import frc.commands.ShootWithSensors;
import frc.commands.TestShooter;
import frc.util.TwoButtonTrigger;


public class OI {


    public XboxController driveController, manipController;
    private JoystickButton shootXBtn, intakeYBtn, climbRBumper, climbLBumper, climbABtn, climbBBtn; 


    public OI() {
        initControllers();
        Robot.shooter.setDefaultCommand(new SetShooterRPM(1000));
    }

    /**
     * Initialize JoystickButtons and Controllers
     */
    private void initControllers() {

        manipController = new XboxController(1);

        shootXBtn = new JoystickButton(manipController, XboxController.Button.kX.value);
        shootXBtn.whenHeld(new SetShooterRPM(1000));


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