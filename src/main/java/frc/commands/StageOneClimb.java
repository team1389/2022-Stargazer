package frc.commands;


import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Robot;

public class StageOneClimb extends SequentialCommandGroup {

    public StageOneClimb(double waitTime) {
        addRequirements(Robot.climber);
        addCommands(new ParallelCommandGroup(new ClimberLeftExtend(), new ClimberRightExtend()), 
                    new WaitCommand(waitTime),
                    new ParallelCommandGroup(new ClimberRightRetract(), new ClimberLeftRetract()));
    }
}
/**
 * 1. Extend both up
 * 2. Wait
 * 3. Retract both down
 */