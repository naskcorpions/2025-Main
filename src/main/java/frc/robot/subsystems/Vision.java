package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

// import frc.robot.subsystems.PhotonCamera;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.EstimatedRobotPose;
import org.photonvision.targeting.PhotonTrackedTarget;
import edu.wpi.first.math.geometry.Transform2d;



import java.util.List;

public class Vision {
    public Vision() {
        // Create a PhotonCamera instance with the camera name "2594_cam"
        PhotonCamera picam = new PhotonCamera("picam");

        // Get the NetworkTable instance for sending data to ElasticDashboard
        NetworkTableInstance networkTableInstance = NetworkTableInstance.getDefault();
        NetworkTable table = networkTableInstance.getTable("VisionData");

        // Infinite loop to continuously check for targets and update ElasticDashboard
        while (true) {
            // Get the latest pipeline result
            PhotonPipelineResult result = picam.getLatestResult();

            // Check if there are any targets detected
            if (result.hasTargets()) {
                // If there are targets, retrieve the list of targets
                List<PhotonTrackedTarget> targets = result.getTargets();

                // Get the best target
                PhotonTrackedTarget bestTarget = result.getBestTarget();

                // Extract information from the best target
                double yaw = bestTarget.getYaw();  // Yaw of the target
                double pitch = bestTarget.getPitch();  // Pitch of the target
                double area = bestTarget.getArea();  // Area of the target
                double skew = bestTarget.getSkew();  // Skew of the target
                // Transform2d cameraToTarget = bestTarget.getCameraToTarget();  // Camera-to-target transform

                // Send data to NetworkTables for ElasticDashboard to read
                table.getEntry("BestTargetYaw").setDouble(yaw);
                table.getEntry("BestTargetPitch").setDouble(pitch);
                table.getEntry("BestTargetArea").setDouble(area);
                table.getEntry("BestTargetSkew").setDouble(skew);

                // Send camera-to-target transform data
                // table.getEntry("CameraToTargetX").setDouble(cameraToTarget.getX());
                // table.getEntry("CameraToTargetY").setDouble(cameraToTarget.getY());

                // Optional: You can add additional entries if you need more data
                // For example, if you have more targets, you could send them as well.
                // table.getEntry("SecondTargetYaw").setDouble(secondTargetYaw);
            } else {
                // If no targets detected, send a default value or reset data
                table.getEntry("BestTargetYaw").setDouble(0.0);
                table.getEntry("BestTargetPitch").setDouble(0.0);
                table.getEntry("BestTargetArea").setDouble(0.0);
                table.getEntry("BestTargetSkew").setDouble(0.0);
                table.getEntry("CameraToTargetX").setDouble(0.0);
                table.getEntry("CameraToTargetY").setDouble(0.0);
            }

            // Delay for a short time before checking again (adjust as needed)
            try {
                Thread.sleep(100);  // Sleep for 100 milliseconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
