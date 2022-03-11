package frc.robot.joystickcontrols.dualjoystickcontrols;


import edu.wpi.first.wpilibj.Joystick;

import frc.robot.Constants;
import frc.robot.commands.climber.ReleaseClimberPins;
import frc.robot.commands.drivetrain.DriveStraight;
import frc.robot.commands.drivetrain.ReverseDrivetrain;
import frc.robot.commands.drivetrain.DriveStraight.DriveStraightDirection;
import frc.robot.commands.intake.ExtendIntakeArm;
import frc.robot.commands.intake.RetractIntakeArm;
import frc.robot.commands.intake.RunFeederAndIndexerWhileHeld;
import frc.robot.commands.intake.RunFeederWhileHeld;
import frc.robot.commands.intake.ToggleIndexBall;
import frc.robot.commands.manipulator.RunIndexer;
import frc.robot.commands.manipulator.RunLauncher;
import frc.robot.joystickcontrols.JoystickControls;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Manipulator;


public abstract class DualJoystickControls extends JoystickControls {
    // ----------------------------------------------------------
    // Joysticks

    protected final Joystick m_primaryJoystick;
    protected final Joystick m_secondaryJoystick;

    // ----------------------------------------------------------
    // Joystick helpers

    @Override
    public boolean isActivelyDriving() {
        return (Math.abs(m_primaryJoystick.getY()) + Math.abs(m_secondaryJoystick.getY())) / 2. > (DEADBAND * 2.);
    }

    @Override
    public int getPrimaryJoystickPort() {
        return m_primaryJoystick.getPort();
    }

    @Override
    public int getSecondaryJoystickPort() {
        return m_secondaryJoystick.getPort();
    }

    // ----------------------------------------------------------
    // Constructor

    public DualJoystickControls(Joystick primaryJoystick, Joystick secondaryJoystick, Drivetrain drivetrain, Intake intake, Manipulator manipulator, Climber climber) {
        m_primaryJoystick = primaryJoystick;
        m_secondaryJoystick = secondaryJoystick;
        
        // ----------------------------------------------------------
        // Drivetrain

        reverseDrivetrainButton = reverseDrivetrainButton(primaryJoystick);
        if (reverseDrivetrainButton != null) {
            reverseDrivetrainButton
                .whenPressed(new ReverseDrivetrain(drivetrain))
                .whenReleased(new ReverseDrivetrain(drivetrain));
        }

        // ----------------------------------------------------------
        // Intake

        runFeederDisposalButton = runReverseFeederButton(secondaryJoystick);
        if (runFeederDisposalButton != null) runFeederDisposalButton.whenHeld(new RunFeederWhileHeld(intake, true));
        runFeederIntakebutton = runFeederButton(secondaryJoystick);
        if (runFeederIntakebutton != null) runFeederIntakebutton.whenHeld(new RunFeederAndIndexerWhileHeld(intake, manipulator, false));
        toggleFeederButton = toggleFeederButton(secondaryJoystick);
        if (toggleFeederButton != null) toggleFeederButton.toggleWhenPressed(new ToggleIndexBall(intake, manipulator));
        extendIntakeArmButton = extendIntakeArmButton(primaryJoystick);
        if (extendIntakeArmButton != null) {
            extendIntakeArmButton
                // the boolean second-param specifies if the command should run when the robot is disabled
                .whenPressed(new ExtendIntakeArm(intake, false))
                .whenReleased(new RetractIntakeArm(intake, false));
        }
        

        // ----------------------------------------------------------
        // Manipulator
        
        runIndexerButton = runIndexerButton(primaryJoystick);
        if (runIndexerButton != null) runIndexerButton.whenHeld(new RunIndexer(manipulator));
        runLauncherButton = runLauncherButton(primaryJoystick);
        if (runLauncherButton != null) runLauncherButton.whenHeld(new RunLauncher(manipulator));

        // ----------------------------------------------------------
        // Climber

        toggleClimberPinsButton = toggleClimberPinsButton(primaryJoystick);
        if (toggleClimberPinsButton != null) toggleClimberPinsButton.whenPressed(new ReleaseClimberPins(climber));
        driveStraightPOVButton = driveStraightPOVButton(primaryJoystick);
        if (driveStraightPOVButton != null) driveStraightPOVButton.whenHeld(new DriveStraight(drivetrain, DriveStraightDirection.FORWARDS, Constants.Climber.kDriveStraightMPS));
        driveStraightJoystickButton = driveStraightJoystickButton(primaryJoystick);
        if (driveStraightJoystickButton != null) driveStraightJoystickButton.whenHeld(new DriveStraight(drivetrain, DriveStraightDirection.FORWARDS, Constants.Climber.kDriveStraightMPS));
    }
}