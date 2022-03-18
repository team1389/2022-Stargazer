package frc.autos;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.commands.DriveTime;
import frc.commands.FollowPath;
import frc.commands.RunIntake;
import frc.commands.Shoot;
import frc.commands.TurnAngle;
import frc.commands.TurnTurret;
import frc.commands.TurretTracking;
import frc.robot.Robot;

public class ThreeBallAuto extends SequentialCommandGroup{
    public ThreeBallAuto() {
        addRequirements();
        addCommands(
            new ParallelCommandGroup(new TurretTracking(), 
            new SequentialCommandGroup(
                new InstantCommand(() -> Robot.intake.extendIntake()),
            
                new ParallelCommandGroup(new RunIntake(2.2), new DriveTime(1.483666, 0, 0.3, 0), new TurnTurret(1.5)),
                new DriveTime(0.7, 0, -0.3, 0),
                new Shoot(),
            
                new TurnAngle(3, -120.001),
                new ParallelCommandGroup(new RunIntake(3.888), new DriveTime(3.888, 0, 0.3, 0), new TurnTurret(1)),
                new TurnAngle(3, 50),
                new Shoot()
            )
            )

        );
    }
}
