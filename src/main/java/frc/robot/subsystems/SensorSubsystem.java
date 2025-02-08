package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SensorSubsystem extends SubsystemBase {
    DigitalInput lazer_Input = new DigitalInput(2);
    DigitalInput switch_Input = new DigitalInput(9);
    DigitalInput button_Input = new DigitalInput(6);
    
    public boolean lazer_input() {
        return lazer_Input.get();
    }

    public boolean switch_input() {
        return switch_Input.get();
    }

    public boolean button_input() {
        return button_Input.get();
    }

    AnalogPotentiometer dial_Input = new AnalogPotentiometer(1, 1, 0);
    AnalogPotentiometer sonic_Input = new AnalogPotentiometer(2, 255, 0);

    public double dial_input() {
        return dial_Input.get();
    }

    public double sonic_input() {
        return sonic_Input.get();
    }
    
}
