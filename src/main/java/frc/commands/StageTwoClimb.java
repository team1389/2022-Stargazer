package frc.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Robot;
import frc.subsystems.Climber;

public class StageTwoClimb extends SequentialCommandGroup {

    private Climber climber;

    public StageTwoClimb() {
        climber = Robot.climber;
        addRequirements(climber);
        addCommands(
            new ParallelCommandGroup(new InstantCommand(() -> climber.pistonRetractRight()), 
                                    new InstantCommand(() -> climber.pistonRetractLeft())),
            new WaitCommand(2),
            new InstantCommand(() -> climber.pistonExtendLeft()),
            new WaitCommand(2),
            new ClimberLeftExtend(),
            new WaitCommand(2),
            new ParallelCommandGroup(new ClimberLeftRetract(), 
                                    new SequentialCommandGroup(new WaitCommand(1), 
                                                            new InstantCommand(() -> climber.pistonExtendRight()))),
            new ClimberRightExtend(),
            new ClimberRightRetract()
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
 * 7. Extend left arm, releasing from the lower bar
 **/