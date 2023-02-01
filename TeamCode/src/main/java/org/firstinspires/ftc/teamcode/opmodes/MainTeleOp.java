package org.firstinspires.ftc.teamcode.opmodes;

import static java.lang.Math.round;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.drivebase.DifferentialDrive;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.text.DecimalFormat;

//@Disabled
@Config
@TeleOp(name = "TeleOp", group = ".")
public class MainTeleOp extends LinearOpMode {
	// TICK TO DEG
	private static final double        nr40           = (360.0 / 1120.0) / 3;
	// NUMBER FORMATS
	private static final DecimalFormat doubleDigits   = new DecimalFormat("00");
	// CONFIGURATION
	public static        double        ARM_kP         = 0.01; // P controller ???
	public static        double        ARM_INCREMENT  = 5; // Amount of DEG to increment by
	public static        double        DRIVE_MULT     = 1; // Drive speed multiplier
	public static        double        TURN_MULT      = 0.65; // Turn speed multiplier
	public static        int[]         LIFT_POSITIONS = {0, 100, 200, 465};

	@Override
	public void runOpMode() {
		if (isStopRequested()) {
			return;
		}

		// Get Devices
		final Hardware bot = new Hardware(hardwareMap);

		// Setup Dashboard Telemetry
		telemetry = new MultipleTelemetry(telemetry, bot.dashboard.getTelemetry());
		Telemetry dTelemetry = bot.dashboard.getTelemetry();

		// Set Status
		telemetry.addData("!Status", "Initialized");
		telemetry.update();

		// START
		waitForStart();
		bot.time.reset();

		// ARM VAR
		double targetPosition = 0; // Init Arm Pos

		// CLAW VAR
		long    lastx = 0; // Time since X last pressed
		boolean clw   = false; // Claw toggle state
		double  clawPosition; // Init Claw Pos

		while (opModeIsActive()) {
			// GAME-PADS
			GamepadEx gPad1 = new GamepadEx(gamepad1);

			// DRIVE---------------------------------------------------------------------------------
			// Set Differential drive
			DifferentialDrive difDrive = new DifferentialDrive(bot.leftMotors, bot.rightMotors);

			// Calculate values based off sticks
			double drive = gPad1.getLeftX() * DRIVE_MULT;
			double turn  = gPad1.getRightX() * TURN_MULT;

			// Apply values
			difDrive.arcadeDrive(drive, turn);

			// ARM-----------------------------------------------------------------------------------
			// Controls
			if (gPad1.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0 && targetPosition >= 0) {
				targetPosition -= ARM_INCREMENT;
			} else if (gPad1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0 && targetPosition <= 465) {
				targetPosition += ARM_INCREMENT;
			}

			if (gPad1.isDown(GamepadKeys.Button.DPAD_DOWN)) {
				targetPosition = LIFT_POSITIONS[0];
			} else if (gPad1.isDown(GamepadKeys.Button.DPAD_LEFT)) {
				targetPosition = LIFT_POSITIONS[1];
			} else if (gPad1.isDown(GamepadKeys.Button.DPAD_UP)) {
				targetPosition = LIFT_POSITIONS[2];
			} else if (gPad1.isDown(GamepadKeys.Button.DPAD_RIGHT)) {
				targetPosition = LIFT_POSITIONS[3];
			}

			// set and get the position coefficient
			bot.arm.setPositionCoefficient(ARM_kP);   //Default: 0.05

			// set the target position
			bot.arm.setTargetPosition((int) targetPosition);  // an integer representing desired tick count

			// set the tolerance
			bot.arm.setPositionTolerance(20);  // allowed maximum error Default: 13.6

			// perform the control loop
			if (!bot.arm.atTargetPosition()) {
				bot.arm.set(0.2);
			} else {
				bot.arm.set(0);
				bot.arm.stopMotor();
			}

			// CLAW----------------------------------------------------------------------------------
			// Toggle claw state if last button press not within 500ms
			if (gPad1.getButton(GamepadKeys.Button.X) && System.currentTimeMillis() - lastx > 500) {
				lastx = System.currentTimeMillis();
				clw   = !clw;
			}

			// Set open/close values when toggled
			if (clw) {
				clawPosition = 30;
			} else {
				clawPosition = -30;
			}

			bot.claw.turnToAngle(clawPosition);

			// Misc Things---------------------------------------------------------------------------
			// Calculate Run-Time
			long seconds = round(bot.time.time());
			long t1      = seconds % 60;
			long t2      = seconds / 60;
			long t3      = t2 % 60;
			t2 = t2 / 60;

			// TELEMETRY--------------------------------------------------------------------------------------
			// Driver Station Telemetry
			telemetry.addData("!Status", "Run Time: " + doubleDigits.format(t2) + ":" +
			                             doubleDigits.format(t3) + ":" +
			                             doubleDigits.format(t1)); // Run Time HH:MM:SS
			telemetry.addData("Arm DEG", bot.arm.getCurrentPosition());
			telemetry.addData("Arm DEG", targetPosition);

			telemetry.update();
			idle();
		}
	}
}