// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
//INFO: ROBOT IMPORTS
package frc.robot.subsystems;
import frc.robot.Constants.OtherConstants;
import frc.robot.Constants.SwerveConstants.DriveConstants;

// INFO: WPILIB IMPORTS
import edu.wpi.first.hal.FRCNetComm.tInstances;
import edu.wpi.first.hal.FRCNetComm.tResourceType;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
// INFO: PATHPLANNER IMPORTS
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.config.PIDConstants;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.controllers.PPHolonomicDriveController;
// INFO: OTHER IMPORTS
import com.studica.frc.AHRS;

/*********************************************************************************************************
 * 
 * 
 * We inverted the gyro in the drive method by adding a negative sign
 *      In SwerveConstants.java, there is a boolean that reverses the gyro
 *      Could be adding the negative sign, instead of just switching the boolean in the constants file
 *          be the reason that the odometry was having an issue? 
 * 
 * 
 ********************************************************************************************************/

public class DriveSubsystem extends SubsystemBase {
    // Create MAXSwerveModules
    private static final MAXSwerveModule m_frontLeft = new MAXSwerveModule(
    DriveConstants.kFrontLeftDrivingCanId,
    DriveConstants.kFrontLeftTurningCanId,
    DriveConstants.kFrontLeftChassisAngularOffset);
    
    private static final MAXSwerveModule m_frontRight = new MAXSwerveModule(
    DriveConstants.kFrontRightDrivingCanId,
    DriveConstants.kFrontRightTurningCanId,
    DriveConstants.kFrontRightChassisAngularOffset);
    
    private static final MAXSwerveModule m_rearLeft = new MAXSwerveModule(
    DriveConstants.kRearLeftDrivingCanId,
    DriveConstants.kRearLeftTurningCanId,
    DriveConstants.kBackLeftChassisAngularOffset);
    
    private static final MAXSwerveModule m_rearRight = new MAXSwerveModule(
    DriveConstants.kRearRightDrivingCanId,
    DriveConstants.kRearRightTurningCanId,
    DriveConstants.kBackRightChassisAngularOffset);
    
    // The gyro sensor
    private static final AHRS m_gyro = new AHRS(AHRS.NavXComType.kMXP_SPI);
    
    public static final SwerveDrivePoseEstimator m_odometry = 
        new SwerveDrivePoseEstimator(
            DriveConstants.kDriveKinematics, 
        new Rotation2d(getHeading()), 
        new SwerveModulePosition[] {
            m_frontLeft.getPosition(),
            m_frontRight.getPosition(),
            m_rearLeft.getPosition(),
            m_rearRight.getPosition()
        },
        // Change to estimated start pose
        OtherConstants.kRobotStartingPose);
    
