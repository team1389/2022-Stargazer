package frc.autos;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.commands.FollowPath;
import frc.commands.ShootWithSensors;
import frc.robot.Robot;

public class FiveBallAutoEnd extends SequentialCommandGroup{
    public FiveBallAutoEnd() {
        addRequirements(Robot.drivetrain, Robot.shooter,Robot.intake);
        addCommands( 
            new ShootWithSensors(),
            new InstantCommand(() -> Robot.intake.setIntakePercent(0.85)),
            new FollowPath("ThreeBallAuto"),
            new InstantCommand(() -> Robot.intake.setIntakePercent(0.0)),
            new ShootWithSensors(),
            new InstantCommand(() -> Robot.intake.setIntakePercent(0.85)),
            new FollowPath("FiveBallAutoEnd"),
            new InstantCommand(() -> Robot.intake.setIntakePercent(0.0)),
            new ShootWithSensors()
        );
    }
}
