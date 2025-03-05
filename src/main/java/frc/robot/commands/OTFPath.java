// INFO: ROBOT IMPORTS
package frc.robot.commands;
import frc.robot.Constants.PathPlannerConstants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.VisionSubsystem;
import frc.robot.subsystems.Dashboard;
// INFO: WPILIB IMPORTS
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.DeferredCommand;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.Trajectory.State;
import edu.wpi.first.wpilibj.counter.Tachometer;

// INFO: JAVA IMPORTS
import java.util.List;
import java.util.Set;
// INFO: PATHPLANNER IMPORTS
import com.pathplanner.lib.path.Waypoint;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.GoalEndState;
import com.pathplanner.lib.path.IdealStartingState;
import com.pathplanner.lib.path.PathConstraints;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.path.PathPoint;



public class OTFPath extends Command {
    static List<Waypoint> waypoints;
    static PathPlannerPath path;
    
    static PathConstraints pathConstraints =  new PathConstraints(
        PathPlannerConstants.PathConstraints.maxTranslationSpeed, 
        PathPlannerConstants.PathConstraints.maxTranslationAcc, 
        PathPlannerConstants.PathConstraints.maxRotationSpeed, 
        PathPlannerConstants.PathConstraints.maxRotationAcc
        );

        
    public static Command driveToTagCommand() {
    
        waypoints = PathPlannerPath.waypointsFromPoses (
            DriveSubsystem.getPoseStatic(),
            // new Pose2d(3.359, 1.817, new Rotation2d(180))
            // Dashboard.returnWantedTagPose(),
            new Pose2d(
                Dashboard.returnWantedTagPose().getX(),
                Dashboard.returnWantedTagPose().getY(),
                Dashboard.returnWantedTagPose().getRotation()
            ).plus(applyOffsets(Dashboard.returnWantedTagID()))
        );
          
        path = new PathPlannerPath(
            waypoints, 
            pathConstraints, 
            null, 
            new GoalEndState(0, Dashboard.returnWantedTagPose().getRotation().plus(Rotation2d.k180deg)
            )
        );
                
        path.preventFlipping = true;
        
        return AutoBuilder.followPath(path);
    }
    private static Transform2d applyOffsets(int tagNumber) {
        
        return PathPlannerConstants.Offsets.rightWallOffset;
    }

}
