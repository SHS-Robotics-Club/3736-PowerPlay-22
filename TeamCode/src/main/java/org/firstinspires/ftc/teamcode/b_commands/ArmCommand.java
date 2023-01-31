package org.firstinspires.ftc.teamcode.b_commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.c_subsystems.ArmSubsystem;


public class ArmCommand extends CommandBase {
	ArmSubsystem arm;
	ArmStates    armStates;
	ArmLevels    armLevels;

	public ArmCommand(ArmSubsystem armSubsystem) {
		arm = armSubsystem;
		addRequirements(armSubsystem);
	}

	@Override
	public void initialize() {
		armStates = ArmStates.READY;
		armLevels = ArmLevels.FLOOR;
		arm.setTolerance(10);
		arm.setSetPoint(0);
		arm.stop();
	}

	public void setLiftLevels(ArmLevels armLevels) {
		this.armLevels = armLevels;
	}

	@Override
	public void execute() {
		arm.getPosition();
		arm.setSetPoint(armLevels.getLevelPos());
		switch (armStates) {
			case READY:
				if (arm.atSetPoint()) {
					arm.stop();
				}
				if (!arm.atSetPoint()) {
					armStates = ArmStates.MOVING;
				}
			case MOVING:
				if (!arm.atSetPoint()) {
					arm.set(1);
				}
				if (arm.atSetPoint()) {
					armStates = ArmStates.READY;
				}
		}
	}

	enum ArmStates {
		READY, MOVING
	}

	public enum ArmLevels {
		FLOOR(0), LOW(100), MED(500), HIGH(1000);

		private final int levelPos;

		ArmLevels(int levelPos) {
			this.levelPos = levelPos;
		}

		public int getLevelPos() {
			return levelPos;
		}
	}
}
