// INFO: ROBOT
package frc.robot.subsystems;
import frc.robot.Constants.VisionConstants;
// INFO: JAVA
import java.util.List;
import java.util.Optional;
import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
// INFO: PHOTONVISION
import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;
// INFO: WPILib
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.net.PortForwarder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Timer;


public class VisionSubsystem extends SubsystemBase {
    // Create Camera
    private static PhotonCamera camera;
    // Holds all current camera data
    private static List<PhotonPipelineResult> cameraResult;
    private static PhotonPipelineResult result;
    // "Best" targeted tag
    private static PhotonTrackedTarget bestTarget;

    private static boolean isTagDetected;
    // Vision Latentcy
    private static double latency = 0.0;

    // Field Layout
    private static AprilTagFieldLayout fieldLayout = AprilTagFieldLayout.loadField(AprilTagFields.k2025Reefscape);
    private static PhotonPoseEstimator poseEstimator;
    private static Matrix<N3, N1> curStdDevs;


    public void Vision() {
        camera = new PhotonCamera(VisionConstants.RPI1.kCameraName);
        // NOTE: Forwards the camera's ports, so that the cameras can be accessed through the ROBORIO's USB port
        // REVIEW:
        PortForwarder.add(5800, VisionConstants.RPI1.kRPIIP, 5800);
        PortForwarder.add(5800, VisionConstants.RPI2.kRPIIP, 5800);

        poseEstimator = 
            new PhotonPoseEstimator(
                fieldLayout, 
                PoseStrategy.MULTI_TAG_PNP_ON_COPROCESSOR, 
                VisionConstants.RPI1.kCameraToRobot);
        poseEstimator.setMultiTagFallbackStrategy(PoseStrategy.LOWEST_AMBIGUITY);
        

        // visionInit(camera);
        camera = new PhotonCamera(VisionConstants.RPI1.kCameraName);

    }


    // private void visionInit(PhotonCamera camera) {
    //     camera = new PhotonCamera(VisionConstants.cameraName);
    // }

    @Override
    public void periodic() {
        cameraResult = camera.getAllUnreadResults();
        if (!cameraResult.isEmpty()) {
            isTagDetected = true;
            result = cameraResult.get(cameraResult.size() - 1);
            bestTarget = result.getBestTarget();
        } else {
            isTagDetected = false;
        }
        System.out.println(fieldRobotPose());
        
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
        return bestTarget;
    }

    /**
     * Returns true if a tag is detected, and false if one isn't
     * @return boolean
     */
    public static boolean isTagDetedted() {
        return isTagDetected;
    }

    // RETURNS TAG POSE
    public static Pose2d estimatedTagPose() {
        Pose2d tagPose2d;
        if (bestTarget != null) {
            Optional<Pose3d> fieldTagPose = fieldLayout.getTagPose(bestTarget.getFiducialId());
            tagPose2d = fieldTagPose.get().toPose2d();
            System.out.println(tagPose2d);
            return tagPose2d;
        } else {
            // Return if np tag detected
            return new Pose2d();
        }
    }

    // RETURNS ROBOT TO TAG POSE. Used to get robot pose?
    public static Pose2d fieldRobotPose() {
        if (!cameraResult.isEmpty()) {
            Optional<EstimatedRobotPose> visionEst = Optional.empty();
            // Temporary pose to help convert from EstimatedRobotPose to Pose2d
            Pose3d tempPose = new Pose3d();
            // Pose2d to be converted to
            Pose2d estimatedPose2d = new Pose2d();
            // Loop through camera results
            for (var change : camera.getAllUnreadResults()) {
                
                visionEst = poseEstimator.update(change);
                updateEstimationStdDevs(visionEst, change.getTargets());
                
            }
            // Should set tempPose to the pose3d from EstimatedRobotPose
            tempPose = poseEstimator.getReferencePose();
            System.out.println(tempPose);
            // Pose 2d to be returned
            estimatedPose2d = new Pose2d(tempPose.getX(), tempPose.getY(), tempPose.getRotation().toRotation2d());
            
            return estimatedPose2d;

        } else {
            // Should handle when a tag is not detected
        }
        
        return new Pose2d();
    }

        /**
     * Calculates new standard deviations This algorithm is a heuristic that creates dynamic standard
     * deviations based on number of tags, estimation strategy, and distance from the tags.
     *
     * @param estimatedPose The estimated pose to guess standard deviations for.
     * @param targets All targets in this camera frame
     */

    public static final Matrix<N3, N1> kSingleTagStdDevs = VecBuilder.fill(4, 4, 8);
    public static final Matrix<N3, N1> kMultiTagStdDevs = VecBuilder.fill(0.5, 0.5, 1);

    private static void updateEstimationStdDevs(
            Optional<EstimatedRobotPose> estimatedPose, List<PhotonTrackedTarget> targets) {
        if (estimatedPose.isEmpty()) {
            // No pose input. Default to single-tag std devs
            curStdDevs = kSingleTagStdDevs;

        } else {
            // Pose present. Start running Heuristic
            var estStdDevs = kSingleTagStdDevs;
            int numTags = 0;
            double avgDist = 0;

            // Precalculation - see how many tags we found, and calculate an average-distance metric
            for (var tgt : targets) {
                var tagPose = poseEstimator.getFieldTags().getTagPose(tgt.getFiducialId());
                if (tagPose.isEmpty()) continue;
                numTags++;
                avgDist +=
                        tagPose
                                .get()
                                .toPose2d()
                                .getTranslation()
                                .getDistance(estimatedPose.get().estimatedPose.toPose2d().getTranslation());
            }

            if (numTags == 0) {
                // No tags visible. Default to single-tag std devs
                curStdDevs = kSingleTagStdDevs;
            } else {
                // One or more tags visible, run the full heuristic.
                avgDist /= numTags;
                // Decrease std devs if multiple targets are visible
                if (numTags > 1) estStdDevs = kMultiTagStdDevs;
                // Increase std devs based on (average) distance
                if (numTags == 1 && avgDist > 4)
                    estStdDevs = VecBuilder.fill(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
                else estStdDevs = estStdDevs.times(1 + (avgDist * avgDist / 30));
                curStdDevs = estStdDevs;
            }
        }
    }
    public static Pose2d getRobotEstimatedPose() {
        return new Pose2d();
    }
 
}
