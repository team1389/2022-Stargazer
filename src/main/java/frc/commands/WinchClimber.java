package frc.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.subsystems.Climber;


public class WinchClimber extends CommandBase {

    private Climber climber;
    //TODO: What type of encoder

    // The number of rotations at which the climber is fully extended and fully retractd, respectively
    //TODO: FIX THESE
    private final double MAX_EXTENDED_VALUE = 1;
    private final double MAX_RETRACTED_VALUE = 0;

    public enum LeftOrRight {
        left,
        right
    }
    private LeftOrRight leftOrRight;

    private boolean isExtending;

    /*
    *@param leftOrRight Should be "left" or "right"
    *@param isExtending false is retracting, true is extending
    */
    public WinchClimber(LeftOrRight leftOrRight, boolean isExtending) {
        climber = Robot.climber;
        this.leftOrRight = leftOrRight;
        this.isExtending = isExtending;

        switch (leftOrRight) {
            case left: 
                //encoder = climber.getLeftEncoder();
                break;
            case right:
                //encoder = climber.getRightEncoder();
                break;
        }

        addRequirements(climber);
    }

    @Override
    public void execute() {

        // Do the correct thing
        switch (leftOrRight) {
            case left: 
                if(isExtending) {
                    climber.leftWinchExtend();
                }
                else {
                    climber.leftWinchRetract();
                }
                break;

            case right:
                if(isExtending) {
                    climber.rightWinchExtend();
                }
                else {
                   climber.rightWinchRetract();
                }
                break;
        }

        SmartDashboard.putNumber("left encoder position", climber.getLeftEncoderPosition());
    }

    @Override
    public boolean isFinished() {
        // if((isExtending && encoder.getAbsolutePosition() >= MAX_EXTENDED_VALUE) ||
        //     (!isExtending && encoder.getAbsolutePosition() <= MAX_RETRACTED_VALUE)) {
                
        //     return true;
        // }


        return false;
    }

    @Override
    public void end(boolean interrupted) {
        switch (leftOrRight) {
            case left:
                climber.stopLeftClimber();
                break;

            case right:
                climber.stopRightClimber();
                break;
        }
        
    }
}
