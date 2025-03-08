// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of the
// WPILib BSD license file in the root directory of this project.
// INFO: ROBOT IMPORTS
package frc.robot;
    // CONSTANTS
    import frc.robot.Constants.ControllerConstants;
    import frc.robot.Constants.OtherConstants;
    import frc.robot.Constants.VisionConstants;
    // COMMANDS
    import frc.robot.commands.AutoAllign;
    import frc.robot.commands.FollowSimplePath;
    import frc.robot.commands.OTFPath;
    // SUBSYTEMS
    import frc.robot.subsystems.DriveSubsystem;
    import frc.robot.subsystems.ElevatorSubsystem;
    import frc.robot.subsystems.VisionSubsystem;
    import frc.robot.subsystems.Dashboard;
    import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.PivotSubsystem;

// INFO: JAVA IMPORTS
import java.util.Set;
// INFO: WPILIB IMPORTS
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.net.PortForwarder;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.DeferredCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
// INFO: PATHPLANNER IMPORTS
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.FollowPathCommand;


/**
* This class is where the bulk of the robot should be declared.  Since Command-based is a
* "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
* periodic methods (other than the scheduler calls).  Instead, the structure of the robot
* (including subsystems, commands, and button mappings) should be declared here.
*/
public class RobotContainer {
    // INFO: CREATE SUBSYSTEMS
    private final DriveSubsystem m_robotDrive = new DriveSubsystem();
    private final VisionSubsystem m_vision = new VisionSubsystem();
    private final Dashboard m_dashboard = new Dashboard();
    private final ElevatorSubsystem m_elevator = new ElevatorSubsystem();
    private final IntakeSubsystem m_intake = new IntakeSubsystem();
    private final PivotSubsystem m_pivot = new PivotSubsystem();
    
    // INFO: CREATAE CONTROLLERS
    XboxController m_driverController = new XboxController(ControllerConstants.driveController.kDriverControllerPort);
    XboxController m_operatorController = new XboxController(ControllerConstants.operatorController.kOperatorControllerPort);
    
    // Other Vars/Constants
    private final SendableChooser<Command> autoChooser;
    
    /**
    * The container for the robot. Contains subsystems, OI devices, and commands.
    */
    public RobotContainer() {
        // Vision System Init
        m_vision.Vision();
        Dashboard.initialize();;

        // NOTE: Forwards the camera's ports, so that the cameras can be accessed through the ROBORIO's USB port
        // REVIEW:
        PortForwarder.add(5800, VisionConstants.RPI1.kRPIIP, 5800);
        PortForwarder.add(5800, VisionConstants.RPI2.kRPIIP, 5800);

        
        m_robotDrive.resetOdometry(OtherConstants.kRobotStartingPose);
        
        // REVIEW:
        // Register Commands Prior to using them in an auto?
        NamedCommands.registerCommand("AutoAllign", Commands.print("Register Auto Allign"));
        NamedCommands.registerCommand("driveToTagCommand", OTFPath.driveToTagCommand());
        
        // REVIEW:
        // Init PathPlanner
        autoChooser = AutoBuilder.buildAutoChooser();
        SmartDashboard.putData("Auto Chooser", autoChooser);
        
        // "Warmup" Paths
        FollowPathCommand.warmupCommand().schedule();
        
        // Configure the button bindings
        configureButtonBindings();
        
        
        // Configure default commands
        // The left stick controls translation of the robot.
        // Turning is controlled by the X axis of the right stick.
        m_robotDrive.setDefaultCommand(
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
        
        // INFO: ROBOT DRIVE
        new JoystickButton(m_driverController, ControllerConstants.driveController.kDriverRobotOrientedDriveButton).whileTrue(
            new RunCommand(
                () -> m_robotDrive.drive(
                    -MathUtil.applyDeadband(m_driverController.getLeftY(), ControllerConstants.driveController.kDriveDeadband),
                    -MathUtil.applyDeadband(m_driverController.getLeftX(), ControllerConstants.driveController.kDriveDeadband),
                    -MathUtil.applyDeadband(m_driverController.getRightX(), ControllerConstants.driveController.kDriveDeadband),
                    false),
                    m_robotDrive));
        
        new JoystickButton(m_driverController, ControllerConstants.driveController.kDriverDefenseButton)
            .whileTrue(new RunCommand(
                () -> m_robotDrive.setX(),
                m_robotDrive));

        // INFO: INTAKE
        new JoystickButton(m_operatorController, ControllerConstants.operatorController.kOperatorIntakeInButton)
            .whileTrue(new RunCommand(() -> m_intake.runIntake(), m_intake))
            .onFalse(new InstantCommand(() -> m_intake.stopIntake(), m_intake));

        new JoystickButton(m_operatorController, ControllerConstants.operatorController.kOperatorIntakeOutButton)
            .whileTrue(new RunCommand(() -> m_intake.reverseIntake(), m_intake))
            .onFalse(new InstantCommand(() -> m_intake.stopIntake(), m_intake));
        

        // INFO: PIVOT
        new JoystickButton(m_operatorController, ControllerConstants.operatorController.kOperatorPivotIntakePoseButton).onTrue(
            new RunCommand( () -> PivotSubsystem.setIntakePosition(), m_pivot)
        );
        new JoystickButton(m_operatorController, ControllerConstants.operatorController.kOperatorPivotOuttakePoseButton).onTrue(
            new RunCommand( () -> PivotSubsystem.setOuttakePosition(), m_pivot)
        );
        new JoystickButton(m_operatorController, ControllerConstants.operatorController.kOperatorPivotStopButton).onTrue(
            new RunCommand( () -> PivotSubsystem.stopMotor(), m_pivot)
        );

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