    /** Creates a new DriveSubsystem. */
    public DriveSubsystem() {
        // Usage reporting for MAXSwerve template
        HAL.report(tResourceType.kResourceType_RobotDrive, tInstances.kRobotDriveSwerve_MaxSwerve);
        
        // @SuppressWarnings("unused")
        RobotConfig pathConfig = null;
        try {
            pathConfig = RobotConfig.fromGUISettings();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(pathConfig != null) {
            AutoBuilder.configure(
            this::getPose,
            this::resetOdometry, 
            this::getRobotChassisSpeeds, 
            (speeds, feedforwards) -> driveRobotRelative(speeds), 
            new PPHolonomicDriveController(
                new PIDConstants(
                    OtherConstants.AutoBuilderConstants.kTranslationP, 
                    OtherConstants.AutoBuilderConstants.kTranslationI, 
                    OtherConstants.AutoBuilderConstants.kTranslationD
                ),
                new PIDConstants(
                    OtherConstants.AutoBuilderConstants.kRotationP, 
                    OtherConstants.AutoBuilderConstants.kRotationI, 
                    OtherConstants.AutoBuilderConstants.kRotationD
                )
            ),
            pathConfig,
            () -> {
                var alliance = DriverStation.getAlliance();
                if (alliance.isPresent()) {
                    return alliance.get() == DriverStation.Alliance.Red;
                }
                return false;
            }, 
            this
            );
        }
    }
    
    @Override
    public void periodic() {
        // REVIEW:
        // Should update the robot odometry based on the estimated tag position
        // if (VisionSubsystem.result.getMultiTagResult().isPresent()) {
        //     m_odometry.addVisionMeasurement(
        //         VisionSubsystem.robotFieldPose(), 
        //         Timer.getFPGATimestamp(),
        //         VisionSubsystem.kMultiTagStdDevs
        //     );
        //     System.out.println(VisionSubsystem.robotFieldPose());
        // }
        // Put pos vslurd into Elastic
        // SmartDashboard.putNumber("robotOdometry X", m_odometry.getEstimatedPosition().getX());
        // SmartDashboard.putNumber("robotOdometry Y", m_odometry.getEstimatedPosition().getY());
        // SmartDashboard.putNumber("robotOdometry Rotation", m_odometry.getEstimatedPosition().getRotation().getDegrees());
        // SmartDashboard.putNumber("Odometry Rotation", m_odometry.getEstimatedPosition().getRotation().getDegrees());

        // Update the odometry in the periodic block
        m_odometry.update(
            Rotation2d.fromDegrees(m_gyro.getAngle()),
            new SwerveModulePosition[] {
                m_frontLeft.getPosition(),
                m_frontRight.getPosition(),
                m_rearLeft.getPosition(),
                m_rearRight.getPosition()
        });
        // SmartDashboard.putNumber("Gyro Pitch", m_gyro.getPitch());
        // SmartDashboard.putNumber("Gyro Yaw", m_gyro.getYaw());
        // SmartDashboard.putNumber("Gyro Roll", m_gyro.getRoll());
        // Should update the robot odometry based on the estimated tag position
        // m_odometry.addVisionMeasurement(
        // VisionSubsystem.getEstimatedFieldRobotPose(), 
        // VisionSubsystem.returnVisionLatentcy());
    }
    
    /**
    * Returns the currently-estimated pose of the robot.
    *
    * @return The pose.
    */
    public Pose2d getPose() {
        return m_odometry.getEstimatedPosition();
        
    }
    public static Pose2d getPoseStatic() {
        return m_odometry.getEstimatedPosition();
    }
    
    /**
    * Resets the odometry to the specified pose.
    *
    * @param pose The pose to which to set the odometry.
    */
    public void resetOdometry(Pose2d pose) {
        m_odometry.resetPosition(
        Rotation2d.fromDegrees(m_gyro.getAngle()),
        new SwerveModulePosition[] {
            m_frontLeft.getPosition(),
            m_frontRight.getPosition(),
            m_rearLeft.getPosition(),
            m_rearRight.getPosition()
        },
        pose);
    }
    
    /**
    * Method to drive the robot using joystick info.
    *
    * @param xSpeed        Speed of the robot in the x direction (forward).
    * @param ySpeed        Speed of the robot in the y direction (sideways).
    * @param rot           Angular rate of the robot.
    * @param fieldRelative Whether the provided x and y speeds are relative to the
    *                      field.
    */

    public void drive(double xSpeed, double ySpeed, double rot, boolean fieldRelative) {
        // Convert the commanded speeds into the correct units for the drivetrain
        double xSpeedDelivered = xSpeed * DriveConstants.kMaxSpeedMetersPerSecond;
        double ySpeedDelivered = ySpeed * DriveConstants.kMaxSpeedMetersPerSecond;
        double rotDelivered = rot * DriveConstants.kMaxAngularSpeed;
        
        // Handles Field Oriented Stuff ---------------------------------------------------------------------
        var swerveModuleStates = DriveConstants.kDriveKinematics.toSwerveModuleStates(
        fieldRelative
        // TRUE
        ? ChassisSpeeds.fromFieldRelativeSpeeds(xSpeedDelivered, ySpeedDelivered, rotDelivered,
        Rotation2d.fromDegrees(-m_gyro.getYaw()))
        // FALSE
        : new ChassisSpeeds(xSpeedDelivered, ySpeedDelivered, rotDelivered));
        // --------------------------------------------------------------------------------------------------
        SwerveDriveKinematics.desaturateWheelSpeeds(
        swerveModuleStates, DriveConstants.kMaxSpeedMetersPerSecond);
        m_frontLeft.setDesiredState(swerveModuleStates[0]);
        m_frontRight.setDesiredState(swerveModuleStates[1]);
        m_rearLeft.setDesiredState(swerveModuleStates[2]);
        m_rearRight.setDesiredState(swerveModuleStates[3]);
    }
    
    // Duplicate methods for PathPlanner
    private void drive(ChassisSpeeds speeds, boolean fieldRelative) {
        if (fieldRelative) {
            speeds = ChassisSpeeds.fromFieldRelativeSpeeds(speeds, getPose().getRotation());
        }
        var swerveModuleStates = DriveConstants.kDriveKinematics.toSwerveModuleStates(speeds);
        SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, DriveConstants.kMaxSpeedMetersPerSecond);
        setModuleStates(swerveModuleStates);
        
        
    }
    private void driveRobotRelative(ChassisSpeeds speeds) {
        drive(speeds, false);
    }
    /**
    * Sets the wheels into an X formation to prevent movement.
    */
    public void setX() {
        m_frontLeft.setDesiredState(new SwerveModuleState(0, Rotation2d.fromDegrees(45)));
        m_frontRight.setDesiredState(new SwerveModuleState(0, Rotation2d.fromDegrees(-45)));
        m_rearLeft.setDesiredState(new SwerveModuleState(0, Rotation2d.fromDegrees(-45)));
        m_rearRight.setDesiredState(new SwerveModuleState(0, Rotation2d.fromDegrees(45)));
    }
    
