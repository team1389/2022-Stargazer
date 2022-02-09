package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import frc.commands.OneWheelTest;
import frc.commands.TeleOpDrive;
import frc.commands.TestAngles;

public class OI {


    public XboxController driveController, manipController;

    public OI() {
        initControllers();

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