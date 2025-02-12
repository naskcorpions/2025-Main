// package frc.robot.commands;

// import java.util.List;

// import com.pathplanner.lib.auto.AutoBuilder;
// import com.pathplanner.lib.path.GoalEndState;
// import com.pathplanner.lib.path.PathConstraints;
// import com.pathplanner.lib.path.PathPlannerPath;
// import com.pathplanner.lib.path.Waypoint;

// import edu.wpi.first.wpilibj2.command.Commands;
// import frc.robot.subsystems.DriveSubsystem;
// import frc.robot.subsystems.VisionSubsystem;
// import edu.wpi.first.math.geometry.Pose2d;
// import edu.wpi.first.math.geometry.Transform2d;
// import edu.wpi.first.math.geometry.Transform3d;
// import edu.wpi.first.wpilibj2.command.Command;

// public class DriveToTag extends Command {
//     private DriveSubsystem m_driveSubsystem;
//     private VisionSubsystem m_vision;

//     Pose2d robotPose;
//     Pose2d targetPose;

//     public DriveToTag(VisionSubsystem m_vision, DriveSubsystem m_driveSubsystem) {
//         this.m_vision = m_vision;
//         this.m_driveSubsystem = m_driveSubsystem;
//         addRequirements(m_vision, m_driveSubsystem);
//     }

//     // TODO: FINISH THIS. NOT WORKING YET
//     @Override
//     public void initialize() {
//         robotPose = DriveSubsystem.getPoseStatic();
//         targetPose = VisionSubsystem.getFieldTargetPosition();
//         Transform3d transform = new Transform3d();
//         List<Waypoint> waypoinsts = PathPlannerPath.waypointsFromPoses(
//             // robotPose,
//             targetPose
//         );
//         PathConstraints constraints = new PathConstraints(1.00, 0.5, 152, 150);
        
//         PathPlannerPath path = new PathPlannerPath(
//             waypoinsts, 
//             constraints,
//             null, 
//             new GoalEndState(0, null));
//         path.preventFlipping = true;
//         AutoBuilder.followPath(path);
//     }

//     @Override
//     public void execute() {
//         System.out.println(targetPose);
//     }






// }
