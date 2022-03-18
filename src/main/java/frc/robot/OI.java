package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ScheduleCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.commands.GetDistanceToTarget;
import frc.commands.HoldClimberInPlace;
import frc.commands.ManualTurret;
import frc.commands.RunHopper;
// import frc.commands.ClimberLeftExtend;
// import frc.commands.ClimberLeftRetract;
// import frc.commands.ClimberRightExtend;
// import frc.commands.ClimberRightRetract;
import frc.commands.RunIndexer;
import frc.commands.RunIntake;
import frc.commands.SetShooterRPM;
import frc.commands.Shoot;
import frc.commands.ShootWithSensors;
import frc.commands.StageOneClimb;
import frc.commands.StageTwoClimb;
import frc.commands.TeleOpDrive;
import frc.commands.TestAngles;
import frc.commands.ToggleIntakePistons;
import frc.commands.TurretTracking;
import frc.commands.WinchClimber;
import frc.commands.HoldClimberInPlace.RightOrLeft;
import frc.commands.WinchClimber.LeftOrRight;
import frc.util.DPadButton;
import frc.util.TwoButtonTrigger;
import frc.util.DPadButton.Direction;


public class OI {


    public XboxController driveController, manipController;
    private JoystickButton manipXBtn, manipABtn, manipBBtn, manipRBumper, manipLBumper, manipBackBtn, manipStartBtn;
    public JoystickButton driveRBumper, driveLBumper, manipYBtn, manipRTrigger, manipLTrigger;
    public DPadButton manipUpDPadButton, manipDownDPadButton, manipLeftDPadButton, manipRightDPadButton;


    public OI() {
       initControllers();
        

        Robot.drivetrain.setDefaultCommand(new TeleOpDrive());
        // Robot.shooter.setDefaultCommand(new ShootWithSensors());
        //Robot.shooter.setDefaultCommand(new Shoot());
        //Robot.intake.setDefaultCommand(new RunIntake());
        // Robot.climber.setDefaultCommand(new WinchClimber("right", false));
        Robot.shooter.setDefaultCommand(new TurretTracking());
        
        //runIndexer = new RunIndexer();
        //runIndexer.schedule();
        
        //Robot.intake.retractIntake();
        //Robot.shooter.setDefaultCommand(new SetShooterRPM(1000));
        // Robot.shooter.setDefaultCommand(new RunIndexer());
        //(new InstantCommand(() ->  Robot.intake.extendIntake())).schedule();
    }

    /**
     * Init
     * ialize JoystickButtons and Controllers
     */
    private void initControllers() {
        driveController = new XboxController(0);
        manipController = new XboxController(1);

        // Driver RBumper Button --> Toggle Field Oriented
        driveRBumper = new JoystickButton(driveController, XboxController.Button.kRightBumper.value);
        driveRBumper.whenPressed(new InstantCommand(() -> Robot.drivetrain.toggleFieldOriented()));

        // Driver Left Bumper Button --> Reset field oriented angle
        driveLBumper = new JoystickButton(driveController, XboxController.Button.kLeftBumper.value);
        driveLBumper.whenPressed(new InstantCommand(() -> Robot.drivetrain.setGyro(-90)));

        //Hold Manip Y --> run hopper
        manipYBtn = new JoystickButton(manipController, XboxController.Button.kY.value);
        // manipYBtn.whenHeld(new RunHopper());
        manipYBtn.whenHeld(new TurretTracking());


        // Hold Manip A Button --> Run Intake
        manipABtn = new JoystickButton(manipController, XboxController.Button.kA.value);
        manipABtn.whenHeld(new RunIntake(true));
        // manipABtn.whenPressed(new InstantCommand(() ->  Robot.intake.extendIntake()));


        // Hold Manip X Button --> Run Shooter System
        manipXBtn = new JoystickButton(manipController, XboxController.Button.kX.value);
        manipXBtn.whenHeld(new Shoot()); 

        // Press Manip B button --> Extend or retract intake
        manipBBtn = new JoystickButton(manipController, XboxController.Button.kB.value);
        manipBBtn.whenPressed(new ToggleIntakePistons());

        //new InstantCommand(() -> Robot.intake.toggleIntakePistons())
        
        //TODO: test the dpad controls
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
        manipLTrigger= new JoystickButton(manipController, XboxController.Axis.kLeftTrigger.value);
        manipRTrigger = new JoystickButton(manipController, XboxController.Axis.kRightTrigger.value);
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

    public double getDriverLeftTrigger() {
        return driveController.getLeftTriggerAxis();
    }

    public boolean getDriverLeftBumper() {
        return driveController.getLeftBumper();
    }

    public void initManualClimber() {
        // Hold Manip LB --> Extend left climber
        manipLBumper.whileHeld(new WinchClimber(LeftOrRight.left, true));

        // Hold Manip RB --> Extend right climber
        manipRBumper.whileHeld(new WinchClimber(LeftOrRight.right, true));

        // Hold Manip back button --> Retract left climber
        manipBackBtn.whileHeld(new WinchClimber(LeftOrRight.left, false));

        // Hold Manip start button --> Retract right climber
        manipStartBtn.whileHeld(new WinchClimber(LeftOrRight.right, false));

        // Press DPad left --> Toggle left piston
        manipLeftDPadButton = new DPadButton(manipController, Direction.LEFT);
        manipLeftDPadButton.whenPressed(new InstantCommand(() -> Robot.climber.toggleLeftPiston()));

        // Press DPad left --> Toggle left piston
        manipRightDPadButton = new DPadButton(manipController, Direction.RIGHT);
        manipRightDPadButton.whenPressed(new InstantCommand(() -> Robot.climber.toggleRightPiston()));

        // Press Manip LT --> Toggle automatic climber hold
        manipLTrigger.toggleWhenActive(new HoldClimberInPlace(RightOrLeft.left));

        // Press Manip RT --> Toggle automatic climber hold
        manipLTrigger.toggleWhenActive(new HoldClimberInPlace(RightOrLeft.right));



    }

    public void initAutomaticClimber() {
        // Hold Manip LB and RB --> Stage One Climb with 2 second wait
        //TwoButtonTrigger stageOneTrigger = new TwoButtonTrigger(manipLBumper, manipRBumper);
        //stageOneTrigger.whenActive(new StageOneClimb(2));  //check time

        // Hold Manip LT and RT --> Stage Two Climb
        //TwoButtonTrigger stageTwoTrigger = new TwoButtonTrigger(manipLTrigger, manipRTrigger);
        //stageTwoTrigger.whenActive(new StageTwoClimb());

    }
    

}