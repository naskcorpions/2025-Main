// INFO: ROBOT IMPORTS
package frc.robot.commands;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
// INFO: WPILIB IMPORTS
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
// INFO: JAVA IMPORTS
import java.util.List;

// INFO: PATHPLANNER IMPORTS
import com.pathplanner.lib.path.Waypoint;

import frc.robot.Constants.PathPlannerConstants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.VisionSubsystem;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.GoalEndState;
import com.pathplanner.lib.path.IdealStartingState;
import com.pathplanner.lib.path.PathConstraints;
import com.pathplanner.lib.path.PathPlannerPath;



public class OTFPath extends Command {
    static List<Waypoint> waypoints;
    static PathPlannerPath path;
    
    static PathConstraints pathConstraints =  new PathConstraints(
        PathPlannerConstants.PathConstraints.maxTranslationSpeed, 
        PathPlannerConstants.PathConstraints.maxTranslationAcc, 
        PathPlannerConstants.PathConstraints.maxRotationSpeed, 
        PathPlannerConstants.PathConstraints.maxRotationAcc
        );
    private static void update() {
        waypoints = PathPlannerPath.waypointsFromPoses (
            DriveSubsystem.getPoseStatic(),
            // VisionSubsystem.estimatedTagPose()
            new Pose2d(3.359, 1.817, new Rotation2d(180))
            // new Pose2d(16, 4.5, new Rotation2d(0)),
            // new Pose2d(17, 4.5, new Rotation2d(0))
        );
    
        path = new PathPlannerPath(
            waypoints, 
            pathConstraints, 
            null, 
            new GoalEndState(0, new Rotation2d(0, 0))
        );
    }
        
    public static Command driveToTagCommand() {
        update();
        path.preventFlipping = true;
        System.out.println("OTF PATH");
        return AutoBuilder.followPath(path);
    }

}
