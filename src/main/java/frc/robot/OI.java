package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import frc.commands.OneWheelTest;
import frc.commands.TeleOpDrive;
import frc.commands.TestAngles;

public class OI {


    public XboxController driveController, manipController;
    public Joystick flightStick;

    public OI() {
        initControllers();

    }

    /**
     * Initialize JoystickButtons and Controllers
     */
    private void initControllers() {
        flightStick = new Joystick(0);
        //driveController = new XboxController(0);
        manipController = new XboxController(1);

        Robot.drivetrain.setDefaultCommand(new TeleOpDrive());
    }

    public double getDriverLeftX() {
        //return driveController.getLeftX();
        return flightStick.getX();
    }
    public double getDriverLeftY() {
        //negative because we want up to be a positive value
        return -flightStick.getY();
    }
    public double getDriverRightX() {
        return flightStick.getTwist();
    }


}