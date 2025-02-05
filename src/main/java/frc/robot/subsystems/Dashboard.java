package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Elastic;
import frc.robot.Constants.OtherConstants;
import edu.wpi.first.wpilibj.RobotController;

public class Dashboard extends SubsystemBase {
    
    @Override
    public void periodic() {
        SmartDashboard.putNumber("Batt Volts", RobotController.getBatteryVoltage());

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
    // Remove the above comment when completed ^^^
    /** Switches to the Teleoperated tab in the Elastic Dashboard */
    public void switchToTeleopTab() {
        Elastic.selectTab(OtherConstants.DashboardConstants.teleopTabName);
    }
    /** Switches to the Auto tab in the Elastic Dashboard */
    public void switchToAutoTab() {
        Elastic.selectTab(OtherConstants.DashboardConstants.autoTabName);
    }


}
