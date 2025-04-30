package frc.robot.subsystems.elevator; 

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.AlignToReefTagRelative;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;
import frc.robot.Constants.VisionConstants;
import frc.robot.Constants.DrivebaseConstants;
import frc.robot.LimelightHelpers;



public class AutoAlignWrapper extends SequentialCommandGroup {
    public AutoAlignWrapper(SwerveSubsystem drivebase, boolean alignToReefTagRelative) {
        addCommands(
            // Enable heading correction
            new InstantCommand(() -> drivebase.setHeadingCorrection(true)),

            // Perform the auto-align
            new AlignToReefTagRelative(alignToReefTagRelative, drivebase).withTimeout(3),

            // Disable heading correction (if not needed after alignment)
            new InstantCommand(() -> drivebase.setHeadingCorrection(false))
        );
    }
}