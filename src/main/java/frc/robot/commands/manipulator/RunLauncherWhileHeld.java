package frc.robot.commands.manipulator;


import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Manipulator;


public class RunLauncherWhileHeld extends CommandBase {
	// ----------------------------------------------------------
	// Resource

	private final Manipulator m_manipulator;

	// ----------------------------------------------------------
	// Constructor

	public RunLauncherWhileHeld(Manipulator manipulator) {	
		m_manipulator = manipulator; 
	}

	// ----------------------------------------------------------
	// Scheduler methods

	@Override
	public void initialize() {
		m_manipulator.runLauncher();
	}

	@Override
	public void end(boolean interrupted) {
		m_manipulator.idleLauncher();
	}	

	@Override
	public boolean isFinished() {
		return false;
	}
}
