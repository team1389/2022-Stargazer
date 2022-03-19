// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.autos;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.commands.DriveTime;
import frc.commands.RunIntake;
import frc.commands.Shoot;
import frc.commands.ShootWithSensors;
import frc.commands.TurnTurret;
import frc.robot.Robot;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class TimedAuto extends SequentialCommandGroup {
    /** Creates a new TimedAuto. */
    public TimedAuto() {
        // Add your commands in the addCommands() call, e.g.
        // addCommands(new FooCommand(), new BarCommand());
        addCommands(
                new InstantCommand(() -> Robot.intake.extendIntake()),
                // new InstantCommand(() -> Robot.climber.extendLeftPiston()),
                // new InstantCommand(() -> Robot.climber.extendRightPiston()),
                new ParallelCommandGroup(
                        new RunIntake(2.2),
                        // TODO: fix this: new DriveTime(1.483666, 0, 0.3, 0),
                        new TurnTurret(1.5)),
                // TODO: fix this: new DriveTime(0.7, 0, -0.3, 0),
                new Shoot());
    }
}