    /**
    * Sets the swerve ModuleStates.
    *
    * @param desiredStates The desired SwerveModule states.
    */
    public void setModuleStates(SwerveModuleState[] desiredStates) {
        SwerveDriveKinematics.desaturateWheelSpeeds(
        desiredStates, DriveConstants.kMaxSpeedMetersPerSecond);
        m_frontLeft.setDesiredState(desiredStates[0]);
        m_frontRight.setDesiredState(desiredStates[1]);
        m_rearLeft.setDesiredState(desiredStates[2]);
        m_rearRight.setDesiredState(desiredStates[3]);
    }
    
    /** Resets the drive encoders to currently read a position of 0. */
    public void resetEncoders() {
        m_frontLeft.resetEncoders();
        m_rearLeft.resetEncoders();
        m_frontRight.resetEncoders();
        m_rearRight.resetEncoders();
    }
    
    /** Zeroes the heading of the robot. */
    public void zeroHeading() {
        m_gyro.reset();
    }
    
    /**
    * Returns the heading of the robot.
    *
    * @return the robot's heading in degrees, from -180 to 180
    */
    public static double getHeading() {
        return m_gyro.getAngle();
    }
    
    /**
    * Returns the turn rate of the robot.
    *
    * @return The turn rate of the robot, in degrees per second
    */
    public double getTurnRate() {
        // return m_oldGyro.getRate(IMUAxis.kZ) * (DriveConstants.kGyroReversed ? -1.0 : 1.0);
        //TODO: CHECK IF VALUE RETURNED IS CORRECT
        return m_gyro.getRate() * (DriveConstants.kGyroReversed ? -1.0 : 1.0);
    }
    
    private SwerveModuleState[] getModuleStates() {
        return new SwerveModuleState[] {
            m_frontLeft.getState(),
            m_frontRight.getState(),
            m_rearLeft.getState(),
            m_rearRight.getState()
        };
    }
    private ChassisSpeeds getRobotChassisSpeeds() {
        return DriveConstants.kDriveKinematics.toChassisSpeeds(getModuleStates());
    }
    // Try to change name from newPoseEstimator to m_odometry to drop in replace it
    
    
}
