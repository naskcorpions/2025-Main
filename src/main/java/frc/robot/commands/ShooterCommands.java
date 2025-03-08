package frc.robot.commands;

// Import WPILib PID controller for closed-loop control and MathUtil for clamping values
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.MathUtil;
// Import the subsystems required by shooter commands
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.PivotSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

/*
 * ShooterCommands class provides composite commands that simultaneously control both
 * the elevator and pivot subsystems to reach pre-defined shooter positions. Each shooter
 * mode is defined by exact target encoder values (for the elevator) and target angles (for the pivot).
 * The command uses PID controllers to continuously adjust speeds until the current positions 
 * exactly match the target setpoints.
 */
public class ShooterCommands {
    // --- Composite command for shooter positions using PID control ---
    public static class ShooterCommand extends edu.wpi.first.wpilibj2.command.Command {
        // Subsystem references.
        private final ElevatorSubsystem elevator;
        private final PivotSubsystem pivot;
        private final IntakeSubsystem intake;
        // Exact target setpoints for elevator and pivot.
        private final double elevatorTarget;
        private final double pivotTarget;

        // PID controllers with example gains; adjust as needed.
        private final PIDController elevatorPID = new PIDController(0.01, 0, 0);
        private final PIDController pivotPID = new PIDController(0.1, 0, 0);

        /*
         * Constructor.
         * @param elevator       Elevator subsystem instance.
         * @param pivot          Pivot subsystem instance.
         * @param intake         Intake subsystem instance.
         * @param elevatorTarget The exact target elevator encoder position (in degrees).
         * @param pivotTarget    The exact target pivot encoder position.
         */
        public ShooterCommand(ElevatorSubsystem elevator, PivotSubsystem pivot, IntakeSubsystem intake,
                              double elevatorTarget, double pivotTarget) {
            this.elevator = elevator;
            this.pivot = pivot;
            this.intake = intake;
            this.elevatorTarget = elevatorTarget;
            this.pivotTarget = pivotTarget;
            addRequirements(elevator, pivot, intake);
        }

        @Override
        public void initialize() {
            // Reset both PID controllers.
            elevatorPID.reset();
            pivotPID.reset();
        }

        @Override
        public void execute() {
            // Elevator PID control.
            double elevatorSpeed = MathUtil.clamp(
                elevatorPID.calculate(ElevatorSubsystem.getEncoderValue(), elevatorTarget), -0.5, 0.5);
            elevator.runElevator(elevatorSpeed);

            // Pivot PID control.
            double pivotSpeed = MathUtil.clamp(
                pivotPID.calculate(pivot.getCurrentPosition(), pivotTarget), -0.3, 0.3);
            pivot.movePivot(pivotSpeed);
        }

        @Override
        public boolean isFinished() {
            // Command finishes exactly when the current positions equal the target setpoints.
            return ElevatorSubsystem.getEncoderValue() == elevatorTarget
                    && pivot.getCurrentPosition() == pivotTarget;
        }

        @Override
        public void end(boolean interrupted) {
            elevator.stopElevator();
            PivotSubsystem.stopMotor();
        }
    }

    // --- Factory methods for shooter positions ---
    public static edu.wpi.first.wpilibj2.command.Command shooterLow(ElevatorSubsystem elevator, PivotSubsystem pivot, IntakeSubsystem intake) {
        double elevatorTarget = 50.0;   // Low shooter elevator setpoint.
        double pivotTarget = 0.2;         // Low shooter pivot setpoint.
        return new ShooterCommand(elevator, pivot, intake, elevatorTarget, pivotTarget);
    }
    
    public static edu.wpi.first.wpilibj2.command.Command shooterMidLow(ElevatorSubsystem elevator, PivotSubsystem pivot, IntakeSubsystem intake) {
        double elevatorTarget = 100.0;  // Mid-low shooter elevator setpoint.
        double pivotTarget = 0.3;
        return new ShooterCommand(elevator, pivot, intake, elevatorTarget, pivotTarget);
    }
    
    public static edu.wpi.first.wpilibj2.command.Command shooterMidHigh(ElevatorSubsystem elevator, PivotSubsystem pivot, IntakeSubsystem intake) {
        double elevatorTarget = 150.0;  // Mid-high shooter elevator setpoint.
        double pivotTarget = 0.4;
        return new ShooterCommand(elevator, pivot, intake, elevatorTarget, pivotTarget);
    }
    
    public static edu.wpi.first.wpilibj2.command.Command shooterHigh(ElevatorSubsystem elevator, PivotSubsystem pivot, IntakeSubsystem intake) {
        double elevatorTarget = 200.0;  // High shooter elevator setpoint.
        double pivotTarget = 0.5;
        return new ShooterCommand(elevator, pivot, intake, elevatorTarget, pivotTarget);
    }
    // ...existing code if any...
}
