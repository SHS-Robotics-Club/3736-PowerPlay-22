package org.firstinspires.ftc.teamcode.b_commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.c_subsystems.DriveSubsystem;

import java.util.function.DoubleSupplier;

public class DriveCommand extends CommandBase {
	private final DriveSubsystem driveSubsystem;
	private final DoubleSupplier forward, turn;
	private double multiplier;

	/**
	 * @param driveSubsystem The drive subsystem this command wil run on.
	 * @param forward        The control input for driving forwards/backwards.
	 * @param turn           The control input for turning.
	 * @param multiplier     A multiplier for bot speed.
	 */
	public DriveCommand(DriveSubsystem driveSubsystem, DoubleSupplier forward, DoubleSupplier turn, double multiplier) {
		this.driveSubsystem = driveSubsystem;
		this.forward        = forward;
		this.turn           = turn;
		this.multiplier     = multiplier;

		addRequirements(driveSubsystem);
	}

	/**
	 * @param driveSubsystem The drive subsystem this command wil run on.
	 * @param forward        The control input for driving forwards/backwards.
	 * @param turn           The control input for turning.
	 */
	public DriveCommand(DriveSubsystem driveSubsystem, DoubleSupplier forward, DoubleSupplier turn) {
		this.driveSubsystem = driveSubsystem;
		this.forward        = forward;
		this.turn           = turn;
		multiplier          = 1.0;

		addRequirements(driveSubsystem);
	}

	@Override
	public void execute() {
		driveSubsystem.drive(forward.getAsDouble() * 0.9 * multiplier, turn.getAsDouble() * 0.8 * multiplier);
	}
}
