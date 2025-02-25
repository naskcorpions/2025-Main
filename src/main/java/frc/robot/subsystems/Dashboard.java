// INFO: ROBOT IMPORTS
package frc.robot.subsystems;
import frc.robot.Elastic;
import frc.robot.Constants.OtherConstants;
import frc.robot.Constants.ControllerConstants.driveController;
// INFO: WPILIB IMPORTS
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.rmi.server.ObjID;

import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class Dashboard extends SubsystemBase {
    public static enum driverConfig { DEFAULT, DRIVER1, DRIVER2, DRIVER3 }
    public static enum operatorConfig { DEFAULT, OPERATOR1, OPERATOR2, OPERATOR3 }

    private static final SendableChooser<driverConfig> driveConfigChooser = new SendableChooser<>();
    private static final SendableChooser<operatorConfig> operatorConfigChooser = new SendableChooser<>();

    public static void initialize() {
        // INFO: CONFIG DRIVE OPTIONS
        driveConfigChooser.setDefaultOption("Default Driver", driverConfig.DEFAULT);
        driveConfigChooser.addOption("Driver 1", driverConfig.DRIVER1);
        driveConfigChooser.addOption("Driver 2", driverConfig.DRIVER2);
        driveConfigChooser.addOption("Driver 3", driverConfig.DRIVER3);
        
        // INFO: CONFIG OPERATOR OPTIONS
        operatorConfigChooser.setDefaultOption("Default Operator", operatorConfig.DEFAULT);
        operatorConfigChooser.addOption("Operator 1", operatorConfig.OPERATOR1);
        operatorConfigChooser.addOption("Operator 2", operatorConfig.OPERATOR2);
        operatorConfigChooser.addOption("Operator 3", operatorConfig.OPERATOR3);

        // INFO: PUT CHOOSERS ON DASHBOARD
        SmartDashboard.putData("Driver Config", driveConfigChooser);
        SmartDashboard.putData("OperatorController", operatorConfigChooser);



    }
    
    @Override
    public void periodic() {
        var selectedDriveConfig = driveConfigChooser.getSelected();
        var selectedOperatorConfig = operatorConfigChooser.getSelected();
        switch (selectedDriveConfig) {
            case DEFAULT : /*  */ break;
            case DRIVER1 : /*  */ break;
            case DRIVER2 : /*  */ break;
            case DRIVER3 : /*  */ break;
        }
        switch (selectedOperatorConfig) {
            case DEFAULT : /*   */ break;
            case OPERATOR1 : /*  */ break;
            case OPERATOR2 : /*  */ break;
            case OPERATOR3 : /*  */ break;
        }
        

        SmartDashboard.putNumber("Batt Volts", RobotController.getBatteryVoltage());
        SmartDashboard.putNumber("Elevator Encoder Pos", ElevatorSubsystem.getEncoderValue());

        
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
    public void switchToTeleopTab() {
        Elastic.selectTab(OtherConstants.DashboardConstants.teleopTabName);
    }
    /** Switches to the Auto tab in the Elastic Dashboard */
    public void switchToAutoTab() {
        Elastic.selectTab(OtherConstants.DashboardConstants.autoTabName);
    }


}
