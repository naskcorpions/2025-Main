package frc.robot.subsystems;

import java.util.List;

import org.photonvision.PhotonCamera;
import org.photonvision.proto.Photon;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.VisionConstants;

import edu.wpi.first.math.geometry.Pose2d;


public class VisionSubsystem extends SubsystemBase {
    private PhotonCamera camera;
    private PhotonCamera camera2;
    static List<PhotonPipelineResult> cameraResult;
    static PhotonTrackedTarget bestResult;

    static PhotonTrackedTarget bestTarget;

    private static boolean isTagDetected;

            
    public void Vision() {
        // visionInit(camera);
        camera = new PhotonCamera(VisionConstants.pi1CameraName);
        camera2 = new PhotonCamera(VisionConstants.pi2CameraName);

    }


    private void visionInit(PhotonCamera camera) {
        camera = new PhotonCamera(VisionConstants.pi1CameraName);

    }

    @Override
    public void periodic() {
        cameraResult = camera.getAllUnreadResults();
        if (!cameraResult.isEmpty()) {
            isTagDetected = true;
        } else {
            isTagDetected = false;
        }

        bestResult = this.returnBestTarget();
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
    
    public static Pose2d getTargetPosition() {
        
        return new Pose2d();
    }
}
