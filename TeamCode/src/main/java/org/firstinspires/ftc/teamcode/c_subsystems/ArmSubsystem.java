package org.firstinspires.ftc.teamcode.c_subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;

@Config
public class ArmSubsystem extends SubsystemBase {
	MotorEx arm;

	public ArmSubsystem(MotorEx arm) {
		this.arm = arm;
	}

	@Override
	public void periodic() {
		getPosition();
	}

	public void set(double output) {
		arm.set(output);
	}

	public void stop() {
		arm.stopMotor();
	}

	/**
	 * @return the velocity of the motor in ticks per second
	 */
	public double getVelocity() {
		return arm.getVelocity();
	}

	/**
	 * @param velocity the velocity in ticks per second
	 */
	public void setVelocity(double velocity) {
		arm.setVelocity(velocity);
	}

	/**
	 * @return the acceleration of the motor in ticks per second squared
	 */
	public double getAcceleration() {
		return arm.getAcceleration();
	}

	/**
	 * @return the current position of the encoder
	 */
	public int getPosition() {
		return arm.getCurrentPosition();
	}

	/**
	 * @return the distance traveled by the encoder
	 */
	public double getDistance() {
		return arm.getDistance();
	}

	/**
	 * @return the velocity of the encoder adjusted to account for the distance per pulse
	 */
	public double getRate() {
		return arm.getRate();
	}

	/**
	 * Resets the encoder without having to stop the motor.
	 */
	public void resetEncoder() {
		arm.resetEncoder();
	}

	/**
	 * Sets the distance per pulse of the encoder.
	 *
	 * @param distancePerPulse the desired distance per pulse (in units per tick)
	 */
	public void setDistancePerPulse(double distancePerPulse) {
		arm.setDistancePerPulse(distancePerPulse);
	}

	/**
	 * Corrects for velocity overflow
	 *
	 * @return the corrected velocity
	 */
	public double getCorrectedVelocity() {
		return arm.getCorrectedVelocity();
	}

	/**
	 * Sets the error which is considered tolerable for use with {@link #atSetPoint()}.
	 *
	 * @param positionTolerance Position error which is tolerable.
	 */
	public void setTolerance(double positionTolerance) {
		arm.setPositionTolerance(positionTolerance);
	}

	/**
	 * Sets the setpoint
	 *
	 * @param sp The desired setpoint.
	 */
	public void setSetPoint(int sp) {
		arm.setTargetPosition(sp);
	}

	/**
	 * Returns true if the error is within the percentage of the total input range, determined by
	 * {@link #setTolerance}.
	 *
	 * @return Whether the error is within the acceptable bounds.
	 */
	public boolean atSetPoint() {
		return arm.atTargetPosition();
	}

	/**
	 * @return the PIDF coefficients
	 */
	public double getCoefficients() {
		return arm.getPositionCoefficient();
	}
}