package frc.autos;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.commands.FollowPath;
import frc.commands.ShootWithSensors;
import frc.robot.Robot;

public class ThreeBallAuto extends SequentialCommandGroup{
    public ThreeBallAuto() {
        addRequirements(Robot.drivetrain, Robot.shooter);
        addCommands(
            new ShootWithSensors(),
            new FollowPath("ThreeBallAuto"),
            new ShootWithSensors()
        );
    }
}
