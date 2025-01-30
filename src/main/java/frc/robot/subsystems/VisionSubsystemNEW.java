package frc.robot.subsystems;

import java.util.List;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import frc.robot.Constants.VisionConstants;


public class VisionSubsystemNEW {
    private PhotonCamera camera;
    static List<PhotonPipelineResult> cameraResult;

    static PhotonTrackedTarget bestTarget;

    private static boolean isTagDetected;
            
            
    public void Vision() {
        visionInit(camera);
    }


    private void visionInit(PhotonCamera camera) {
        camera = new PhotonCamera(VisionConstants.cameraName);
    }

    
    private void pereodic() {
        cameraResult = camera.getAllUnreadResults();
        if (!cameraResult.isEmpty()) {
            isTagDetected = true;
        } else {
            isTagDetected = false;
        }

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
}
