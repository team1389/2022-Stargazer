package frc.autos;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.commands.FollowPath;
import frc.commands.ShootWithSensors;

public class FourBallAuto extends SequentialCommandGroup{
    public FourBallAuto() {
        addCommands(
            new FollowPath("Four Ball Auto"),
            new ShootWithSensors(),
            new FollowPath("Four Ball Auto Pt.Two"),
            new ShootWithSensors()
        );
}
}
