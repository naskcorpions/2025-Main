// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of the
// WPILib BSD license file in the root directory of this project.
// INFO: ROBOT IMPORTS
package frc.robot;
    // CONSTANTS
    import frc.robot.Constants.ControllerConstants;
    // COMMANDS
    import frc.robot.commands.AutoAllign;
    import frc.robot.commands.ExampleCommand;
    import frc.robot.commands.FollowSimplePath;
    // SUBSYTEMS
    import frc.robot.subsystems.DriveSubsystem;
    import frc.robot.subsystems.ElevatorSubsystem;
    import frc.robot.subsystems.SensorSubsystem;
    import frc.robot.subsystems.VisionSubsystem;
    import frc.robot.subsystems.Dashboard;
    import frc.robot.subsystems.IntakeSubsystem;
// INFO: WPILIB IMPORTS
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
// INFO: JAVA IMPORTS
import java.util.concurrent.Exchanger;
// INFO: PATHPLANNER IMPORTS
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.FollowPathCommand;


/*
* This class is where the bulk of the robot should be declared.  Since Command-based is a
* "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
* periodic methods (other than the scheduler calls).  Instead, the structure of the robot
* (including subsystems, commands, and button mappings) should be declared here.
*/
public class RobotContainer {
    // The robot's subsystems
    private final DriveSubsystem m_robotDrive = new DriveSubsystem();
    private final VisionSubsystem m_vision = new VisionSubsystem();
    private final Dashboard m_dashboard = new Dashboard();
    private final ElevatorSubsystem m_elevator = new ElevatorSubsystem();
    // New Intake subsystem instance
    private final IntakeSubsystem m_intake = new IntakeSubsystem();
    
    // The driver's controller
    XboxController m_driverController = new XboxController(ControllerConstants.driveController.kDriverControllerPort);
    // Operator Controller
    XboxController m_operatorController = new XboxController(ControllerConstants.operatorController.kOperatorControllerPort);
    
    // Other Vars/Constants
    private final SendableChooser<Command> autoChooser;
    /**
    * The container for the robot. Contains subsystems, OI devices, and commands.
    */
    public RobotContainer() {
        // Vision System Init
        m_vision.Vision();
        
        Pose2d robotStartingPose = new Pose2d(2.359, 0.817, new Rotation2d(0));
        m_robotDrive.resetOdometry(robotStartingPose);
        
        // REVIEW:
        // Register Commands Prior to using them in an auto?
        NamedCommands.registerCommand("AutoAllign", Commands.print("Register Auto Allign"));
        
        // REVIEW:
        // Init PathPlanner
        autoChooser = AutoBuilder.buildAutoChooser();
        SmartDashboard.putData("Auto Chooser", autoChooser);
        
        // Configure the button bindings
        configureButtonBindings();
        
        
        // Configure default commands
        m_robotDrive.setDefaultCommand(
        // The left stick controls translation of the robot.
        // Turning is controlled by the X axis of the right stick.
        new RunCommand(
        () -> m_robotDrive.drive(
        -MathUtil.applyDeadband(m_driverController.getLeftY(), ControllerConstants.driveController.kDriveDeadband),
        -MathUtil.applyDeadband(m_driverController.getLeftX(), ControllerConstants.driveController.kDriveDeadband),
        -MathUtil.applyDeadband(m_driverController.getRightX(), ControllerConstants.driveController.kDriveDeadband),
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
    private void configureButtonBindings() {
        new JoystickButton(m_driverController, ControllerConstants.driveController.kDriverDefenseButton)
        .whileTrue(new RunCommand(
        () -> m_robotDrive.setX(),
        m_robotDrive));
        
        // NEW ALLIGN
        new JoystickButton(m_driverController, ControllerConstants.driveController.kDriverAutoAllignButton)
        .whileTrue(new AutoAllign(m_vision, m_robotDrive));
        
        new JoystickButton(m_driverController, ControllerConstants.driveController.kDriverPathRunButton)
        .whileTrue(FollowSimplePath.followPath());
        new POVButton(m_driverController, 2).whileTrue(
        new RunCommand(
        () -> System.out.println("rdtcfvhbj")
        ));
        new JoystickButton(m_driverController, 6).whileTrue(
        new RunCommand(
        () -> m_robotDrive.drive(
        -MathUtil.applyDeadband(m_driverController.getLeftY(), ControllerConstants.driveController.kDriveDeadband),
        -MathUtil.applyDeadband(m_driverController.getLeftX(), ControllerConstants.driveController.kDriveDeadband),
        -MathUtil.applyDeadband(m_driverController.getRightX(), ControllerConstants.driveController.kDriveDeadband),
        false),
        m_robotDrive));
    }
    
    
    
    /**
    * Use this to pass the autonomous command to the main {@link Robot} class.
    *
    * @return the command to run in autonomous
    */
    // REVIEW:
    public Command getAutonomousCommand() {
        // INFO: Returns the selected auto's command to run when enabled
        return autoChooser.getSelected();
    }
}
