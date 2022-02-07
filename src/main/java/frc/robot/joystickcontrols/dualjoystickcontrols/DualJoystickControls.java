package frc.robot.joystickcontrols.dualjoystickcontrols;


import edu.wpi.first.wpilibj.Joystick;
import frc.robot.commands.drivetrain.DriveStraightWhileHeld;
import frc.robot.commands.drivetrain.ReverseDrivetrainWhileHeld;
import frc.robot.commands.intake.RunFeeder;
import frc.robot.commands.intake.RunReverseFeeder;
import frc.robot.commands.intake.ToggleFeeder;
import frc.robot.commands.manipulator.RunIndexer;
import frc.robot.commands.manipulator.RunLauncher;
import frc.robot.joystickcontrols.JoystickControls;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Manipulator;


public abstract class DualJoystickControls extends JoystickControls {
    // ----------------------------------------------------------
    // Joysticks

    protected final Joystick m_primaryJoystick;
    protected final Joystick m_secondaryJoystick;

    // ----------------------------------------------------------
    // Constructor

    public DualJoystickControls(Joystick primaryJoystick, Joystick secondaryJoystick, Drivetrain drivetrain, Intake intake, Manipulator manipulator) {
        m_primaryJoystick = primaryJoystick;
        m_secondaryJoystick = secondaryJoystick;
        
        reverseDrivetrainButton = reverseDrivetrainButton(primaryJoystick);
        if (reverseDrivetrainButton != null) reverseDrivetrainButton.whenHeld(new ReverseDrivetrainWhileHeld(drivetrain));
        driveStraightPOVButton = driveStraightPOVButton(primaryJoystick);
        if (driveStraightPOVButton != null) driveStraightPOVButton.whenHeld(new DriveStraightWhileHeld(drivetrain));
        driveStraightJoystickButton = driveStraightJoystickButton(primaryJoystick);
        if (driveStraightJoystickButton != null) driveStraightJoystickButton.whenHeld(new DriveStraightWhileHeld(drivetrain));

        runFeederDisposalButton = runFeederDisposalButton(secondaryJoystick);
        if (runFeederDisposalButton != null) runFeederDisposalButton.whenHeld(new RunReverseFeeder(intake));
        runFeederIntakebutton = runFeederButton(secondaryJoystick);
        if (runFeederIntakebutton != null) runFeederIntakebutton.whenHeld(new RunFeeder(intake));
        toggleFeederButton = toggleFeederButton(secondaryJoystick);
        if (toggleFeederButton != null) toggleFeederButton.toggleWhenPressed(new ToggleFeeder(intake));

        runIndexerButton = runIndexerButton(primaryJoystick);
        if (runIndexerButton != null) runIndexerButton.whenHeld(new RunIndexer(manipulator));
        runLauncherButton = runLauncherButton(primaryJoystick);
        if (runLauncherButton != null) runLauncherButton.whenHeld(new RunLauncher(manipulator));
    }
}
