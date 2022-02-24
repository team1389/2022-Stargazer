package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.commands.RunIntake;
import frc.commands.Shoot;
import frc.commands.ShootWithSensors;
import frc.commands.StageOneClimb;
import frc.commands.StageTwoClimb;
import frc.commands.TeleOpDrive;
import frc.util.TwoButtonTrigger;


public class OI {


    public XboxController driveController, manipController;
    private JoystickButton shootXBtn, intakeYBtn, climbRBumper, climbLBumper, climbABtn, climbBBtn; 


    public OI() {
        initControllers();
        
        Robot.drivetrain.setDefaultCommand(new TeleOpDrive());
    }

    /**
     * Initialize JoystickButtons and Controllers
     */
    private void initControllers() {
        driveController = new XboxController(0);
        manipController = new XboxController(1);

        intakeYBtn = new JoystickButton(manipController, XboxController.Button.kY.value);
        intakeYBtn.whenHeld(new RunIntake());

        shootXBtn = new JoystickButton(manipController, XboxController.Button.kX.value);
        shootXBtn.whenHeld(new Shoot());

        climbRBumper = new JoystickButton(manipController, XboxController.Button.kRightBumper.value);
        climbLBumper = new JoystickButton(manipController, XboxController.Button.kLeftBumper.value);
        TwoButtonTrigger stageOneTrigger = new TwoButtonTrigger(climbRBumper, climbLBumper);
        stageOneTrigger.whenActive(new StageOneClimb(2));  //check time

        climbABtn = new JoystickButton(manipController, XboxController.Button.kA.value);
        climbBBtn = new JoystickButton(manipController, XboxController.Button.kB.value);
        TwoButtonTrigger stageTwoTrigger = new TwoButtonTrigger(climbABtn, climbBBtn);
        stageTwoTrigger.whenActive(new StageTwoClimb());
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