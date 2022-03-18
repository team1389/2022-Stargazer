package frc.autos;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.commands.FollowPath;
import frc.commands.RunIntake;
import frc.commands.Shoot;
import frc.commands.ShootWithSensors;
import frc.commands.TurretTracking;
import frc.robot.Robot;

public class TwoBallAuto extends SequentialCommandGroup{



    public TwoBallAuto() {
        addRequirements(Robot.drivetrain, Robot.shooter, Robot.intake);

        addCommands(
            new ParallelCommandGroup(new TurretTracking(), 
            new SequentialCommandGroup(
                new InstantCommand(() -> Robot.intake.extendIntake()),
                new InstantCommand(() -> Robot.climber.extendLeftPiston()),
                new InstantCommand(() -> Robot.climber.extendRightPiston()),
                new ParallelCommandGroup(new RunIntake(5.2), new FollowPath("TwoBallAuto")),
                new ShootWithSensors()
            )
            )
            
        );
    }
}
