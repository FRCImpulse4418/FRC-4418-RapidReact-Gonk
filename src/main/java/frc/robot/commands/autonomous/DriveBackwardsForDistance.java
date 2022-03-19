package frc.robot.commands.autonomous;


import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.commands.drivetrain.DriveStraightForDistance;
import frc.robot.commands.drivetrain.DriveStraight.DriveStraightDirection;
import frc.robot.subsystems.Drivetrain;


// TODO: !!!Remove this commmand once debugging is done

public class DriveBackwardsForDistance extends SequentialCommandGroup {
	public DriveBackwardsForDistance(Drivetrain drivetrain) {
		super(
			new DriveStraightForDistance(drivetrain, DriveStraightDirection.BACKWARDS)
		);
	}
}