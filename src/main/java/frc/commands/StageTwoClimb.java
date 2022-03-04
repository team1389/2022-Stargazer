package frc.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.commands.WinchClimber.LeftOrRight;
import frc.robot.Robot;
import frc.subsystems.Climber;

public class StageTwoClimb extends SequentialCommandGroup {

    private Climber climber;

    public StageTwoClimb() {
        climber = Robot.climber;
        addRequirements(climber);
        addCommands(
            new ParallelCommandGroup(
                new InstantCommand(() -> climber.toggleRightPiston()), 
                new InstantCommand(() -> climber.toggleLeftPiston())
            ),

            new WaitCommand(2),
            new InstantCommand(() -> climber.toggleLeftPiston()),
            new WaitCommand(2),
            new WinchClimber(LeftOrRight.left, true),
            new WaitCommand(2),

            new ParallelCommandGroup(
                new WinchClimber(LeftOrRight.left, false), 
                
                new SequentialCommandGroup(
                    new WaitCommand(1), 
                    new InstantCommand(() -> climber.toggleLeftPiston())
                )
            ),

            new WinchClimber(LeftOrRight.right, true),
            new WinchClimber(LeftOrRight.right, false)
        );
    }
}

/**
 * 1. Extend both up
 * 2. Wait 2 seconds
 * 3. Retract both down
 * 4. Retract both pistons
 * 5. Extend left arm
 * 6. Retract left arm, hooking onto upper bar
 * 7. Extend right arm, releasing from the lower bar
 **/