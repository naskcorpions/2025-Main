package frc.robot.subsystems;
/*
 *  Commented imports are unused
 */

import frc.robot.Constants;
// import frc.robot.Constants;
// import frc.robot.Elastic;
// import java.util.List;
// import frc.robot.RobotContainer;
// import frc.robot.subsystems.DriveSubsystem;
import frc.robot.Constants.DriveConstants;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.units.Unit;
// import edu.wpi.first.wpilibj.PS4Controller.Button;
// import edu.wpi.first.wpilibj.XboxController;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import org.photonvision.PhotonCamera;
// import org.photonvision.PhotonUtils;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonUtils;
// import org.photonvision.PhotonPoseEstimator.PoseStrategy;
import org.photonvision.targeting.PhotonPipelineResult;
// import org.photonvision.EstimatedRobotPose;
import org.photonvision.targeting.PhotonTrackedTarget;
// import org.photonvision.utils.PacketUtils;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.math.Pair;
// import edu.wpi.first.math.geometry.Pose3d;
// import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.util.Units;

// import org.photonvision.struct.PhotonPipelineMetadataSerde;
// import org.photonvision.targeting.PhotonPipelineMetadata;



public class VisionSubsystem extends SubsystemBase {
    static PhotonCamera camera;
    PhotonPoseEstimator poseEstimator;
    PhotonTrackedTarget target;
    PhotonPipelineResult result;
    AprilTagFieldLayout aprilTagFieldLayout;

    private static double turn = 0.0;
    private static double forward = 0.0;

    

    public void visionInit() {
        camera = new PhotonCamera("picam");

    }

    public void visionPereodic() {
        // Get the NetworkTable instance for sending data to ElasticDashboard
        NetworkTableInstance networkTableInstance = NetworkTableInstance.getDefault();
        NetworkTable table = networkTableInstance.getTable("VisionData");

        // Get data from camera
        result = camera.getLatestResult();
        
        if (result.hasTargets()) {
            PhotonTrackedTarget target = result.getBestTarget();
            Transform3d pose = target.getBestCameraToTarget();
            
        }

       


        // if (aprilTagFieldLayout.getTagPose(target.getFiducialId()).isPresent()) {
        //     Pose3d robotPose = PhotonUtils.estimateFieldToRobotAprilTag(target.getBestCameraToTarget(), aprilTagFieldLayout.getTagPose(target.getFiducialId()).get(), cameraToRobot);
        // }
    }


    public void tagPoint(int targetNumber) {
        boolean targetVisible = false;
        double targetYaw = 0.0;
        double turn;
        var results = camera.getAllUnreadResults();
        if (!results.isEmpty()) {
            // Camera processed a new frame since last
            // Get the last one in the list.
            var result = results.get(results.size() - 1);
            if (result.hasTargets()) {
                // At least one AprilTag was seen by the camera
                for (var target : result.getTargets()) {
                    if (target.getFiducialId() == targetNumber) {
                        // Found Tag 7, record its information
                        targetYaw = target.getYaw();
                        targetVisible = true;
                    }                }
            }
        }
        
        if (targetVisible) {
            turn = -1.0 * targetYaw * 0.004 /* <- May need tuning? */ * DriveConstants.kMaxAngularSpeed;
        }
        //TODO: Use turn variable as the angle when running the swerve drive
        // Use the controller for the other two inputs
    }
    
    public static void tagAllign() {
        boolean targetVisible = false;
        double targetYaw = 0.0;
        double targetRange = 0.0;

        var results = camera.getAllUnreadResults();
        if (!results.isEmpty()) {
            // Camera processed a new frame since last
            // Get the last one in the list.
            var result = results.get(results.size() - 1);
            if (result.hasTargets()) {
                // At least one AprilTag was seen by the camera
                for (var target : result.getTargets()) {
                    if (target.getFiducialId() == 7) {
                        // Found Tag 7, record its information
                        targetYaw = target.getYaw();
                        targetRange =
                                PhotonUtils.calculateDistanceToTargetMeters(
                                        0.5, // Measured with a tape measure, or in CAD.
                                        1.435, // From 2024 game manual for ID 7
                                        Units.degreesToRadians(-30.0), // Measured with a protractor, or in CAD.
                                        Units.degreesToRadians(target.getPitch()));
                    
                        targetVisible = true;
                    }
                }
            }
        }
        // Auto-align when requested
        if (targetVisible) {
            // Driver wants auto-alignment to tag 7
            // And, tag 7 is in sight, so we can turn toward it.
            // Override the driver's turn and fwd/rev command with an automatic one
            // That turns toward the tag, and gets the range right.
            turn =
                    (0.0 /* ANGLE See commment below */ - targetYaw) * 0.004 /* <- P value. Will need tuning */ * DriveConstants.kMaxAngularSpeed;
            forward =
                    (1.0 /* DISTANCE See commment below */ - targetRange) * 0.004 /* <- P value. Will need tuning */ * DriveConstants.kMaxSpeedMetersPerSecond;
            // TODO: TuneNumbers
            /*
             *  Desired angle at which to view the tag 
             *  0.0 = straight
             *  Anything else = not straight
             *  Link: https://docs.photonvision.org/en/latest/docs/examples/aimandrange.html
             */
                
            
        }
    }
    public static double allignGetForward() {
        return forward;
    }
    public static double allignGetTurn() {
        return turn;
    }
 
}