// INFO: ROBOT IMPORTS
package frc.robot.subsystems;
// INFO: WPILIB IMPORTS
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
// INFO: JAVA IMPORTS
import java.nio.channels.Channel;
import javax.sound.sampled.Port;


// IMPORTANT:
// NOTE: Port numbers will need to be changed
// NOTE: Port number moved to SensorPorts class


public abstract class SensorSubsystem extends SubsystemBase {
    public static DigitalInput lazer_input(Channel port) {
        DigitalInput lazer_Input = new DigitalInput(100);
        return lazer_Input;
    }
    public static DigitalInput switch_input() {
        DigitalInput switch_Input = new DigitalInput(100);
        return switch_Input;
    }
    public static DigitalInput button_input() {
        DigitalInput button_Input = new DigitalInput(100);
        return button_Input;
    }
    public static AnalogPotentiometer dial_input(int port) {
        AnalogPotentiometer dial_Input = new AnalogPotentiometer(port,1.0, 0);
        return dial_Input;
    }
    public AnalogPotentiometer sonic_input(int port) {
        AnalogPotentiometer sonic_Input = new AnalogPotentiometer(port, 255.0, 0);
        return sonic_Input;
    }
    
}