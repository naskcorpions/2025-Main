package frc.robot.commands;

import java.util.List;

import org.photonvision.PhotonUtils;
import org.photonvision.proto.Photon;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.SwerveConstants.DriveConstants;
import frc.robot.Constants.VisionConstants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.VisionSubsystem;
import frc.robot.subsystems.VisionSubsystemNEW;

import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;


public class AutoAllign extends Command{
    private VisionSubsystem m_vision = new VisionSubsystem();
    private DriveSubsystem m_drive = new DriveSubsystem();

    // List<PhotonPipelineResult> cameraResult;
    PhotonPipelineResult targetedTarget;

    private double turn = 0;
    private double forward = 0;

    private int targetedTag = 1;
    private double desiredDistance = 1.5;
    // TODO: P values will need tuning
    // TODO: ADD TO VISION CONSTANTS
    private final double turnP = 0.005;
    private final double driveP = 0.0002;

    private PhotonTrackedTarget bestTag;
    private int bestTagID;

    public static double targetRange = 0;
    
    public AutoAllign(VisionSubsystem m_vision, DriveSubsystem m_drive) {
        this.m_vision = m_vision;
        this.m_drive = m_drive;
        addRequirements(m_drive, m_vision);

    }
    
    @Override
    public void initialize() {
        /* RUNS ONCE ON INIT */
        // Put any initialization here
    }

    @Override
    public void execute() {
        /* RUNS PEREODICALLY */
        double targetYaw = 0;
        targetRange = 0;


        if (VisionSubsystemNEW.isTagDetedted()) {
            bestTag = VisionSubsystemNEW.returnBestTarget();
            bestTagID = bestTag.getFiducialId();

            if (bestTagID == targetedTag) {
                targetYaw = bestTag.getYaw();
                        targetRange =
                                PhotonUtils.calculateDistanceToTargetMeters(
                                        1.3, // Measured with a tape measure, or in CAD.
                                        1.524, // From 2024 game manual for ID 7
                                        Units.degreesToRadians(0.0), // Measured with a protractor, or in CAD.
                                        Units.degreesToRadians(bestTag.getPitch()));
            }

            // If tag is detected, update turn & forward variables
            if (VisionSubsystemNEW.isTagDetedted()) {
                turn =
                    (0.0 /* ANGLE See commment below */ - targetYaw) * turnP * DriveConstants.kMaxAngularSpeed;
                forward =
                    (desiredDistance /* DISTANCE See commment below */ - targetRange) * driveP * DriveConstants.kMaxSpeedMetersPerSecond;
                System.out.println(targetRange);
            } else {
                //TODO: NEEDS TESTING - Goal - Stop robot when tag isn't detected
                turn = 0;
                forward = 0;
            }
        }

        // Drive Robot
        m_drive.drive(
            forward, // x drive
            0, // y drive
            turn, // rotation
            true// is field relative
        );

    }

    @Override
    public boolean isFinished() {
        /* Checked with the command ends to see it it should run again
         * When TRUE returned, it will move on to the end command
         */
        //TODO: IF STATEMENT CONDITION MAY NEED TO BE MODIFIED
        if (targetRange <= 0.1) {
            return true;
        } else {
            return false; // Modify with logic
        }
    }

    public void end() {
        /* RUNS LAST */
        // Put anything in here that is required to finish a command
    }
}
