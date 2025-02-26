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
import org.photonvision.targeting.MultiTargetPNPResult;
// INFO: WPILib
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.net.PortForwarder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Timer;


public class VisionSubsystem extends SubsystemBase {
    // Create Camera
    private static PhotonCamera camera;
    // Holds all current camera data
    private static List<PhotonPipelineResult> cameraResult;
    public static PhotonPipelineResult result = new PhotonPipelineResult();
    // "Best" targeted tag
    private static PhotonTrackedTarget bestTarget;

    private static boolean isTagDetected = false;
    // Vision Latentcy
    private static double latency = 0.0;

    // Field Layout
    private static AprilTagFieldLayout fieldLayout = AprilTagFieldLayout.loadField(AprilTagFields.k2025ReefscapeWelded);
    private static PhotonPoseEstimator poseEstimator;
    private static Matrix<N3, N1> curStdDevs;


    public void Vision() {
        camera = new PhotonCamera(VisionConstants.RPI1.kCameraName);

        poseEstimator = 
            new PhotonPoseEstimator(
                fieldLayout, 
                PoseStrategy.MULTI_TAG_PNP_ON_COPROCESSOR, 
                VisionConstants.RPI1.kCameraToRobot);
        poseEstimator.setMultiTagFallbackStrategy(PoseStrategy.LOWEST_AMBIGUITY);
        
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

        robotFieldPose();
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
        
        if (isTagDetected) {
            PhotonPipelineResult tempResult = cameraResult.get(0);
            latency = Timer.getFPGATimestamp() - tempResult.getTimestampSeconds();
            return latency;
        }
        return 0.0;        
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

    /** Returns the position of the robot on the field (only if it detects multiple tags) */
    public static Pose2d robotFieldPose() {
        MultiTargetPNPResult multiTargetResult;
        Pose2d robotPose;
        if (result.getMultiTagResult().isPresent()) {
            // Gets Multi Tag Result, and puts in in multiTargetTesult
            multiTargetResult = result.getMultiTagResult().get();
            robotPose = new Pose2d(
                multiTargetResult.estimatedPose.best.getX(), 
                multiTargetResult.estimatedPose.best.getY(), 
                multiTargetResult.estimatedPose.best.getRotation().toRotation2d()    
            );
            // System.out.println(robotPose);
            

            return robotPose;
        } else {
            return new Pose2d();
        }
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
 
}
