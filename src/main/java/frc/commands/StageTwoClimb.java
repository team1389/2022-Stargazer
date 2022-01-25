package frc.commands;


import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Robot;

public class StageTwoClimb extends SequentialCommandGroup {

    public StageTwoClimb() {
        addRequirements(Robot.climber);
        addCommands(new ParallelCommandGroup(new ClimberLeftExtend(), new ClimberRightExtend()),
                    new WaitCommand(2),
                    new ParallelCommandGroup(new ClimberLeftRetract(), new ClimberRightRetract())

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