package frc.robot.commands;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathPlannerPath;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

public class FollowSimplePath extends Command {

    private static SendableChooser<Command> autoChooser;

    public static Command followPath() {
        autoChooser = AutoBuilder.buildAutoChooser();
        try {
          PathPlannerPath path = PathPlannerPath.fromPathFile("Straight 1");
          return AutoBuilder.followPath(path);
        } catch (Exception r) {
          DriverStation.reportError("PATAHPLANNER ERROR", r.getStackTrace());
          return Commands.none();
        }
    }
}
