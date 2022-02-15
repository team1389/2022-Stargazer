// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.commands;

import java.util.ArrayList;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.PathPlannerTrajectory.PathPlannerState;

import edu.wpi.first.math.controller.HolonomicDriveController;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.subsystems.Drivetrain;

public class FollowPath extends CommandBase {
    private Drivetrain drivetrain = Robot.drivetrain;
    private PathPlannerTrajectory trajectory;
    private Timer timer = new Timer();
    
    //TODO: Tune these
    private double kP = 1;
    private double kI = 0;
    private double kD = 0.00;

    private PathPlannerState state = new PathPlannerState();
    private Pose2d odometryPose = new Pose2d();
    private Rotation2d desiredAngle;
    private ChassisSpeeds speeds = new ChassisSpeeds();

    private HolonomicDriveController driveController;

    // Measured in m/s and m/s/s
    private final double MAX_VELOCITY = 1;
    private final double MAX_ACCELERATION = 0.5;

    //Input the name of the generated path in PathPlanner
    public FollowPath(String pathName) {
        trajectory = getTrajectory(pathName);

        addRequirements(drivetrain);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {

        //Create necessary profiled PID controller and configure it to be used with the holonomic controller
        ProfiledPIDController rotationController =
        new ProfiledPIDController(
            1.0,
            0.0,
            0.0,
            new TrapezoidProfile.Constraints(1, 1));

        //Create main holonomic drive controller
        driveController = new HolonomicDriveController(
            new PIDController(kP, kI, kD), new PIDController(kP, kI, kD), rotationController);
        driveController.setEnabled(true);

        //  Set robot pose to initial position
        Robot.drivetrain.setPose(trajectory.sample(0).poseMeters);

        //Start timer when path begins 
        timer.reset();
        timer.start();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        //To access the desired angle at the current state, the Trajectory.State must be cast to a PathPlannerState
        state = (PathPlannerState) trajectory.sample(timer.get());
        desiredAngle = state.holonomicRotation;
        odometryPose = drivetrain.getPose();
        speeds = driveController.calculate(odometryPose, (Trajectory.State)state, desiredAngle);

        //Send the desired speeds to the drivetrain
        drivetrain.setChassisSpeeds(speeds);

        SmartDashboard.putNumber("auto x", odometryPose.getX());
        SmartDashboard.putNumber("auto y", odometryPose.getY());
        SmartDashboard.putNumber("auto rotation", odometryPose.getRotation().getDegrees());
    }

    //Read the path with the given name from the PathPlanner
    private PathPlannerTrajectory getTrajectory(String pathName) {
        PathPlannerTrajectory currentTrajectory = PathPlanner.loadPath(pathName, MAX_VELOCITY, MAX_ACCELERATION);

        return currentTrajectory;
    }

    // Calculate trajectory manually with a clamped cubic spline
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
        return timer.hasElapsed(trajectory.getTotalTimeSeconds()+6);
    }
}
