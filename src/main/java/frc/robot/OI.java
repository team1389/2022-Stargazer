package frc.robot;

import java.sql.Driver;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.autos.OneBallAuto;
import frc.autos.OnemAuto;
import frc.commands.OneWheelTest;
import frc.commands.TeleOpDrive;
import frc.commands.TestAngles;

public class OI {


    public XboxController driveController, manipController;
    public JoystickButton aButton;




    public OI() {
        initControllers();

        aButton = new JoystickButton(driveController, XboxController.Button.kA.value);
        aButton.whenPressed(new InstantCommand(() -> Robot.drivetrain.toggleFieldOriented()));

    }

    /**
     * Initialize JoystickButtons and Controllers
     */
    private void initControllers() {
        driveController = new XboxController(0);
        manipController = new XboxController(1);

        Robot.drivetrain.setDefaultCommand(new TeleOpDrive());

        
    }

    public double getDriverLeftX() {
        return driveController.getLeftX();
    }
    public double getDriverLeftY() {
        //negative because we want up to be a positive value
        return -driveController.getLeftY();
    }
    public double getDriverRightX() {
        return driveController.getRightX();
    }




}