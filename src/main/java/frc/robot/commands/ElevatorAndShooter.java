package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants.ControllerConstants;
// subsystem
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
//
import edu.wpi.first.wpilibj.XboxController;


public class ElevatorAndShooter extends Command{
    // The subsystem the command runs on
    private IntakeSubsystem m_intake;
    private ElevatorSubsystem m_ElevatorSubsystem;
    private XboxController m_driverController = new XboxController(ControllerConstants.driveController.kDriverControllerPort);
    // Define other required variables here
    // Remember that public variables will be accessible through the whole program, 
    // and private will have to be passed into methods in order to be used.
    private final ElevatorSubsystem m_elevator = new ElevatorSubsystem();
    // New Intake subsystem instance
    private final IntakeSubsystem m_IntakeSubsystem = new IntakeSubsystem();

    public ElevatorAndShooter(ElevatorSubsystem elevatorSubsystem, IntakeSubsystem intakeSubsystem) {
        // Brings in private Variable
        m_ElevatorSubsystem = elevatorSubsystem;
        m_intake = intakeSubsystem;
        addRequirements(m_ElevatorSubsystem);
        
        
    }

    @Override
    public void initialize() {
        /* RUNS ONCE ON INIT */
        // Put any initialization here
    }

    @Override
    public void execute() {
        /* RUNS PEREODICALLY */
        // Put any commands that you want to run pereodically here
        configureButtonBindings();
    
    }

    @Override
    public boolean isFinished() {
        /* Checked with the command ends to see it it should run again
         * When TRUE returned, it will move on to the end command
         */
        return false; // Modify with logic
    }

    public void end() {
        /* RUNS LAST */
        // Put anything in here that is required to finish a command
    }
    private void configureButtonBindings() {
        // Run elevator on A button
        new JoystickButton(m_driverController, 1).whileTrue(
            new RunCommand(
                () -> {
                    ElevatorSubsystem.runTestUp();
                    System.out.println("ELEVATOR DOWN");
                }, 
            m_elevator)
        );
        new JoystickButton(m_driverController, 2).whileTrue(
            new RunCommand(
                () -> {
                    ElevatorSubsystem.runTestDown();
                    System.out.println("ELEVATOR UP");
                }, 
            m_elevator)
        );
        new JoystickButton(m_driverController, 1).whileFalse(
            new RunCommand(
                () -> {
                    ElevatorSubsystem.stopElevator();
                    System.out.println("STOP 1");
                }, 
            m_elevator)
        );
        new JoystickButton(m_driverController, 2).whileFalse(
            new RunCommand(
                () -> {
                    ElevatorSubsystem.stopElevator();
                    System.out.println("STOP 2");
                }, 
            m_elevator)
        );
        // the intake wheel spins on its own with no stop
        new JoystickButton(m_driverController, 3).whileTrue(
            new RunCommand(
                () -> {
                    m_IntakeSubsystem.manualSpin(0.4);
                    System.out.println("intake running");
                }, 
            m_IntakeSubsystem)
        );
        new JoystickButton(m_driverController, 9).whileTrue(
            new RunCommand(
                () -> {
                    m_IntakeSubsystem.rotationSpinUp(0.07);
                    System.out.println("intake !!!!!!!");
                }, 
            m_IntakeSubsystem)
        );
        new JoystickButton(m_driverController, 10).whileTrue(
            new RunCommand(
                () -> {
                    m_IntakeSubsystem.rotationSpinDown(0.05);
                    System.out.println("intake @@@@@@@@");
                }, 
            m_IntakeSubsystem)
        );
        // the intake wheel spins on its own with no stop
        new JoystickButton(m_driverController, 3).whileFalse(
            new RunCommand(
                () -> {
                    m_IntakeSubsystem.manualSpin(0.0);
                    System.out.println("intake not running");
                }, 
            m_IntakeSubsystem)
        );
        new JoystickButton(m_driverController, 9).whileFalse(
            new RunCommand(
                () -> {
                    m_IntakeSubsystem.rotationSpinUp(0.0);
                    System.out.println("intake not !!!!!!!");
                }, 
            m_IntakeSubsystem)
        );
        new JoystickButton(m_driverController, 10).whileFalse(
            new RunCommand(
                () -> {
                    m_IntakeSubsystem.rotationSpinDown(0.0);
                    System.out.println("intake not @@@@@@@@");
                }, 
            m_IntakeSubsystem)
        );
            
        }

}
