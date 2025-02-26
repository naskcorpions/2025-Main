// INFO: ROBOT IMPORTS
package frc.robot.subsystems;
import frc.robot.Elastic;
import frc.robot.Constants.OtherConstants;
import frc.robot.Constants.ControllerConstants.driveController;
// INFO: WPILIB IMPORTS
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import java.text.FieldPosition;

import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class Dashboard extends SubsystemBase {

    // private static final SendableChooser driveConfigChooser;
    // private static final SendableChooser operatorConfigChooser;
    Field2d field = new Field2d();

    public static void initialize() {
        // driveConfigChooser
        //     .addOption(null, driveConfigChooser);
        // SmartDashboard.putData("Driver Config", driveConfigChooser);
        // SmartDashboard.putData("OperatorController", operatorConfigChooser);
    }
    
    @Override
    public void periodic() {
        SmartDashboard.putNumber("Batt Volts", RobotController.getBatteryVoltage());
        SmartDashboard.putNumber("Elevator Encoder Pos", ElevatorSubsystem.getEncoderValue());
        field.setRobotPose(DriveSubsystem.getPoseStatic());
        SmartDashboard.putData("Field", field);
        
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
