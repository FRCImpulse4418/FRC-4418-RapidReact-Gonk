package frc.robot.subsystems;


import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;


public class Manipulator extends SubsystemBase {
	// ----------------------------------------------------------
	// Resources
	

	private final WPI_TalonSRX m_indexerMotor = new WPI_TalonSRX(Constants.Manipulator.CAN_ID.kIndexer);
	private final WPI_TalonFX m_launcherMotor = new WPI_TalonFX(Constants.Manipulator.CAN_ID.kLauncher);

	// override booleans for overriding normal, continuous behavior for the indexer
	private boolean m_indexerIsLocked = false;

	private double
		m_launcherSetRPM = 0.,
		m_indexerSetPercent = 0.;


	// ----------------------------------------------------------
	// Constructor


	public Manipulator() {
		// ----------------------------------------------------------
		// Indexer motor configuration

		m_indexerMotor.configFactoryDefault();
		m_indexerMotor.setInverted(true);
		m_indexerMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, Constants.Manipulator.kIndexerPidIdx, Constants.Manipulator.kTimeoutMs);

		// ----------------------------------------------------------
		// Launcher motor configuration

		m_launcherMotor.configFactoryDefault();
		m_launcherMotor.setInverted(true);
		m_launcherMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, Constants.Manipulator.kLauncherPidIdx, Constants.Manipulator.kTimeoutMs);

		// ----------------------------------------------------------
		// Final setup
		
		configurePIDs();
	}


	// ----------------------------------------------------------
	// Constants-reconfiguration methods


	public Manipulator configurePIDs() {
		m_launcherMotor.config_kP(Constants.Manipulator.kLauncherPidIdx, Constants.Manipulator.kLauncherRPMGains.kP);
		m_launcherMotor.config_kI(Constants.Manipulator.kLauncherPidIdx, Constants.Manipulator.kLauncherRPMGains.kI);
        m_launcherMotor.config_kD(Constants.Manipulator.kLauncherPidIdx, Constants.Manipulator.kLauncherRPMGains.kD);
		m_launcherMotor.config_kF(Constants.Manipulator.kLauncherPidIdx, Constants.Manipulator.kLauncherRPMGains.kF);

		m_indexerMotor.config_kP(Constants.Manipulator.kIndexerPidIdx, Constants.Manipulator.kIndexerRPMGains.kP);
		m_indexerMotor.config_kI(Constants.Manipulator.kIndexerPidIdx, Constants.Manipulator.kIndexerRPMGains.kI);
        m_indexerMotor.config_kD(Constants.Manipulator.kIndexerPidIdx, Constants.Manipulator.kIndexerRPMGains.kD);
		// m_indexerMotor.config_kF(Constants.Manipulator.kIndexerPidIdx, Constants.Manipulator.kIndexerRPMGains.kF);
		return this;
	}


	// ----------------------------------------------------------
	// Indexer motor
	

	public boolean indexerIsRunning() {
		return m_indexerSetPercent == Constants.Manipulator.kIndexerPercent;
	}

	// public int getIndexerRPM() {
	// 	return (int) (m_indexerMotor.getSelectedSensorVelocity(Constants.Manipulator.kIndexerPidIdx) / Constants.Manipulator.kIndexerOutputRPMToInputTicksPer100ms);
	// }

	private boolean withinIndexerRPMRange(int rpm) {
		return (rpm >= Constants.Manipulator.kIndexerMinRPM && rpm <= Constants.Manipulator.kIndexerMaxRPM);
	}

	// public void setIndexerRPM(int rpm) {
	// 	if (withinIndexerRPMRange(rpm)) {
	// 		m_indexerMotor.set(ControlMode.Velocity, rpm * Constants.Manipulator.kIndexerOutputRPMToInputTicksPer100ms);
	// 	}
	// }

	public void setIndexerPercent(double percent) {
		if (withinIndexerRPMRange((int) (Constants.Falcon500.kMaxRPM * percent))) {
			m_indexerMotor.set(ControlMode.PercentOutput, percent);
			m_indexerSetPercent = percent;
		}
	}

	public void runIndexer() {
		setIndexerPercent(Constants.Manipulator.kIndexerPercent);
	}

	public void runReverseIndexer() {
		setIndexerPercent(Constants.Manipulator.kReverseIndexerPercent);
	}

	// Motor locks should only be used by commands

	public void stopIndexer() {
		setIndexerPercent(0.);
	}

	public void lockIndexer() {
		m_indexerIsLocked = true;
	}

	public void unlockIndexer() {
		m_indexerIsLocked = false;
	}

	public boolean indexerIsLocked() {
		return m_indexerIsLocked;
	}


	// ----------------------------------------------------------
	// Launcher motor


	public boolean launcherIsIdling() {
		return m_launcherSetRPM == Constants.Manipulator.kLauncherIdleRPM;
	}

	public boolean launcherIsFiring() {
		return m_launcherSetRPM == Constants.Manipulator.kLauncherFiringRPM;
	}

	public int getLauncherRPM() {
		return (int) (m_launcherMotor.getSelectedSensorVelocity(Constants.Manipulator.kLauncherPidIdx) / Constants.Manipulator.kLauncherOutputRPMToInputTicksPer100ms);
	}

	private boolean withinLauncherRPMRange(int rpm) {
		return rpm >= Constants.Manipulator.kLauncherMinRPM && rpm <= Constants.Manipulator.kLauncherMaxRPM;
	}

	public void setLauncherRPM(int rpm) {
		if (withinLauncherRPMRange(rpm)) {
			m_launcherMotor.set(ControlMode.Velocity, rpm * Constants.Manipulator.kLauncherOutputRPMToInputTicksPer100ms);
			m_launcherSetRPM = rpm;
		}
	}

	// sets the launcher to it's idle speed
	public void idleLauncher() {
		setLauncherRPM(Constants.Manipulator.kLauncherIdleRPM);
	}

	public void runLauncher() {
		setLauncherRPM(Constants.Manipulator.kLauncherFiringRPM);
	}

	public void stopLauncher() {
		setLauncherRPM(0);
	}
}