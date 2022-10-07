package org.firstinspires.ftc.teamcode.b_hardware;

import com.arcrobotics.ftclib.hardware.motors.CRServo;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.arcrobotics.ftclib.hardware.motors.MotorGroup;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;

/*
 * TYPE			NAME			ID		DESCRIPTION
 * ------------------------------------------------------------
 * MOTOR		frontLeft		fL		Front Left Omni
 * MOTOR		frontRight		fR		Front Right Omni
 * MOTOR		backLeft		bL		Back Left Omni
 * MOTOR		backRight		bR		Back Right Omni
 *
 * MOTOR		arm				arm		Virtual Forebear Arm
 * CRSERVO		claw			claw	Da Claw
 */

public class Hardware {
	// MISC
	public double volt = Double.POSITIVE_INFINITY; // Bot Voltage
	public static double kp = 0, ki = 0, kd = 0; // PID
	
	// DEFINE DEVICES
	public MotorEx frontLeft, frontRight, backLeft, backRight;
	public MotorGroup leftMotors, rightMotors;
	public MotorEx arm;
	public CRServo claw;

	public Hardware(HardwareMap hwMap) {

		// Drive Motors
		frontLeft = new MotorEx(hwMap, "fL");
		frontRight = new MotorEx(hwMap, "fR");
		backLeft = new MotorEx(hwMap, "bL");
		backRight = new MotorEx(hwMap, "bR");

		leftMotors = new MotorGroup(frontLeft, backLeft);
		rightMotors = new MotorGroup(frontRight, backRight);

		frontLeft.resetEncoder();
		frontLeft.setRunMode(MotorEx.RunMode.VelocityControl);
		frontLeft.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);
		frontLeft.setVeloCoefficients(kp, ki, kd);

		backLeft.resetEncoder();
		backLeft.setRunMode(MotorEx.RunMode.VelocityControl);
		backLeft.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);
		backLeft.setVeloCoefficients(kp, ki, kd);

		frontRight.resetEncoder();
		frontRight.setRunMode(MotorEx.RunMode.VelocityControl);
		frontRight.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);
		frontRight.setVeloCoefficients(kp, ki, kd);

		backRight.resetEncoder();
		backRight.setRunMode(MotorEx.RunMode.VelocityControl);
		backRight.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);
		backRight.setVeloCoefficients(kp, ki, kd);

		// Arm
		arm = new MotorEx(hwMap, "arm");
		arm.resetEncoder();
		arm.setRunMode(MotorEx.RunMode.PositionControl);
		arm.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);

		// Claw
		claw = new CRServo(hwMap, "claw");

		// BOT VOLTAGE
		for (VoltageSensor sensor : hwMap.voltageSensor) {
			double voltage = sensor.getVoltage();
			if (voltage > 0) {
				volt = Math.min(volt, voltage);
			}
		}
	}
}