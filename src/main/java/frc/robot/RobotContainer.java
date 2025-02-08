// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

// CONSTANTS IMPORT
import frc.robot.Constants.ControllerConfig;
import frc.robot.Constants.GlobalVariables;
// COMMANDS IMPORT
import frc.robot.commands.AutoAllign;
import frc.robot.commands.FollowSimplePath;
import frc.robot.commands.DriveToTag;
// SUBSYTEMS IMPORT
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.VisionSubsystem;
import frc.robot.subsystems.Dashboard;
// ALL OTHER IMPORTS
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import com.pathplanner.lib.auto.NamedCommands;


/*
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems
  private static final DriveSubsystem m_robotDrive = new DriveSubsystem();
  private static final VisionSubsystem m_vision = new VisionSubsystem();
  private static final Dashboard m_dashboard = new Dashboard();
  private static final Elevator m_elevator = new Elevator();

  // The driver's controller
  static XboxController m_driverController = new XboxController(ControllerConfig.driveController.kDriverControllerPort);
  // Operator Controller
  XboxController m_operatorController = new XboxController(ControllerConfig.operatorController.kOperatorControllerPort);

  // Other Vars/Constants
  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Vision System Init
    m_vision.Vision();
    // Elevator System Init 
    m_elevator.Elevator();
    // Dashboard Init
    m_dashboard.dashboardInit();

    // Reset Robot Pose
    m_robotDrive.resetOdometry(GlobalVariables.robotStartingPose);


    // REVIEW:
    // Register Commands Prior to using them in an auto?
    NamedCommands.registerCommand("AutoAllign", Commands.print("Register Auto Allign"));
    NamedCommands.registerCommand("DriveSubsustem", Commands.print("Register DriveSubsystem"));

    // Configure the button bindings
    configureButtonBindings();


    // Configure default commands
    m_robotDrive.setDefaultCommand(
        // The left stick controls translation of the robot.
        // Turning is controlled by the X axis of the right stick.
        new RunCommand(
            () -> m_robotDrive.drive(
                -MathUtil.applyDeadband(m_driverController.getLeftY(), ControllerConfig.driveController.kDriveDeadband),
                -MathUtil.applyDeadband(m_driverController.getLeftX(), ControllerConfig.driveController.kDriveDeadband),
                -MathUtil.applyDeadband(m_driverController.getRightX(), ControllerConfig.driveController.kDriveDeadband),
                true),
            m_robotDrive));

  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by
   * instantiating a {@link edu.wpi.first.wpilibj.GenericHID} or one of its
   * subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then calling
   * passing it to a
   * {@link JoystickButton}.
   */
  public static void configureButtonBindings() {
    new JoystickButton(m_driverController, ControllerConfig.driveController.kDriverDefenseButton)
      .whileTrue(new RunCommand(
          () -> m_robotDrive.setX(),
          m_robotDrive));
    // NEW ALLIGN
    new JoystickButton(m_driverController, ControllerConfig.driveController.kDriverAutoAllignButton)
        .whileTrue(new AutoAllign(m_vision, m_robotDrive));
    // Follow Path
    new JoystickButton(m_driverController, ControllerConfig.driveController.kDriverPathRunButton)
        .whileTrue(FollowSimplePath.followPath());
    // Follow Auto Command
    new JoystickButton(m_driverController, ControllerConfig.driveController.kDriverAutoAllignButton)
        .whileTrue(FollowSimplePath.followAuto());
    // Run Robot Relative
    new JoystickButton(m_driverController, ControllerConfig.driveController.kDriverDriveRobotRelative).whileTrue(
      new RunCommand(
              () -> m_robotDrive.drive(
                  -MathUtil.applyDeadband(m_driverController.getLeftY(), ControllerConfig.driveController.kDriveDeadband),
                  -MathUtil.applyDeadband(m_driverController.getLeftX(), ControllerConfig.driveController.kDriveDeadband),
                  -MathUtil.applyDeadband(m_driverController.getRightX(), ControllerConfig.driveController.kDriveDeadband),
                  false),
              m_robotDrive));
    // Drive To Tag
    new JoystickButton(m_driverController, ControllerConfig.driveController.kDriveToTag)
      .whileTrue(new DriveToTag(m_vision, m_robotDrive));
  }



  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  // REVIEW:
  public Command getAutonomousCommand() {
    // INFO: Returns the selected auto's command to run when enabled
    return FollowSimplePath.followAuto();
  }
}
