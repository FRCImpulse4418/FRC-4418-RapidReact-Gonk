package frc.robot.commands.autonomous;


import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.commands.drivetrain.DriveStraightForDistance;
import frc.robot.commands.drivetrain.DriveStraightWhileHeld.DriveStraightDirection;
import frc.robot.commands.intake.ExtendIntakeArm;
import frc.robot.commands.intake.RetractIntakeArm;
import frc.robot.commands.intake.RunFeederAndIndexer;
import frc.robot.commands.intake.StopFeederAndIndexer;
import frc.robot.commands.manipulator.LaunchOneBall;
import frc.robot.commands.manipulator.LaunchTwoBalls;
import frc.robot.subsystems.Autonomous;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Manipulator;


public class Wait_LH_TC_Wait_TC_LH extends SequentialCommandGroup {
	public Wait_LH_TC_Wait_TC_LH(Drivetrain drivetrain, Intake intake, Manipulator manipulator) {
		super(
			new WaitFor(Autonomous.getStartDelaySeconds()),
			new LaunchOneBall(manipulator),

			// We have now fired the pre-loaded ball
			
			new ExtendIntakeArm(intake),
			new RunFeederAndIndexer(intake, manipulator, false),
			new DriveStraightForDistance(drivetrain, DriveStraightDirection.FORWARDS),

			// At this point, we have now just collected the second ball

			new StopFeederAndIndexer(intake, manipulator, false),
			new WaitFor(Autonomous.getTarmacReturnDelaySeconds()),

			new RunFeederAndIndexer(intake, manipulator, true),
			new BackTailBallTrajectory(drivetrain, false),

			// At this point, we have now just collected the third ball

			new StopFeederAndIndexer(intake, manipulator, false),
			new RetractIntakeArm(intake),
			new BackTailBallTrajectory(drivetrain, true),
			new DriveStraightForDistance(drivetrain, DriveStraightDirection.BACKWARDS),
			
			new LaunchTwoBalls(manipulator)

			// At this point, we have now just fired the second and third ball
		);
	}
}