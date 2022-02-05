package frc.robot.commands;


import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Intake;


public class RunFeeder extends CommandBase {
	// ----------------------------------------------------------
	// Resource

	private final Intake m_intake;

	// ----------------------------------------------------------
	// Constructor

	public RunFeeder(Intake intake) {
		this.m_intake = intake;
	}

	// ----------------------------------------------------------
	// Scheduler methods

	@Override
	public void initialize() {
		m_intake.runFeeder();
	}

	@Override
	public void execute() {
		
	}

	@Override
	public void end(boolean interrupted) {
		m_intake.stopFeeder();
	}

	@Override
	public boolean isFinished() {
		return false;
	}
}