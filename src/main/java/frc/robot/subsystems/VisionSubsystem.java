package frc.robot.subsystems;

import java.lang.StackWalker.Option;
import java.util.List;
import java.util.Optional;

import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonUtils;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;
import org.photonvision.proto.Photon;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.VisionConstants;


public class VisionSubsystem extends SubsystemBase {
    // Create Camera
    private PhotonCamera camera;
    // Holds all current camera data
    private static List<PhotonPipelineResult> cameraResult;
    // "Best" targeted tag
    private static PhotonTrackedTarget bestTarget;

    private static boolean isTagDetected;
    // Vision Latentcy
    private static double latency = 0.0;

    // Field Layout
    private static AprilTagFieldLayout fieldLayout = AprilTagFieldLayout.loadField(AprilTagFields.k2025Reefscape);
    private static PhotonPoseEstimator poseEstimator = new PhotonPoseEstimator(
        fieldLayout, 
        PoseStrategy.MULTI_TAG_PNP_ON_COPROCESSOR, 
        // Measured in Meters? From the center of the robot.
        new Transform3d(0.381, 0.0, 0.0, new Rotation3d())
    );


    public void Vision() {
        // visionInit(camera);
        camera = new PhotonCamera(VisionConstants.cameraName);

    }


    // private void visionInit(PhotonCamera camera) {
    //     camera = new PhotonCamera(VisionConstants.cameraName);
    // }

    @Override
    public void periodic() {
        cameraResult = camera.getAllUnreadResults();
        if (!cameraResult.isEmpty()) {
            isTagDetected = true;
        } else {
            isTagDetected = false;
        }
    }
    /**
     * 
     * @return the latency of the pipeline result
     */
    public static double returnVisionLatentcy() {
        // IMPORTANT:
        //REVIEW:
        /* Should get the latency. Lines do the following
         * 1. Gets the first result from the pipeline result
         * 2. Calculates latency by getting the time of the robot and the time the result was found/created
         * 3. Returns the latency in a double.
         * 
         * NOTE: Due to the timestamp stuff found in PhotonVision's docs, doing 'Timer.getFPGATimestamp() - ' may not be nessary
         * NOTE:    It may not be nessary depending on what 'tempResult.getTimestampSeconds()' deos exactly.
         * https://docs.photonvision.org/en/latest/docs/contributing/design-descriptions/time-sync.html
         */
        PhotonPipelineResult tempResult = cameraResult.get(0);
        latency = Timer.getFPGATimestamp() - tempResult.getTimestampSeconds();
        return latency;
    }

    /**
     * Returns the latest camera result
     * @return List<PhotonPipelineResult>
     */
    public static List<PhotonPipelineResult> returnCameraResults() {
        return cameraResult;
    }

    /**
     * <p>
     * Calculates the best target based on the latest result from the pereodic method in this subsystem.
     * Returns the best target
     * <p>
     * @return PhotonTrackedTarget
     */
    public static PhotonTrackedTarget returnBestTarget() {
        var result = cameraResult.get(cameraResult.size() - 1);
        bestTarget = result.getBestTarget();
        return bestTarget;
    }

    /**
     * Returns true if a tag is detected, and false if one isn't
     * @return boolean
     */
    public static boolean isTagDetedted() {
        return isTagDetected;
    }

    public static Optional<Pose2d> estimatedTagPose() {
        // var visionPoseEstimate = poseEstimator.update();

        return Optional.empty(); 
    }
}
