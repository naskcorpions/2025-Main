// // INFO: ROBOT IMPORTS
// package frc.robot.commands;
// // INFO: WPILIB IMPORTS
// import edu.wpi.first.wpilibj.DriverStation;
// import edu.wpi.first.wpilibj2.command.Command;
// import edu.wpi.first.wpilibj2.command.Commands;
// import edu.wpi.first.wpilibj2.command.RunCommand;
// import edu.wpi.first.wpilibj2.command.button.JoystickButton;
// import frc.robot.Constants.ControllerConstants;
// import frc.robot.subsystems.ElevatorSubsystem;
// import frc.robot.subsystems.IntakeSubsystem;
// //
// import edu.wpi.first.wpilibj.XboxController;

// import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

// public class SimpleElavatorAndShooter extends Command {
//     private IntakeSubsystem m_intake;
//     private ElevatorSubsystem m_ElevatorSubsystem;
//     private XboxController m_driverController = new XboxController(ControllerConstants.driveController.kDriverControllerPort);
//     // Define other required variables here
//     // Remember that public variables will be accessible through the whole program, 
//     // and private will have to be passed into methods in order to be used.
//     private final ElevatorSubsystem m_elevator = new ElevatorSubsystem();
//     // New Intake subsystem instance
//     private final IntakeSubsystem m_IntakeSubsystem = new IntakeSubsystem();
//     m_ElevatorSubsystem = elevatorSubsystem;
//     m_intake = intakeSubsystem;
//     //addRequirements(m_ElevatorSubsystem);
    
//     //private static SendableChooser<Command> autoChooser;
    
//     public Command simpleElavatorAndShooter(){
//         new JoystickButton(m_driverController, 1).whileTrue(
//             new RunCommand(
//                 () -> {
//                     ElevatorSubsystem.runTestUp();
//                     System.out.println("ELEVATOR DOWN");
//                 }, 
//             m_elevator)
//         );
//         new JoystickButton(m_driverController, 2).whileTrue(
//             new RunCommand(
//                 () -> {
//                     ElevatorSubsystem.runTestDown();
//                     System.out.println("ELEVATOR UP");
//                 }, 
//             m_elevator)
//         );
//         new JoystickButton(m_driverController, 1).whileFalse(
//             new RunCommand(
//                 () -> {
//                     ElevatorSubsystem.stopElevator();
//                     System.out.println("STOP 1");
//                 }, 
//             m_elevator)
//         );
//         new JoystickButton(m_driverController, 2).whileFalse(
//             new RunCommand(
//                 () -> {
//                     ElevatorSubsystem.stopElevator();
//                     System.out.println("STOP 2");
//                 }, 
//             m_elevator)
//         );
//         // the intake wheel spins on its own with no stop
//         new JoystickButton(m_driverController, 3).whileTrue(
//             new RunCommand(
//                 () -> {
//                     m_IntakeSubsystem.manualSpin(0.4);
//                     System.out.println("intake running");
//                 }, 
//             m_IntakeSubsystem)
//         );
//         new JoystickButton(m_driverController, 9).whileTrue(
//             new RunCommand(
//                 () -> {
//                     m_IntakeSubsystem.rotationSpinUp(0.07);
//                     System.out.println("intake !!!!!!!");
//                 }, 
//             m_IntakeSubsystem)
//         );
//         new JoystickButton(m_driverController, 10).whileTrue(
//             new RunCommand(
//                 () -> {
//                     m_IntakeSubsystem.rotationSpinDown(0.05);
//                     System.out.println("intake @@@@@@@@");
//                 }, 
//             m_IntakeSubsystem)
//         );
//         // the intake wheel spins on its own with no stop
//         new JoystickButton(m_driverController, 3).whileFalse(
//             new RunCommand(
//                 () -> {
//                     m_IntakeSubsystem.manualSpin(0.0);
//                     System.out.println("intake not running");
//                 }, 
//             m_IntakeSubsystem)
//         );
//         new JoystickButton(m_driverController, 9).whileFalse(
//             new RunCommand(
//                 () -> {
//                     m_IntakeSubsystem.rotationSpinUp(0.0);
//                     System.out.println("intake not !!!!!!!");
//                 }, 
//             m_IntakeSubsystem)
//         );
//         new JoystickButton(m_driverController, 10).whileFalse(
//             new RunCommand(
//                 () -> {
//                     m_IntakeSubsystem.rotationSpinDown(0.0);
//                     System.out.println("intake not@@@@@@@@");
//                 }, 
//             m_IntakeSubsystem)
//         );
            
//     }
// }
