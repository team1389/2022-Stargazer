package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ScheduleCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.commands.ManualTurret;
// import frc.commands.ClimberLeftExtend;
// import frc.commands.ClimberLeftRetract;
// import frc.commands.ClimberRightExtend;
// import frc.commands.ClimberRightRetract;
import frc.commands.RunIndexer;
import frc.commands.RunIntake;
import frc.commands.SetShooterRPM;
import frc.commands.Shoot;
import frc.commands.StageOneClimb;
import frc.commands.StageTwoClimb;
import frc.commands.TeleOpDrive;
import frc.commands.WinchClimber;
import frc.commands.WinchClimber.LeftOrRight;
import frc.util.DPadButton;
import frc.util.TwoButtonTrigger;
import frc.util.DPadButton.Direction;


public class OI {


    public XboxController driveController, manipController;
    private JoystickButton manipXBtn, manipABtn, manipBBtn, manipRBumper, manipLBumper, manipBackBtn, manipStartBtn;
    public JoystickButton driveRBumper, driveLBumper, manipYBtn;
    public DPadButton manipUpDPadButton, manipDownDPadButton;


    public OI() {
       initControllers();
        

        //Robot.drivetrain.setDefaultCommand(new TeleOpDrive());
        //Robot.intake.setDefaultCommand(new RunIntake());
        // Robot.climber.setDefaultCommand(new WinchClimber("right", false));
        // Robot.shooter.setDefaultCommand(new ManualTurret());
        
        //runIndexer = new RunIndexer();
        //runIndexer.schedule();
        
        //Robot.intake.retractIntake();
        //Robot.shooter.setDefaultCommand(new SetShooterRPM(1000));
        // Robot.shooter.setDefaultCommand(new RunIndexer());
        (new InstantCommand(() ->  Robot.intake.extendIntake())).schedule();
    }

    /**
     * Init
     * ialize JoystickButtons and Controllers
     */
    private void initControllers() {
        //driveController = new XboxController(0);
        manipController = new XboxController(1);

        // Driver RBumper Button --> Toggle Field Oriented
        //driveRBumper = new JoystickButton(driveController, XboxController.Button.kRightBumper.value);
        //driveRBumper.whenPressed(new InstantCommand(() -> Robot.drivetrain.toggleFieldOriented()));

        // Driver Left Bumper Button --> Reset field oriented angle
        //driveLBumper = new JoystickButton(driveController, XboxController.Button.kLeftBumper.value);
        //driveLBumper.whenPressed(new InstantCommand(() -> Robot.drivetrain.setGyro(0)));




        // Hold Manip A Button --> Run Intake
        manipABtn = new JoystickButton(manipController, XboxController.Button.kA.value);
        //manipABtn.whenHeld(new RunIntake(true));
        manipABtn.whenPressed(new InstantCommand(() ->  Robot.intake.extendIntake()));


        // Hold Manip X Button --> Run Shooter System
        manipXBtn = new JoystickButton(manipController, XboxController.Button.kX.value);
        manipXBtn.whenHeld(new SetShooterRPM(3000)); //needs to be swithced to shoot

        // Press Manip B buttopn --> Extend or retract intake
        manipBBtn = new JoystickButton(manipController, XboxController.Button.kB.value);
        manipBBtn.whenPressed(new InstantCommand(() -> Robot.intake.toggleIntakePiston()));
        
        //TODO: test the dpad controls
        // JoystickButton is 1 indexed
        // Hold Manip DPad Up --> Reverse Indexer
        // manipUpDPad = new JoystickButton(manipController, 12 + 1); // 12 is up on the D-Pad
        // manipUpDPad.whenActive(new RunIndexer(false));
    
        // // Hold Manip DPad Down --> Reverse Intake
        // manipDownDPad = new JoystickButton(manipController, 13 + 1); // 13 is down on the D-Pad
        // manipDownDPad.whenActive(new RunIntake(false));

        // Hold Manip DPad Up --> Reverse Indexer
        manipUpDPadButton = new DPadButton(manipController, Direction.UP);
        manipUpDPadButton.whenHeld(new RunIndexer(false));

        // Hold Manip DPad Down --> Reverse Intake
        manipDownDPadButton = new DPadButton(manipController, Direction.DOWN);
        manipDownDPadButton.whenHeld(new RunIntake(false));

        // Set climber controls to either manual or automatic
        manipRBumper = new JoystickButton(manipController, XboxController.Button.kRightBumper.value);
        manipLBumper = new JoystickButton(manipController, XboxController.Button.kLeftBumper.value);
        manipBackBtn = new JoystickButton(manipController, XboxController.Button.kBack.value);
        manipStartBtn = new JoystickButton(manipController, XboxController.Button.kStart.value);
        initManualClimber();
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
    public double getManipLeftX() {
        return manipController.getLeftX();
    }

    public boolean getDriverLeftBumper() {
        return driveController.getLeftBumper();
    }

    public void initManualClimber() {
        // Hold Manip LT --> Extend left climber

        manipLBumper.whileHeld(new WinchClimber(LeftOrRight.left, true));

        // Hold Manip RT --> Extend right climber
        manipRBumper.whileHeld(new WinchClimber(LeftOrRight.right, true));

        // Hold Manip LB --> Retract left climber
        manipBackBtn.whileHeld(new WinchClimber(LeftOrRight.left, false));

        // Hold Manip RB --> Retract right climber
        manipStartBtn.whileHeld(new WinchClimber(LeftOrRight.right, false));

        // Press Manip Y --> Toggle left piston
        manipYBtn = new JoystickButton(manipController, XboxController.Button.kY.value);
        manipYBtn.whenPressed(new InstantCommand(() -> Robot.climber.toggleRightPiston()));



    }

    public void initAutomaticClimber() {
        // Hold Manip LB and RB --> Stage One Climb with 2 second wait
        TwoButtonTrigger stageOneTrigger = new TwoButtonTrigger(manipLBumper, manipRBumper);
        //stageOneTrigger.whenActive(new StageOneClimb(2));  //check time

        // Hold Manip LT and RT --> Stage Two Climb
        //TwoButtonTrigger stageTwoTrigger = new TwoButtonTrigger(manipLTrigger, manipRTrigger);
        //stageTwoTrigger.whenActive(new StageTwoClimb());

    }
    

}