package frc.robot.joystickcontrols.singlejoystickcontrols;


import edu.wpi.first.wpilibj.Joystick;

import frc.robot.Constants;
import frc.robot.commands.climber.ExtendClimberWhileHeld;
import frc.robot.commands.climber.LowerClimberWhileHeld;
import frc.robot.commands.drivetrain.DriveStraightWhileHeld;
import frc.robot.commands.drivetrain.ReverseDrivetrain;
import frc.robot.commands.drivetrain.DriveStraightWhileHeld.DriveStraightDirection;
import frc.robot.commands.intake.ExtendIntakeArm;
import frc.robot.commands.intake.RetractIntakeArm;
import frc.robot.commands.intake.RunFeederAndIndexerWhileHeld;
import frc.robot.commands.intake.RunFeederWhileHeld;
import frc.robot.commands.intake.ToggleIndexBall;
import frc.robot.commands.manipulator.RunIndexer;
import frc.robot.commands.manipulator.RunLauncherWhileHeld;
import frc.robot.joystickcontrols.JoystickControls;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Lights;
import frc.robot.subsystems.Manipulator;


public abstract class SingleJoystickControls extends JoystickControls {
    // ----------------------------------------------------------
    // Joysticks
    
    protected final Joystick m_primaryJoystick;

    // ----------------------------------------------------------
    // Joystick helpers

    @Override
    public boolean isActivelyDriving() {
        return m_primaryJoystick.getMagnitude() > Math.sqrt(DEADBAND * 2.);
    }

    @Override
    public int getPrimaryJoystickPort() {
        return m_primaryJoystick.getPort();
    }

    @Override
    public int getSecondaryJoystickPort() {
        // it's weird, but this obviously erroneous implementation is needed by the driver to override, since the base JoystickControls class is the common type used for our polymorphic code
        return -1;
    }

    // ----------------------------------------------------------
    // Constructor

    public SingleJoystickControls(Joystick primaryJoystick, Drivetrain drivetrain, Intake intake, Manipulator manipulator, Climber climber, Lights lights) {
        m_primaryJoystick = primaryJoystick;

        // ----------------------------------------------------------
        // Drivetrain

        reverseDrivetrainButton = reverseDrivetrainButton(primaryJoystick);
        if (reverseDrivetrainButton != null) {
            reverseDrivetrainButton.whenPressed(new ReverseDrivetrain(drivetrain, lights));
            reverseDrivetrainButton.whenReleased(new ReverseDrivetrain(drivetrain, lights));
        }

        driveStraightButton = driveStraightButton(primaryJoystick);
        if (driveStraightButton != null) driveStraightButton.whenHeld(new DriveStraightWhileHeld(drivetrain, DriveStraightDirection.FORWARDS, Constants.Drivetrain.kDriveStraightMaxPercent));

        // ----------------------------------------------------------
        // Intake
 
        runFeederDisposalButton = runReverseFeederButton(primaryJoystick);
        if (runFeederDisposalButton != null) runFeederDisposalButton.whenHeld(new RunFeederWhileHeld(intake, true));
        
        runFeederIntakebutton = runFeederButton(primaryJoystick);
        if (runFeederIntakebutton != null) runFeederIntakebutton.whenHeld(new RunFeederAndIndexerWhileHeld(intake, manipulator, false));
        
        toggleFeederButton = toggleFeederButton(primaryJoystick);
        if (toggleFeederButton != null) toggleFeederButton.toggleWhenPressed(new ToggleIndexBall(intake, manipulator));
        
        extendIntakeArmButton = extendIntakeArmButton(primaryJoystick);
        if (extendIntakeArmButton != null) {
            extendIntakeArmButton
                // the boolean second-param specifies if the command should run when the robot is disabled
                .whenPressed(new ExtendIntakeArm(intake))
                .whenReleased(new RetractIntakeArm(intake));
        }

        // ----------------------------------------------------------
        // Manipulator

        runIndexerButton = runIndexerButton(primaryJoystick);
        if (runIndexerButton != null) runIndexerButton.whenHeld(new RunIndexer(manipulator));
        
        runLauncherButton = runLauncherButton(primaryJoystick);
        if (runLauncherButton != null) runLauncherButton.whenHeld(new RunLauncherWhileHeld(manipulator));

        // ----------------------------------------------------------
        // Climber

        extendClimberButton = extendClimberButton(primaryJoystick);
        if (extendClimberButton != null) extendClimberButton.whenHeld(new ExtendClimberWhileHeld(climber));

        lowerClimberButton = lowerClimberButton(primaryJoystick);
        if (lowerClimberButton != null) lowerClimberButton.whenHeld(new LowerClimberWhileHeld(climber));
    }
}