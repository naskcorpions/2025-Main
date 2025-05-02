// INFO: ROBOT IMPORTS
package frc.robot.subsystems;
import frc.robot.Configs;
import frc.robot.Constants.ElevatorConstants;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
// INFO: WPILIB IMPORTS
import edu.wpi.first.wpilibj2.command.SubsystemBase;
// INFO: REV IMPORTS
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;

public class ElevatorSubsystem extends SubsystemBase {
    // Create instance
    public static ElevatorSubsystem instance;
    public static ElevatorSubsystem getInstance() {
        if (instance == null) {
            instance = new ElevatorSubsystem();
        }
        return instance;
    }

    // ELEVATOR MOTORS
    private static SparkMax elevatorLeft = new SparkMax(ElevatorConstants.Elevator.kElevatorLeftMotor, MotorType.kBrushless);
    private static SparkMax elevatorRight = new SparkMax(ElevatorConstants.Elevator.kElevatorRightMotor, MotorType.kBrushless);



    /* IMPORTANT:
     * NOTE:
     * NOTE:    Ensure that the .setPosition(double) on encoder.setPosition(0) is the right method
     * NOTE:    It should set the encoder value based on where it is
     * NOTE:        Example: If the elevator hits the lower limit switch, it should
     * NOTE:                    tell the elevator to stop, and then set the encoder value
     * NOTE:                    to the bottom position (basically resetting the encoder)
     * NOTE:    USE CASE:
     * NOTE:        When we start the code/enable, if it starts with the elevator above the minimum
     * NOTE:        elevation, then we can run the elevator down to 0 it's position
     * NOTE:      
     */
    // IMPORTANT:
    
    public ElevatorSubsystem() {
        // Configure elevator motors
        elevatorLeft.configure(Configs.ElevatorConfig.elevatorLeftConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        elevatorRight.configure(Configs.ElevatorConfig.elevatorRightConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);        
    }
    
    
    @Override
    public void periodic() {

    }
    
    // Sets elevator motors to specific speed
    public Command runElevator(double speed) {
        return new RunCommand( () -> {
            elevatorLeft.set(speed);
            elevatorRight.set(speed);
        }, 
        instance);
    }

    // Stop both elevator motors
    @SuppressWarnings("unused")
    private Command stopElevator() {
        return new RunCommand(() -> {
            elevatorLeft.stopMotor();
            elevatorRight.stopMotor();
        }, instance);
    }

}