package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Elastic;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.Constants.ControllerConfig;
import frc.robot.Constants.OtherConstants;
import frc.robot.Constants.ControllerConfig.driveController;
import frc.robot.commands.Controller.switchToDefaultConfig;
import frc.robot.commands.Controller.switchToDriver1Config;

import java.util.List;

import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.wpilibj.RobotController;

public class Dashboard extends SubsystemBase {
    private double battLowest = 100;

    // Auto Command Chooser
    private SendableChooser<Command> autoChooser;
    // Driver & Operator Config Chooser
    private SendableChooser<String> driverChooser = new SendableChooser<>();
    private SendableChooser<String> operatorChooser = new SendableChooser<>();

    String driverConfig;


    public void dashboardInit() {
        autoChooser = AutoBuilder.buildAutoChooser();
        SmartDashboard.putData("Auto Chooser", autoChooser);
        
        // Drive Controller Config
        driverChooser.setDefaultOption("Default Config", "defaultConfig");
        driverChooser.addOption("Config 1", "config1");

        SmartDashboard.putData("Driver Config", driverChooser);


    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Batt Volts", RobotController.getBatteryVoltage());
        SmartDashboard.putNumber("Gyro Angle", Math.floor(DriveSubsystem.getGyroAngle() * 1000) / 1000);
        if (RobotController.getBatteryVoltage() < battLowest) {
            battLowest = Math.floor(RobotController.getBatteryVoltage() * 100) / 100;
        }
        SmartDashboard.putNumber("Batt Lowest", battLowest);

        driverConfig = driverChooser.getSelected();
        configureControllerBindings();

        // TODO: ADD?
        /*
         * Things to possibly add:
         *    SWERVE: 
         *     - Module Telemetry (Each module orientation)
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

    public void configureControllerBindings(){
        switch (driverConfig) {
            case "defaultConfig":
                switchToDefaultConfig.switchConfig();
                ControllerConfig.driveController.checkID();
                RobotContainer.configureButtonBindings();
                break;
            case "config1":
                switchToDriver1Config.switchConfig();
                ControllerConfig.driveController.checkID();
                RobotContainer.configureButtonBindings();
            default:
                // TODO: Handle invalic config
                break;
        }


    }



}
