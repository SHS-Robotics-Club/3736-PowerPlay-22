package org.firstinspires.ftc.teamcode.opmodes;

import static java.lang.Math.round;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.drivebase.DifferentialDrive;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.text.DecimalFormat;

//@Disabled
@Config
@TeleOp(name = "TeleOp", group = ".")
public class MainTeleOp extends LinearOpMode {
	// TICK TO DEG
	private static final double nr40d2 = (360.0 / 1120.0) / 2;

	// CONFIGURATION
	public static double ARM_SPEED = 0.2; // Max Speed??? [0:1]
	public static double ARM_COEFFICIENT = 0.05; // P controller ???
	public static double ARM_TOLERANCE = 5 / nr40d2; // Allowed maximum error
	public static double ARM_INCREMENT = 10 / nr40d2; // Amount of DEG to increment by

	public static double DRIVE_MULT = 1; // Drive speed multiplier
	public static double TURN_MULT = 0.65; // Turn speed multiplier

	public static double CLAW_OPEN = -0.15; // POS for claw to go to when open [-1:1]
	public static double CLAW_CLOSE = 0.25; // POS for claw to go to when closed [-1:1]

	// NUMBER FORMATS
	private static final DecimalFormat decimalTenths = new DecimalFormat("0.00");
	private static final DecimalFormat doubleDigits = new DecimalFormat("00");

	// RUN-TIME
	ElapsedTime time = new ElapsedTime();

	@Override
	public void runOpMode() {
		if (isStopRequested())
			return;

		// Get Hardware
		final Hardware bot = new Hardware(hardwareMap);

		// Setup Dashboard Telemetry
		FtcDashboard dashboard = FtcDashboard.getInstance();
		telemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry());
		Telemetry dTelemetry = dashboard.getTelemetry();

		// Set Status
		telemetry.addData("!Status", "Initialized");
		telemetry.update();

		// START
		waitForStart();
		time.reset();

		// CLAW VAR
		long lastx = 0; // Time since X last pressed
		boolean clw = false; // Claw toggle state

		while (opModeIsActive()) {
			// GAME-PADS
			GamepadEx gPad1 = new GamepadEx(gamepad1);
			GamepadEx gPad2 = new GamepadEx(gamepad2);

			// DRIVE---------------------------------------------------------------------------------
			// Set Differential drive
			DifferentialDrive difDrive = new DifferentialDrive(bot.leftMotors, bot.rightMotors);

			// Calculate values based off sticks
			double drive = gPad1.getLeftX() * DRIVE_MULT;
			double turn = gPad1.getRightX() * TURN_MULT;

			// Apply values
			difDrive.arcadeDrive(drive, turn);

			// ARM-----------------------------------------------------------------------------------
			// Set settings and such
			bot.arm.set(ARM_SPEED);
			bot.arm.setPositionCoefficient(ARM_COEFFICIENT);
			bot.arm.setPositionTolerance(ARM_TOLERANCE);
			// bot.arm.setDistancePerPulse(1);

			// Increment arm pos based on triggers
			if (gPad1.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0) {
				bot.arm.setTargetPosition((int) (bot.arm.getCurrentPosition() - ARM_INCREMENT));
			} else if (gPad1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0) {
				bot.arm.setTargetPosition((int) (bot.arm.getCurrentPosition() + ARM_INCREMENT));
			} else {
				bot.arm.setTargetPosition(bot.arm.getCurrentPosition());
			}

/*			// Set levels using D-PAD
			if (gPad1.getButton(GamepadKeys.Button.DPAD_DOWN)) {
				//bot.arm.set(0.2);
				bot.arm.setTargetPosition((int) (0 / nr40d2));
			} else if (gPad1.getButton(GamepadKeys.Button.DPAD_RIGHT)) {
				// bot.arm.set(0.13);
				bot.arm.setTargetPosition((int) (65 / nr40d2));
			} else if (gPad1.getButton(GamepadKeys.Button.DPAD_UP)) {
				// bot.arm.set(0.13);
				bot.arm.setTargetPosition((int) (100 / nr40d2));
			} else if (gPad1.getButton(GamepadKeys.Button.DPAD_LEFT)) {
				// bot.arm.set(0.13);
				bot.arm.setTargetPosition((int) (140 / nr40d2));
			}*/

			if (bot.arm.getCurrentPosition() > 170 / nr40d2) {
				bot.arm.setTargetPosition((int) (169.8 / nr40d2));
			}

			// CLAW----------------------------------------------------------------------------------
			// Toggle claw state if last button press not within 500ms
			if (gPad1.getButton(GamepadKeys.Button.X) && System.currentTimeMillis() - lastx > 500) {
				lastx = System.currentTimeMillis();
				clw = !clw;
			}

			// Set open/close values when toggled
			if (clw) {
				bot.claw.set(CLAW_OPEN);
			} else {
				bot.claw.set(CLAW_CLOSE);
			}

			// Misc Things---------------------------------------------------------------------------
			// Calculate Run-Time
			long seconds = round(time.time());
			long t1 = seconds % 60;
			long t2 = seconds / 60;
			long t3 = t2 % 60;
			t2 = t2 / 60;

			// TELEMETRY--------------------------------------------------------------------------------------
			// Driver Station Telemetry
			telemetry.addData("!Status",
					"Run Time: " + doubleDigits.format(t2) + ":" + doubleDigits.format(t3) + ":" + doubleDigits.format(t1)); // Run Time HH:MM:SS
			telemetry.addData("Arm DEG", bot.arm.getCurrentPosition() * nr40d2);


			// Dashboard Specific Telemetry
			dTelemetry.addData("Voltage", decimalTenths.format(bot.volt + " V")); // Voltage

			telemetry.update();
			idle();
		}
	}
}