package frc.robot.displays.diagnosticsdisplays;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import frc.robot.Constants;
import frc.robot.subsystems.Drivetrain;


public class SlewRateLimiterTuningDisplay extends DiagnosticsDisplay {
	private final Drivetrain m_drivetrain;

    private NetworkTableEntry arcadeDriveForwardLimiterTextView;
	private NetworkTableEntry arcadeDriveTurnLimiterTextView;

	private NetworkTableEntry tankDriveLeftForwardLimiterTextView;
	private NetworkTableEntry tankDriveRightForwardLimiterTextView;

    public SlewRateLimiterTuningDisplay(Drivetrain drivetrain, int width, int height) {
		super(width, height);

		m_drivetrain = drivetrain;
    }

	@Override
	protected DiagnosticsDisplay createEntriesArray() {
		entries = new ArrayList<>(Arrays.asList(
			arcadeDriveForwardLimiterTextView,
			arcadeDriveTurnLimiterTextView,

			tankDriveLeftForwardLimiterTextView
		));
		return this;
	}

	@Override
	protected DiagnosticsDisplay createDisplayAt(int column, int row) {
		{ var slewRateLimiterLayout = diagnosticsTab
			.getLayout("Slew Rate Limiters", BuiltInLayouts.kGrid)
			.withProperties(Map.of("Number of columns", 1, "Number of rows", 2, "Label position", "TOP"))
			.withPosition(column, row)
			.withSize(width, height);

			{ var arcadeDriveLayout = slewRateLimiterLayout
				.getLayout("Arcade Drive", BuiltInLayouts.kGrid)
				.withProperties(Map.of("Number of columns", 1, "Number of rows", 2, "Label position", "TOP"));

				arcadeDriveForwardLimiterTextView = arcadeDriveLayout
					.add("Forward", Constants.Drivetrain.SlewRates.kDefaultArcadeForward)
					.withWidget(BuiltInWidgets.kTextView)
					.getEntry();

				arcadeDriveTurnLimiterTextView = arcadeDriveLayout
					.add("Turn", Constants.Drivetrain.SlewRates.kDefaultArcadeTurn)
					.withWidget(BuiltInWidgets.kTextView)
					.getEntry();
			}

			{ var tankDriveLayout = slewRateLimiterLayout
				.getLayout("Tank Drive", BuiltInLayouts.kGrid)
				.withProperties(Map.of("Number of columns", 1, "Number of rows", 2, "Label position", "TOP"));
			
				tankDriveLeftForwardLimiterTextView = tankDriveLayout
					.add("Left Forward", Constants.Drivetrain.SlewRates.kDefaultTankForward)
					.withWidget(BuiltInWidgets.kTextView)
					.getEntry();

				tankDriveRightForwardLimiterTextView = tankDriveLayout
					.add("Right Forward", Constants.Drivetrain.SlewRates.kDefaultTankForward)
					.withWidget(BuiltInWidgets.kTextView)
					.getEntry();
			}
		}
		return this;
	}

	@Override
	public DiagnosticsDisplay addEntryListeners() {
		{	// Arcade drive
			arcadeDriveForwardLimiterTextView.addListener(event -> {
				m_drivetrain.setArcadeDriveForwardLimiterRate(event.value.getDouble());
			}, EntryListenerFlags.kNew | EntryListenerFlags.kUpdate);
	
			arcadeDriveTurnLimiterTextView.addListener(event -> {
				m_drivetrain.setArcadeDriveTurnLimiterRate(event.value.getDouble());
			}, EntryListenerFlags.kNew | EntryListenerFlags.kUpdate);
		}

		{	// Tank drive
			tankDriveLeftForwardLimiterTextView.addListener(event -> {
				m_drivetrain.setTankDriveLeftForwardLimiterRate(event.value.getDouble());
			}, EntryListenerFlags.kNew | EntryListenerFlags.kUpdate);

			tankDriveRightForwardLimiterTextView.addListener(event -> {
				m_drivetrain.setTankDriveRightForwardLimiterRate(event.value.getDouble());
			}, EntryListenerFlags.kNew | EntryListenerFlags.kUpdate);
		}
		return this;
	}
}