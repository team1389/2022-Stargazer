package frc.commands;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.sensors.CANCoder;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.subsystems.Climber;


public class WinchClimber extends CommandBase {

    private Climber climber;
    //TODO: What type of encoder
    private CANCoder encoder;

    // The number of rotations at which the climber is fully extended and fully retractd, respectively
    //TODO: FIX THESE
    private final double MAX_EXTENDED_VALUE = 1;
    private final double MAX_RETRACTED_VALUE = 0;

    private String leftOrRight;
    private boolean isExtending;

    /*
    *@param leftOrRight Should be "left" or "right"
    *@param isExtending false is retracting, true is extending
    */
    public WinchClimber(String leftOrRight, boolean isExtending) {
        climber = Robot.climber;

        if(leftOrRight.equals("left")) {
            //encoder = climber.getLeftEncoder();
        }
        else if(leftOrRight.equals("right")) {
            //encoder = climber.getRightEncoder();
        }

        this.leftOrRight = leftOrRight;
        this.isExtending = isExtending;

        addRequirements(climber);
    }

    @Override
    public void execute() {

        // Do the correct thing
        if(leftOrRight.equals("left")) {
            if(isExtending) {
                climber.winchLeftUp();
            }
            else {
                climber.winchLeftDown();
            }
        }
        else if(leftOrRight.equals("right")) {
            if(isExtending) {
                climber.winchRightUp();
            }
            else {
                climber.winchRightDown();
            }
        }
    }

    @Override
    public boolean isFinished() {
        if((isExtending && encoder.getAbsolutePosition() >= MAX_EXTENDED_VALUE) ||
            (!isExtending && encoder.getAbsolutePosition() <= MAX_RETRACTED_VALUE)) {
                
            return true;
        }


        return false;
    }

    @Override
    public void end(boolean interrupted) {
        if(leftOrRight.equals("left")) {
            climber.stopLeftClimber();
        }
        else if(leftOrRight.equals("right")) {
            climber.stopRightClimber();
        }
    }
}
