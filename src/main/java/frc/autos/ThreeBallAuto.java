package frc.autos;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.commands.DriveTime;
import frc.commands.RunIndexer;
import frc.commands.RunIntake;
import frc.commands.Shoot;
import frc.commands.TurnBy;
import frc.commands.TurnTurret;
import frc.commands.TurretTracking;
import frc.robot.Robot;
import frc.util.Angle;

public class ThreeBallAuto extends SequentialCommandGroup {
    public ThreeBallAuto() {
        addRequirements();
        addCommands(
                new InstantCommand(() -> Robot.intake.extendIntake()),

                new ParallelCommandGroup(
                        new RunIntake(2.5),
                        // TODO: fix this: new DriveTime(1.4, 0, 0.3, 0),
                        new TurnTurret(1.5)),
                new ParallelCommandGroup(new TurretTracking().withTimeout(1)
                // TODO: fix this:, new DriveTime(0.7, 0, -0.3, 0)
                ),
                new Shoot(),
                new RunIndexer(false).withTimeout(0.25),

                new TurnBy(3, Angle.fromDegrees(-110)),
                new ParallelCommandGroup(
                        new RunIntake(4),
                        // TODO: fix this: new DriveTime(2, 0, 0.6, 0),
                        new TurnTurret(1)),
                new ParallelCommandGroup(new TurretTracking().withTimeout(1), new TurnBy(2, Angle.fromDegrees(50))),
                new Shoot()

        );
    }
}