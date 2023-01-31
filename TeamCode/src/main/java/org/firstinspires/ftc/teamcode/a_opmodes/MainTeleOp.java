package org.firstinspires.ftc.teamcode.a_opmodes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.b_commands.ArmCommand;
import org.firstinspires.ftc.teamcode.b_commands.DriveCommand;
import org.firstinspires.ftc.teamcode.c_subsystems.ArmSubsystem;
import org.firstinspires.ftc.teamcode.c_subsystems.ClawSubsystem;
import org.firstinspires.ftc.teamcode.c_subsystems.DriveSubsystem;

//@Disabled
@Config
@TeleOp(name = "MainTeleOp", group = ".")
public class MainTeleOp extends CommandOpMode {
	@Override
	public void initialize() {
		if (isStopRequested()) return;

		// Get Devices
		final Devices devices = new Devices(hardwareMap);

		// Gamepad
		GamepadEx gPad1 = new GamepadEx(gamepad1);

		// Define Systems ----------------------------------------------------------------------------------------------------
		DriveSubsystem driveSubsystem = new DriveSubsystem(devices.leftMotors, devices.rightMotors);
		ClawSubsystem  claw           = new ClawSubsystem(devices.claw);
		ArmSubsystem   armSubsystem   = new ArmSubsystem(devices.arm);

		DriveCommand driveCommand = new DriveCommand(driveSubsystem, gPad1::getLeftY, gPad1::getRightX);
		ArmCommand   armCommand   = new ArmCommand(armSubsystem);

		telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

		// CONTROLS ----------------------------------------------------------------------------------------------------
		// X Button = Claw Open/Close
		gPad1.getGamepadButton(GamepadKeys.Button.X)
				.whenPressed(new ConditionalCommand(
						new InstantCommand(claw::open, claw),
						new InstantCommand(claw::close, claw),
						() -> {
							claw.toggle();
							return claw.isOpen();
						}
				));

		gPad1.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenPressed(new InstantCommand(() -> armCommand.setLiftLevels(ArmCommand.ArmLevels.FLOOR)));
		gPad1.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whenPressed(new InstantCommand(() -> armCommand.setLiftLevels(ArmCommand.ArmLevels.LOW)));
		gPad1.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(new InstantCommand(() -> armCommand.setLiftLevels(ArmCommand.ArmLevels.MED)));
		gPad1.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenPressed(new InstantCommand(() -> armCommand.setLiftLevels(ArmCommand.ArmLevels.HIGH)));

		// Register and Schedule ----------------------------------------------------------------------------------------------------
		register(driveSubsystem, armSubsystem);
		schedule(driveCommand.alongWith(armCommand, new RunCommand(() -> {
			// Telemetry
			telemetry.update();
		})));

	}
}
