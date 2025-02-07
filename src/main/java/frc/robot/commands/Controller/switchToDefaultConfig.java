package frc.robot.commands.Controller;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.Constants.ControllerConfig;

public class switchToDefaultConfig {
    
    public static Command switchConfig() {
        ControllerConfig.driveController.kDriverDefenseButton = 6; //Set to Right Bumper
        ControllerConfig.driveController.kDriverAutoAllignButton = 2; // Set to Left Bumper
        
        ControllerConfig.driveController.kDriverPathRunButton = 4;

        ControllerConfig.driveController.kDriverFollowSimpleAuto = 3;

        ControllerConfig.driveController.kDriverDriveRobotRelative = 6;

        System.out.println("ctfvbhjn");
        return Commands.none();
    }

}
