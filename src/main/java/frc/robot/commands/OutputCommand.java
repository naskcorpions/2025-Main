package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.IntakeSubsystem;

public class OutputCommand extends InstantCommand {
    public OutputCommand(IntakeSubsystem intake) {
        super(intake::outputSpin, intake);
    }
}
