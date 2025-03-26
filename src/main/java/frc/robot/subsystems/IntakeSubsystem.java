// INFO: ROBOT IMPORTS
package frc.robot.subsystems;
// INFO: WPILIB IMPORTS
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Configs;
import frc.robot.Constants.ElevatorConstants;
import frc.robot.Constants.SensorPorts;
import frc.robot.Constants.ControllerConstants.operatorController;
import frc.robot.Constants.ElevatorConstants.Intake;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;

// INFO: REV IMPORTS
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;

public class IntakeSubsystem extends SubsystemBase {
    public static IntakeSubsystem instance;
    public static IntakeSubsystem getInstance() {
        if(instance == null) {
            instance = new IntakeSubsystem();
        }
        return instance;
    }

    private static DigitalInput limitSwitch = new DigitalInput(SensorPorts.LimitSwitches.intakeSwitch);
    public static boolean getIntakeSwitch() { return !limitSwitch.get(); }

    private SparkMax intakeMotor = new SparkMax(ElevatorConstants.Intake.kIntakeMotor, MotorType.kBrushless);
    private boolean canRunIntake;

    public IntakeSubsystem() {
        intakeMotor.configure(Configs.IntakeConfig.intakeConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    @Override
    public void periodic() {
        // Limit Switch Checker
        if (!limitSwitch.get()) {
            canRunIntake = false;  
        } else {canRunIntake = true;}

    }
    
    public void runIntake() {
        if (canRunIntake) {
            intakeMotor.set(ElevatorConstants.Intake.intakeSpeed);
        } else {
            stopIntake();
        }
    }
    
    public void reverseIntake() {
        intakeMotor.set(-ElevatorConstants.Intake.reverseIntakeSpeed);
    }

    public void stopIntake() {
        intakeMotor.set(0);
    }

    public Command intakeOut() {
        // return new RunCommand(() -> {
        //     System.out.println("START INTAKE AUTO");
        //     reverseIntake();
        //     try {
        //         System.out.println("TIMER START");
        //         Thread.sleep(1000);
        //         System.out.println("TIMER END");
        //     } catch (InterruptedException e) {
        //         e.printStackTrace();
        //     }
        //     stopIntake();
        //     System.out.println("END INTAKE AUTO");
        // }, getInstance())
        // // .withTimeout(3)
        // ;
        return new InstantCommand(() -> {
            // NOTE: WILL NEED TO SET TIMER TO RUN INTAKE BASED ON TIME
            reverseIntake();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            stopIntake();
        }, getInstance());
    }
}
