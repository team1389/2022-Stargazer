package frc.autos;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.commands.FollowPath;
import frc.commands.Shoot;
import frc.robot.Robot;

public class FourBallAuto extends SequentialCommandGroup{
    public FourBallAuto() {
        addRequirements(Robot.drivetrain, Robot.shooter,Robot.intake);
        addCommands(
            new InstantCommand(() -> Robot.intake.setIntakePercent(0.85)),
            new FollowPath("Four Ball Auto"),
            new InstantCommand(() -> Robot.intake.setIntakePercent(0.0)),
            new Shoot(),
            new InstantCommand(() -> Robot.intake.setIntakePercent(0.85)),
            new FollowPath("Four Ball Auto Pt.Two"),
            new InstantCommand(() -> Robot.intake.setIntakePercent(0.0)),
            new Shoot()
        );
}
}
