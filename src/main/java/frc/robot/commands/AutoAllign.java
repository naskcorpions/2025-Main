// INFO: ROBOT IMPORTS
package frc.robot.commands;
import frc.robot.Constants.SwerveConstants.DriveConstants;
import frc.robot.Constants.VisionConstants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.DriveSubsystemOLD;
import frc.robot.subsystems.VisionSubsystem;
// INFO: WPILIB IMPORTS
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
// INFO: JAVA IMPORTS
import java.util.List;
// INFO: PHOTONVISION IMPORTS
import org.photonvision.PhotonUtils;
import org.photonvision.proto.Photon;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;


public class AutoAllign extends Command{
    private VisionSubsystem m_vision;
    private DriveSubsystem m_robotDrive;

    // List<PhotonPipelineResult> cameraResult;
    PhotonPipelineResult targetedTarget;

    private double turn;
    private double forward;
    private double targetRange;
    private double targetYaw;

    private int targetedTag = 15;
    private double desiredDistance = 1.2;
    // TODO: P values will need tuning
    // TODO: ADD TO VISION CONSTANTS
    private final double turnP = 0.05;
    private final double driveP = 0.6;

    private PhotonTrackedTarget bestTag;
    private int bestTagID;

        
    public AutoAllign(VisionSubsystem m_vision, DriveSubsystem m_robotDrive2) {
        this.m_vision = m_vision;
        this.m_robotDrive = m_robotDrive2;
        addRequirements(m_robotDrive2, m_vision);

    }
    
    @Override
    public void initialize() {
        /* RUNS ONCE ON INIT */
        // Put any initialization here
    }

    @Override
    public void execute() {
        /* RUNS PEREODICALLY */
        // double targetYaw = 0;
        // targetRange = 0;


        if (VisionSubsystem.isTagDetedted()) {
            bestTag = VisionSubsystem.returnBestTarget();
            if (bestTag != null) {
                    bestTagID = bestTag.getFiducialId();
                    bestTag.getArea();

                if (bestTagID == targetedTag) {
                    targetYaw = bestTag.getYaw();
                            targetRange =
                                    PhotonUtils.calculateDistanceToTargetMeters(
                                            VisionConstants.RPI1.kCameraHeight, // Measured with a tape measure, or in CAD.
                                            VisionConstants.RPI1.kTargetHeight, // From 2024 game manual for ID 7
                                            VisionConstants.RPI1.kCameraPitch, // Measured with a protractor, or in CAD.
                                            Units.degreesToRadians(bestTag.getPitch()));
                    System.out.println(targetRange);
                }

                // If tag is detected, update turn & forward variables
                // if (VisionSubsystemNEW.isTagDetedted()) {
                    turn =
                        (0.0 /* ANGLE See commment below */ - targetYaw) * turnP * DriveConstants.kMaxAngularSpeed;
                    forward =
                        (desiredDistance /* DISTANCE See commment below */ - targetRange) * driveP * DriveConstants.kMaxSpeedMetersPerSecond;
                    //     tagX * lateralP * DriveConstants.kMaxSpeedMetersPerSecond
                // } 
                System.out.println("TURN: " + turn);
                System.out.println("FORWARD: " + forward);
            
        } 
        if (!VisionSubsystem.isTagDetedted()) {
            
        }
        // Drive Robot
        
        
        } 
        
        if(VisionSubsystem.isTagDetedted()) {
            // forward = 0;
            // turn = 0;
        }

        if (targetRange > desiredDistance) {
            m_robotDrive.drive(
                -forward, // x drive
                0, // y drive
                turn, // rotation
                false// is field relative
            );
            System.out.println("DRIVE");

        }
        if (targetRange < desiredDistance) {
            m_robotDrive.drive(
                0, // x drive
                0, // y drive
                0, // rotation
                false// is field relative
            );
            System.out.println("STOP");
        }
    } 

    @Override
    public boolean isFinished() {
        /* Checked with the command ends to see it it should run again
         * When TRUE returned, it will move on to the end command
         */
        //TODO: IF STATEMENT CONDITION MAY NEED TO BE MODIFIED
        // if (targetRange <= desiredDistance) {
        //     return true;
        // } else {
        //     return false; // Modify with logic
        // }
        return false;
    }

    @Override
    public void end(boolean interupted) {
        /* RUNS LAST */
        // Put anything in here that is required to finish a command
    }
}
