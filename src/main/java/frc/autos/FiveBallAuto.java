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
import frc.robot.Robot;

public class FiveBallAuto extends SequentialCommandGroup{
    public FiveBallAuto() {
        addRequirements(Robot.drivetrain, Robot.shooter,Robot.intake);
        addCommands( 
            new InstantCommand(() -> Robot.intake.extendIntake()),
            new ParallelCommandGroup(new RunIntake(2.2), new DriveTime(1.483666, 0, 0.3, 0), new TurnTurret(1.5)),
            new DriveTime(0.7, 0, -0.3, 0),
            new Shoot(),
            
            new TurnAngle(5, 100),
            new ParallelCommandGroup(new RunIntake(4.0001), new DriveTime(4.00001, 0, 0.3, 0)),
            new TurnAngle(5, 100),
            new Shoot()
        );
    }
}
