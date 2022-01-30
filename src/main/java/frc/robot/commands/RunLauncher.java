package frc.robot.commands;


import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Manipulator;


public class RunLauncher extends CommandBase {
	// ----------------------------------------------------------
	// Private constants

	private final double LAUNCHER_OUTPUT_PERCENT = 0.7d;
	
	// ----------------------------------------------------------
	// Resources

	private final Manipulator ms;

	// ----------------------------------------------------------
	// Constructor

	public RunLauncher(Manipulator manipulator) {	
		ms = manipulator; 
	}

	// ----------------------------------------------------------
	// Scheduler methods

	@Override
	public void initialize() {

	}

	int counter = 0;

	@Override
	public void execute() {
		SmartDashboard.putNumber("manip", counter++);
		ms
			.setHighMotorPercent(LAUNCHER_OUTPUT_PERCENT)
			.setLowMotorPercent(LAUNCHER_OUTPUT_PERCENT);
	}

	@Override
	public void end(boolean interrupted) {
		ms
			.setLowMotorPercent(0.d)
			.setHighMotorPercent(0.d);
	}	

	@Override
	public boolean isFinished() {
		return false;
	}
}
