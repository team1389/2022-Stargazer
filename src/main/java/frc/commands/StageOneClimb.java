package frc.commands;


import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.commands.WinchClimber.LeftOrRight;
import frc.robot.Robot;

public class StageOneClimb extends SequentialCommandGroup {

    public StageOneClimb(double waitTime) {
        addRequirements(Robot.climber);
        addCommands(new ParallelCommandGroup(new WinchClimber(LeftOrRight.left, true), new WinchClimber(LeftOrRight.right, true)), 
                    new WaitCommand(waitTime),
                    new ParallelCommandGroup(new WinchClimber(LeftOrRight.right, false), new WinchClimber(LeftOrRight.left, false)));
    }
}
/**
 * 1. Extend both up
 * 2. Wait
 * 3. Retract both down
 */