package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Dashboard extends SubsystemBase {
    
    @Override
    public void periodic() {
        SmartDashboard.putNumber("Batt Volts", RobotController.getBatteryVoltage());
    }

}
