package org.firstinspires.ftc.teamcode.c_subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.ServoEx;

public class ClawSubsystem extends SubsystemBase {
	private final ServoEx claw;
	private       boolean isOpen;

	/**
	 * @param claw The claw servo object.
	 */
	public ClawSubsystem(ServoEx claw) {
		this.claw = claw;
		isOpen    = true;
	}

	public void toggle() {
		isOpen = !isOpen;
	}

	public boolean isOpen() {
		return isOpen;
	}

	public void open() {
		claw.turnToAngle(-30);
		isOpen = true;
	}

	public void close() {
		claw.turnToAngle(30);
		isOpen = false;
	}
}