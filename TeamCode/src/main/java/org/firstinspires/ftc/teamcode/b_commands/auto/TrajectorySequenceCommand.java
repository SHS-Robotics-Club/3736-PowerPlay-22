package org.firstinspires.ftc.teamcode.b_commands.auto;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.c_subsystems.auto.TankSubsystem;
import org.firstinspires.ftc.teamcode.d_roadrunner.trajectorysequence.TrajectorySequence;

public class TrajectorySequenceCommand extends CommandBase {

	private final TankSubsystem      drive;
	private final TrajectorySequence trajectory;

	public TrajectorySequenceCommand(TankSubsystem drive, TrajectorySequence trajectory) {
		this.drive      = drive;
		this.trajectory = trajectory;

		addRequirements(drive);
	}

	@Override
	public void initialize() {
		drive.followTrajectorySequence(trajectory);
	}

	@Override
	public void execute() {
		drive.update();
	}

	@Override
	public void end(boolean interrupted) {
		if (interrupted) {
			drive.stop();
		}
	}

	@Override
	public boolean isFinished() {
		return Thread.currentThread().isInterrupted() || !drive.isBusy();
	}

}
