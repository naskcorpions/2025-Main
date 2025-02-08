package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ExampleSubsystem;

public class ExampleCommand extends Command{
    // The subsystem the command runs on
    private final ExampleSubsystem m_subsystem;
    // Define other required variables here
    // Remember that public variables will be accessible through the whole program, 
    // and private will have to be passed into methods in order to be used.

    public ExampleCommand(ExampleSubsystem subsystem) {
        // Brings in private Variable
        m_subsystem = subsystem;
        addRequirements(m_subsystem);
    }

    @Override
    public void initialize() {
        /* RUNS ONCE ON INIT */
        // Put any initialization here
    }

    @Override
    public void execute() {
        /* RUNS PEREODICALLY */
        // Put any commands that you want to run pereodically here
    }

    @Override
    public boolean isFinished() {
        /* Checked with the command ends to see it it should run again
         * When TRUE returned, it will move on to the end command
         */
        return false; // Modify with logic
    }

    @Override
    public void end(boolean interupted) {
        /* RUNS LAST */
        // Put anything in here that is required to finish a command
    }


}
