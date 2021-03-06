package frc.robot.displays.vision;


import java.util.Map;

import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;

import frc.robot.Constants;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.Vision.Camera;


public class CamerasDisplay extends VisionDisplay {
	private final Vision m_vision;

	private NetworkTableEntry
		enableFrontCameraToggleSwitch,
		enableBackCameraToggleSwitch,
		enableInnerCameraToggleSwitch;

    public CamerasDisplay(Vision vision, int width, int height) {
		super(width, height);

		m_vision = vision;
    }

	@Override
	protected VisionDisplay createDisplayAt(int column, int row) {
		{ var layout = tab
			.getLayout("Cameras", BuiltInLayouts.kGrid)
			.withProperties(Map.of("Number of columns", Constants.Vision.kNumCameras, "Number of rows", 1, "Label position", "TOP"))
			.withPosition(column, row)
			.withSize(width, height);

			// Front
			{ var frontCamLayout = layout
				.getLayout("Front", BuiltInLayouts.kGrid)
				.withProperties(Map.of("Number of columns", 1, "Number of rows", 2, "Label position", "HIDDEN"));
				
				if (Constants.Vision.kUsingFrontCamera) {
					frontCamLayout
						.add("Camera", m_vision.getVideoSource(Camera.FRONT))
						.withWidget(BuiltInWidgets.kCameraStream);
					
					enableFrontCameraToggleSwitch = frontCamLayout
						.add("Switch", true)
						.withWidget(BuiltInWidgets.kToggleSwitch)
						.getEntry();
				}
			}

			// Back
			{ var backCamLayout = layout
				.getLayout("Back", BuiltInLayouts.kGrid)
				.withProperties(Map.of("Number of columns", 1, "Number of rows", 2, "Label position", "HIDDEN"));
				
				if (Constants.Vision.kUsingBackCamera) {
					backCamLayout
						.add("Camera", m_vision.getVideoSource(Camera.BACK))
						.withWidget(BuiltInWidgets.kCameraStream);
					
					enableBackCameraToggleSwitch = backCamLayout
						.add("Switch", true)
						.withWidget(BuiltInWidgets.kToggleSwitch)
						.getEntry();
				}
			}

			// Inner
			{ var backCamLayout = layout
				.getLayout("Inner", BuiltInLayouts.kGrid)
				.withProperties(Map.of("Number of columns", 1, "Number of rows", 2, "Label position", "HIDDEN"));
				
				if (Constants.Vision.kUsingInnerCamera) {
					backCamLayout
						.add("Camera", m_vision.getVideoSource(Camera.INNER))
						.withWidget(BuiltInWidgets.kCameraStream);
					
					enableInnerCameraToggleSwitch = backCamLayout
						.add("Switch", true)
						.withWidget(BuiltInWidgets.kToggleSwitch)
						.getEntry();
				}
			}
		}
		return this;
	}

	@Override
	public void addEntryListeners() {
		{ // Front
			if (Constants.Vision.kUsingFrontCamera) {
				enableFrontCameraToggleSwitch.addListener(event -> {
					m_vision.toggleCameraStream(Camera.FRONT, event.value.getBoolean());
				}, EntryListenerFlags.kNew | EntryListenerFlags.kUpdate);
			}
		}

		{ // Back
			if (Constants.Vision.kUsingBackCamera) {
				enableBackCameraToggleSwitch.addListener(event -> {
					m_vision.toggleCameraStream(Camera.BACK, event.value.getBoolean());
				}, EntryListenerFlags.kNew | EntryListenerFlags.kUpdate);
			}
		}

		{ // Inner
			if (Constants.Vision.kUsingInnerCamera) {
				enableInnerCameraToggleSwitch.addListener(event -> {
					m_vision.toggleCameraStream(Camera.INNER, event.value.getBoolean());
				}, EntryListenerFlags.kNew | EntryListenerFlags.kUpdate);
			}
		}
	}
}
