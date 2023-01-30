package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.opmodes.Hardware;

@SuppressWarnings("unused")
@Autonomous(name = "FF: ¼s Forward", group = "Auto", preselectTeleOp = "FF: TeleOp")
// @Disabled
public class Auto_025_Second extends LinearOpMode {

	static final  double      FORWARD_SPEED = 0.6;
	static final  double      FORWARD_TIME  = 0.25;
	// Declare Members
	private final ElapsedTime runtime       = new ElapsedTime();

	@Override
	public void runOpMode() {

		// Get Hardware
		final Hardware robot = new Hardware(hardwareMap);

		// Telemetry
		telemetry.addData("Status", "Ready to run");
		telemetry.update();

		// Wait for START
		waitForStart();

		// Drive forward
		robot.leftMotors.set(FORWARD_SPEED);
		robot.rightMotors.set(-FORWARD_SPEED);
		runtime.reset();
		while (opModeIsActive() && (runtime.seconds() < FORWARD_TIME)) {
			telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
			telemetry.update();
		}

		telemetry.addData("Path", "Complete");
		telemetry.update();
	}
}