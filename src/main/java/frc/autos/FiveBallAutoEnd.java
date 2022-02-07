package frc.autos;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.commands.FollowPath;
//import frc.commands.ShootWithSensors;
import frc.robot.Robot;

public class FiveBallAutoEnd extends SequentialCommandGroup{
    public FiveBallAutoEnd() {
        addRequirements(Robot.drivetrain);
        addCommands(
            //new ShootWithSensors(),
            new FollowPath("ThreeBallAuto"),
            //new ShootWithSensors(),
            new FollowPath("FiveBallAutoEnd")
            //new ShootWithSensors()
        );
    }
}
