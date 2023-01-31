package org.firstinspires.ftc.teamcode.a_opmodes.auto;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.b_commands.auto.TrajectorySequenceCommand;
import org.firstinspires.ftc.teamcode.c_subsystems.auto.TankSubsystem;
import org.firstinspires.ftc.teamcode.d_roadrunner.drive.TankDrive;
import org.firstinspires.ftc.teamcode.d_roadrunner.trajectorysequence.TrajectorySequence;


@Autonomous(name = "Park Middle", group = "Parking", preselectTeleOp = "CommandTeleOp")
public class ParkMid extends CommandOpMode {
	@Override
	public void initialize() {
		if (isStopRequested()) return;

		FtcDashboard dashboard          = FtcDashboard.getInstance(); //FTC Dashboard Instance
		Telemetry    dashboardTelemetry = dashboard.getTelemetry();
		telemetry = new MultipleTelemetry(telemetry, dashboardTelemetry);

		TankSubsystem drive = new TankSubsystem(new TankDrive(hardwareMap), true);


		TrajectorySequence parkingSequence = drive.trajectorySequenceBuilder(new Pose2d())
				.forward(24)
				.build();

		TrajectorySequenceCommand park = new TrajectorySequenceCommand(drive, parkingSequence);

		schedule(park);
	}
}