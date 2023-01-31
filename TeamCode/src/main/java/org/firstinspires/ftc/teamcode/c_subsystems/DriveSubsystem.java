package org.firstinspires.ftc.teamcode.c_subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.drivebase.DifferentialDrive;
import com.arcrobotics.ftclib.hardware.motors.MotorGroup;

public class DriveSubsystem extends SubsystemBase {
	private MotorGroup leftMotors, rightMotors;
	private DifferentialDrive differentialDrive;

	/**
	 * @param leftMotors  The Front Left drive motor object.
	 * @param rightMotors The Front Right drive motor object.
	 */
	public DriveSubsystem(MotorGroup leftMotors, MotorGroup rightMotors) {
		this.leftMotors  = leftMotors;
		this.rightMotors = rightMotors;

		differentialDrive = new DifferentialDrive(leftMotors, rightMotors);
	}

	/**
	 * Input for drive speeds
	 *
	 * @param forwardSpeed The speed for the bot to drive back/forth.
	 * @param turnSpeed    The speed for the bot to turn.
	 */
	public void drive(double forwardSpeed, double turnSpeed) {
		differentialDrive.arcadeDrive(forwardSpeed, turnSpeed, true);
	}
}