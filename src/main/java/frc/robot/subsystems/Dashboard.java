// INFO: ROBOT IMPORTS
package frc.robot.subsystems;
import frc.robot.Elastic;
import frc.robot.Constants.OtherConstants;
import frc.robot.Constants.ControllerConstants.driveController;
import frc.robot.Constants.ElevatorConstants.Intake;
import frc.robot.commands.OTFPath;

// INFO: JAVA IMPORTS
import java.text.FieldPosition;
import java.util.Optional;
// INFO: WPILIB IMPORTS
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class Dashboard extends SubsystemBase {
    // Field for Dashboard
    Field2d field = new Field2d();
    // Field Tag layoout stuff
    private static AprilTagFieldLayout fieldLayout = AprilTagFieldLayout.loadField(AprilTagFields.k2025ReefscapeWelded);
    private static SendableChooser<String> fieldSideChooser = new SendableChooser<String>();
    private static SendableChooser<Integer> tagList = new SendableChooser<Integer>();


    public static void initialize() {
        fieldSideChooser.setDefaultOption("Blue", "blue");
        fieldSideChooser.addOption("Red", "red");
        switchToBlueTags();
        SmartDashboard.putData("Field Side", fieldSideChooser);

    }
    private static void switchToBlueTags() {
        tagList.setDefaultOption("18", 18);
        tagList.addOption("17", 17);
        tagList.addOption("22", 22);
        tagList.addOption("21", 21);
        tagList.addOption("20", 20);
        tagList.addOption("19", 19);
        tagList.addOption("13", 13);
        tagList.addOption("12", 12);
        tagList.addOption("14", 14);
        tagList.addOption("15", 15);
        tagList.addOption("16", 16);
        SmartDashboard.putData("Field Tags", tagList);

    }
    private void switchToRedTage() {

    }
    public static Pose2d returnWantedTagPose() {
        return fieldLayout.getTagPose(tagList.getSelected()).get().toPose2d();
    }
    public static int returnWantedTagID() {
        return tagList.getSelected();
    }
    
    @Override
    public void periodic() {
        if (fieldSideChooser.getSelected() == "blue") {
            switchToBlueTags();
        }
        if (fieldSideChooser.getSelected() == "red") {

        }
        // Put Battery Voltage
        SmartDashboard.putNumber("Batt Volts", RobotController.getBatteryVoltage());
        // Field/Odometry Stuff
        field.setRobotPose(DriveSubsystem.getPoseStatic());
        SmartDashboard.putData("Field", field);
        // Encoder Values
        SmartDashboard.putNumber("Elevator Encoder Pos", ElevatorSubsystem.getEncoderValue());
        SmartDashboard.putNumber("Pivot Encoder Pos", PivotSubsystem.getEncoderValue());

        SmartDashboard.putBoolean("Elevator Top Switch", ElevatorSubsystem.getUpperLimitSwitch());
        SmartDashboard.putBoolean("Elevator Bottom Switch", ElevatorSubsystem.getLowerLimitSwitch());
        SmartDashboard.putBoolean("Intake Switch", IntakeSubsystem.getIntakeSwitch());

        
        // TODO: ADD?
        /*
         * Things to possibly add:
         *    SWERVE: 
         *     - Module Teletry (Each module orientation)
         *     - Chassis Data (Robot overall orientation)
         *    ELEVATOR:
         *     - Position / Level
         *    VISION:
         *     - Camera Data:
         *         - Yaw
         *         - Tag Number
         *         - Tag Distance
         *         - Is in Range/Is alligned?
         *    AUTO:
         *     - Auto Selector
         *    OTHER:
         *     Batt Voltage (Bar or Graph)
         *     Motor Data:
         *       - Temp
         *           * Could have as a number in the dashboard,
         *           *   or it could be sent as a notification
         *     Other Notifications
         *         * Could use other notificaitons related to overall robot health,
         *         *   or any errors
         *     Match Info
         * 
         */
    }

    // REVIEW: Added without testing or setup. Needs to be teseted, and finished.
    // Remove this and the above comment when completed ^^^
    /** Switches to the Teleoperated tab in the Elastic Dashboard */
    public static void switchToTeleopTab() {
        if (OtherConstants.DashboardConstants.switchTabs) { Elastic.selectTab(OtherConstants.DashboardConstants.teleopTabName); }
    }
    /** Switches to the Auto tab in the Elastic Dashboard */
    public static void switchToAutoTab() {
        if (OtherConstants.DashboardConstants.switchTabs) { Elastic.selectTab(OtherConstants.DashboardConstants.autoTabName); }
    }
    public static void switchToInitTab() {
        if (OtherConstants.DashboardConstants.switchTabs) { Elastic.selectTab(OtherConstants.DashboardConstants.initTabName); }
    }


}
