package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.commands.RunIndexer;
import frc.commands.RunIntake;
import frc.commands.Shoot;
import frc.commands.ShootWithSensors;
import frc.commands.StageOneClimb;
import frc.commands.StageTwoClimb;
import frc.commands.TeleOpDrive;
import frc.util.TwoButtonTrigger;


public class OI {


    public XboxController driveController, manipController;
    private JoystickButton shootXBtn, intakeABtn, climbRBumper, climbLBumper, climbATrigger, climbBTrigger, BIndexerUpDPad, BIntakeDownDPad; 
    public JoystickButton driveaButton;


    public OI() {
        initControllers();
        
        driveaButton = new JoystickButton(driveController, XboxController.Button.kA.value);
        driveaButton.whenPressed(new InstantCommand(() -> Robot.drivetrain.toggleFieldOriented()));

        Robot.drivetrain.setDefaultCommand(new TeleOpDrive());
    }

    /**
     * Initialize JoystickButtons and Controllers
     */
    private void initControllers() {
        driveController = new XboxController(0);
        manipController = new XboxController(1);

        intakeABtn = new JoystickButton(manipController, XboxController.Button.kA.value);
        intakeABtn.whenHeld(new RunIntake(true));

        shootXBtn = new JoystickButton(manipController, XboxController.Button.kX.value);
        shootXBtn.whenHeld(new Shoot());

        climbRBumper = new JoystickButton(manipController, XboxController.Button.kRightBumper.value);
        climbLBumper = new JoystickButton(manipController, XboxController.Button.kLeftBumper.value);
        TwoButtonTrigger stageOneTrigger = new TwoButtonTrigger(climbRBumper, climbLBumper);
        stageOneTrigger.whenActive(new StageOneClimb(2));  //check time

        climbATrigger = new JoystickButton(manipController, XboxController.Button.kA.value);
        climbBTrigger = new JoystickButton(manipController, XboxController.Button.kB.value);
        TwoButtonTrigger stageTwoTrigger = new TwoButtonTrigger(climbATrigger, climbBTrigger);
        stageTwoTrigger.whenActive(new StageTwoClimb());

        // JoystickButton is 1 indexed
        BIndexerUpDPad = new JoystickButton(manipController, 12 + 1); // 12 is up on the D-Pad
        BIndexerUpDPad.whenActive(new RunIndexer(false));
        

        BIntakeDownDPad = new JoystickButton(manipController, 13 + 1); // 13 is down on the D-Pad
        BIntakeDownDPad.whenActive(new RunIntake(false));

        
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