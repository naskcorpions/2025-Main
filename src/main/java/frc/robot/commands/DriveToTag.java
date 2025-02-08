package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.VisionSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

public class DriveToTag extends Command {
    private DriveSubsystem m_driveSubsystem;
    private VisionSubsystem m_vision;

    public DriveToTag(VisionSubsystem m_vision, DriveSubsystem m_driveSubsystem) {
        this.m_vision = m_vision;
        this.m_driveSubsystem = m_driveSubsystem;
        addRequirements(m_vision, m_driveSubsystem);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {

    }






}
