// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.commands;

import java.util.ArrayList;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.subsystems.Drivetrain;

public class FollowPath extends CommandBase {
    private Drivetrain drivetrain = Robot.drivetrain;
    private Trajectory trajectory;

    // Measured in m/s and m/s/s
    private final double MAX_VELOCITY = 5;
    private final double MAX_ACCELERATION = 4;

    // The array of positions in the form (x, y) marking the desired path. x
    // positive is left. Angles in degrees CCW. Distance in meters.
    public FollowPath(double[][] points, double startAngle, double endAngle, double startVelocity, double endVelocity) {

        trajectory = calculateTrajectory(points, startAngle, endAngle, startVelocity, endVelocity);

        addRequirements(drivetrain);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    // TODO: Create a HolonomicDriveController to follow the trajectory
    @Override
    public void execute() {
    }

    // Calculate trajectory with a clamped cubic spline
    private Trajectory calculateTrajectory(double[][] points, double startAngle, double endAngle, double startVelocity,
            double endVelocity) {
        Pose2d startPose = new Pose2d(points[0][0], points[0][1], Rotation2d.fromDegrees(startAngle));
        Pose2d endPose = new Pose2d(points[points.length - 1][0], points[points.length - 1][1],
                Rotation2d.fromDegrees(endAngle));

        ArrayList<Translation2d> path = new ArrayList<>();

        // If the desired set of points contains at least one waypoint, add them to the
        // path object
        if (points.length > 2) {
            for (int i = 1; i < path.size() - 1; i++) {
                Translation2d point = new Translation2d(points[i][0], points[i][1]);
                path.add(point);
            }
        }

        TrajectoryConfig trajectoryConfig = new TrajectoryConfig(MAX_VELOCITY, MAX_ACCELERATION);
        trajectoryConfig.setStartVelocity(startVelocity);
        trajectoryConfig.setEndVelocity(endVelocity);

        return TrajectoryGenerator.generateTrajectory(startPose, path, endPose, trajectoryConfig);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        drivetrain.drive(0.0, 0.0, 0.0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
