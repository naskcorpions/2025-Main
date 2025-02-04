package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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

}
