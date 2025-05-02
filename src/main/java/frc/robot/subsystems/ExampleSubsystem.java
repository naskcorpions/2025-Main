// INFO: ROBOT IMPORTS
package frc.robot.subsystems;
// INFO: WPILIB IMPORTS
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ExampleSubsystem extends SubsystemBase{
    // Create instance
    public static ExampleSubsystem instance;
    public static ExampleSubsystem getInstance() {
        if (instance == null) {
            instance = new ExampleSubsystem();
        }
        return instance;
    }
}
