package frc.autos;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.commands.FollowPath;
//import frc.commands.ShootWithSensors;
import frc.robot.Robot;

public class OneBallAuto extends SequentialCommandGroup{
    public OneBallAuto() {
        addRequirements(Robot.drivetrain);
        addCommands(
            //new ShootWithSensors(),
            new FollowPath("OneBallAuto")
        );
    }
}