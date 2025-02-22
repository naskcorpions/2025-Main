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
import frc.robot.subsystems.VisionSubsystem;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.GoalEndState;
import com.pathplanner.lib.path.IdealStartingState;
import com.pathplanner.lib.path.PathConstraints;
import com.pathplanner.lib.path.PathPlannerPath;



public class OTFPath extends Command{
    
    static PathConstraints pathConstraints =  new PathConstraints(
        PathPlannerConstants.PathConstraints.maxTranslationSpeed, 
        PathPlannerConstants.PathConstraints.maxTranslationAcc, 
        PathPlannerConstants.PathConstraints.maxRotationSpeed, 
        PathPlannerConstants.PathConstraints.maxRotationAcc
        );
        
        
    public static Command driveToTagCommand() {
            
        List<Waypoint> waypoints = PathPlannerPath.waypointsFromPoses (
            VisionSubsystem.robotFieldPose(),
            VisionSubsystem.estimatedTagPose()
        );
        
        PathPlannerPath path = new PathPlannerPath(
            waypoints, 
            pathConstraints, 
            new IdealStartingState(0, new Rotation2d(0, 0)), 
            new GoalEndState(0, new Rotation2d(0, 0))
        );

        path.preventFlipping = true;
        System.out.println("OTF PATH");
        return AutoBuilder.followPath(path);
    }

}
