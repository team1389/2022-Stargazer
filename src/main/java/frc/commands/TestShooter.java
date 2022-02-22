package frc.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Robot;

public class TestShooter extends SequentialCommandGroup{
    public TestShooter() {
        addRequirements(Robot.shooter);
    }

    @Override
    public void execute() {
        Robot.shooter.shooterMotor.set(1);
        //Robot.shooter.setFlywheelSpeed(1000);
        SmartDashboard.putNumber("RPM", Robot.shooter.shooterMotor.getEncoder().getVelocity());
        //Robot.shooter.shooterMotor.set(0.8);
    }
}
