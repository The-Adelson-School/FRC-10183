package frc.robot.autonomous;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands; // Ensure this import is present
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand; // Ensure this import is present
import edu.wpi.first.wpilibj2.command.WaitCommand; // Ensure this import is present
import frc.robot.Constants.ElevatorConstants;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;
import frc.robot.subsystems.AlignToReefTagRelative;

public class AutoStuff {

    public static Command getAutonomousCommand(SwerveSubsystem drivebase, ElevatorSubsystem elevator) {
        return new SequentialCommandGroup(
            new AlignToReefTagRelative(true, drivebase).withTimeout(5), // Align using AprilTag
            new WaitCommand(2), // Wait for 2 seconds
            drivebase.driveToDistanceCommand(1.0, -0.5) // Move left for 1 meter at 0.5 m/s
        );
    }
}
