package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.Elastic;
import java.util.List;
import frc.robot.Constants.DriveConstants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.RobotContainer;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.EstimatedRobotPose;
import org.photonvision.targeting.PhotonTrackedTarget;
import org.photonvision.utils.PacketUtils;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Transform3d;

import org.photonvision.struct.PhotonPipelineMetadataSerde;
import org.photonvision.targeting.PhotonPipelineMetadata;



public class VisionSubsystem extends SubsystemBase {
    PhotonCamera camera;
    PhotonPoseEstimator poseEstimator;
    PhotonTrackedTarget target;
    PhotonPipelineResult result;
    AprilTagFieldLayout aprilTagFieldLayout;
    

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
    
                
    
}