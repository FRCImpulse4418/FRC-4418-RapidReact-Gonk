package frc.robot.joystickcontrols.dualjoystickcontrols.dualtank;


import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.joystickcontrols.IO.X3D;
import frc.robot.joystickcontrols.dualjoystickcontrols.DualJoystickControls;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Lights;
import frc.robot.subsystems.Manipulator;


public class DriverX3DDualTankControls extends DualJoystickControls {
    // ----------------------------------------------------------
    // Joystick helpers

    @Override
    protected double deadband() {
        return X3D.JOYSTICK_DEADBAND;
    }

    // ----------------------------------------------------------
    // Drivetrain axes

    @Override
    public double getCurvatureForwardAxis() {
        return 0.;
    }

    @Override
    public double getCurvatureRotationAxis() {
        return 0.;
    }
    

    @Override
    public double getArcadeForwardAxis() {
        return 0.;
    }

    @Override
    public double getArcadeTurnAxis() {
        return 0.;
    }

    
    @Override
    public double getTankLeftAxis() {
        return m_primaryJoystick.getRawAxis(X3D.PITCH_AXIS);
    }

    @Override
    public double getTankRightAxis() {
        return m_secondaryJoystick.getRawAxis(X3D.PITCH_AXIS);
    }

    // ----------------------------------------------------------
    // Drivetrain buttons

    @Override
    protected JoystickButton reverseDrivetrainButton(Joystick joystick) {
        return new JoystickButton(joystick, X3D.BUTTON_6_ID);
    }

    @Override
    protected JoystickButton driveStraightButton(Joystick joystick) {
        return null;
    }

    // ----------------------------------------------------------
    // Intake axes

    @Override
    public double getReverseFeederAxis() {
        return 0.;
    }

    @Override
    public double getFeederAxis() {
        return 0.;
    }

    // ----------------------------------------------------------
    // Intake buttons

    @Override
    protected JoystickButton runReverseFeederButton(Joystick joystick) {
        return new JoystickButton(joystick, X3D.BUTTON_11_ID);
    }

    @Override
    protected JoystickButton runFeederButton(Joystick joystick) {
        return new JoystickButton(joystick, X3D.BUTTON_12_ID);
    }

    @Override
    protected JoystickButton toggleFeederButton(Joystick joystick) {
        return new JoystickButton(joystick, X3D.BUTTON_4_ID);
    }

    @Override
    protected JoystickButton extendIntakeArmButton(Joystick joystick) {
        return new JoystickButton(joystick, X3D.BUTTON_10_ID);
    }

    // ----------------------------------------------------------
    // Manipulator buttons

    @Override
    protected JoystickButton runIndexerButton(Joystick joystick) {
        return new JoystickButton(joystick, X3D.BUTTON_3_ID);
    }

    @Override
    protected JoystickButton runLauncherButton(Joystick joystick) {
        return new JoystickButton(joystick, X3D.TRIGGER_BUTTON_ID);
    }

    // ----------------------------------------------------------
    // Climber buttons

    @Override
    protected POVButton extendClimberButton(Joystick joystick) {
        return null;
    }

    @Override
    protected POVButton lowerClimberButton(Joystick joystick) {
        return null;
    }

    // ----------------------------------------------------------
    // Constructor

    public DriverX3DDualTankControls(Joystick primaryJoystick, Joystick secondaryJoystick, Drivetrain drivetrain, Intake intake, Manipulator manipulator, Climber climber, Lights lights) {
        super(primaryJoystick, secondaryJoystick, drivetrain, intake, manipulator, climber, lights);

        m_primaryJoystick.setYChannel(X3D.PITCH_AXIS);
        m_secondaryJoystick.setYChannel(X3D.PITCH_AXIS);
    }
}
