package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.arcrobotics.ftclib.hardware.motors.CRServo;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.arcrobotics.ftclib.hardware.motors.MotorGroup;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.List;

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
	// DEFINE DEVICES
	public MotorEx frontLeft, frontRight, backLeft, backRight;
	public MotorGroup leftMotors, rightMotors;
	public MotorEx arm;
	public CRServo claw;

	// MISC DEFINITIONS
	public FtcDashboard     dashboard = FtcDashboard.getInstance(); //FTC Dashboard Instance
	public ElapsedTime      time      = new ElapsedTime(); // Time
	public List<LynxModule> revHubs; //Lynx Module for REV Hubs

	// BOT VARIABLES
	public double volt = Double.POSITIVE_INFINITY; // Bot Voltage

	public Hardware(HardwareMap hardwareMap) {
		// MISC ----------------------------------------------------------------------------------------------------
		// Rev Lynx
		revHubs = hardwareMap.getAll(LynxModule.class);

		// MOTORS ----------------------------------------------------------------------------------------------------
		// Map
		frontLeft  = new MotorEx(hardwareMap, "fL");
		frontRight = new MotorEx(hardwareMap, "fR");
		backLeft   = new MotorEx(hardwareMap, "bL");
		backRight  = new MotorEx(hardwareMap, "bR");

		arm = new MotorEx(hardwareMap, "arm");

		// Group drive motors
		leftMotors  = new MotorGroup(frontLeft, backLeft);
		rightMotors = new MotorGroup(frontRight, backRight);

		// Reset encoders
		frontLeft.resetEncoder();
		frontRight.resetEncoder();
		backLeft.resetEncoder();
		backRight.resetEncoder();

		arm.resetEncoder();

		// Set RunMode for motors (RawPower, VelocityControl, PositionControl)
		frontLeft.setRunMode(MotorEx.RunMode.VelocityControl);
		frontRight.setRunMode(MotorEx.RunMode.VelocityControl);
		backLeft.setRunMode(MotorEx.RunMode.VelocityControl);
		backRight.setRunMode(MotorEx.RunMode.VelocityControl);

		arm.setRunMode(MotorEx.RunMode.PositionControl);

		// Brake when zero power
		frontLeft.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);
		frontRight.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);
		backLeft.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);
		backRight.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);

		arm.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);

		// SERVOS ----------------------------------------------------------------------------------------------------
		// Map
		claw = new CRServo(hardwareMap, "claw");

		// Default POS
		claw.setTargetPosition(0);


		// VOLTAGE ----------------------------------------------------------------------------------------------------
		for (VoltageSensor sensor : hardwareMap.voltageSensor) {
			double voltage = sensor.getVoltage();
			if (voltage > 0) {
				volt = Math.min(volt, voltage);
			}
		}
	}
}