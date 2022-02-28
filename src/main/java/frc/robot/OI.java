package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.commands.TeleOpDrive;


public class OI {


    public XboxController driveController, manipController;
    private JoystickButton shootXBtn, intakeYBtn, climbRBumper, climbLBumper, climbABtn, climbBBtn; 
    public JoystickButton aButton;


    public OI() {
        initControllers();
        aButton = new JoystickButton(driveController, XboxController.Button.kA.value);
        aButton.whenPressed(new InstantCommand(() -> Robot.drivetrain.toggleFieldOriented()));

        Robot.drivetrain.setDefaultCommand(new TeleOpDrive());
    }

    /**
     * Initialize JoystickButtons and Controllers
     */
    private void initControllers() {
        driveController = new XboxController(0);
    }

    public double getDriverLeftX() {
        return driveController.getLeftX();
    }
    public double getDriverLeftY() {
        return -driveController.getLeftY();
    }
    public double getDriverRightX() {
        return driveController.getRightX();
    }


}