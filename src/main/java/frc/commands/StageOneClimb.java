package frc.commands;


import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Robot;

public class StageOneClimb extends SequentialCommandGroup {

    public StageOneClimb() {
        addRequirements(Robot.climber);
        addCommands(new ClimberLeftExtend(), 
                    new ClimberRightExtend(), 
                    new WaitCommand(2),
                    new ClimberRightRetract(),
                    new ClimberLeftRetract());
    }
}
/**
 * 1. Extend both up
 * 2. Wait 2 seconds
 * 3. Retract both down
 */