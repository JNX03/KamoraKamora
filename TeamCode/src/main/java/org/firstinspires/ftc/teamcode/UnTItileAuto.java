package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class UnTItileAuto {
    // Motor declarations
    private DcMotorEx leftFront, leftBack, rightFront, rightBack;

    // IMU declaration
    private BNO055IMU imu;

    // Error constants for precise movement
    private static final double ANGLE_THRESHOLD = 1.0; // Degrees of acceptable deviation
    private static final double POSITION_TOLERANCE = 20; // Encoder ticks tolerance
    private static final double kP = 0.005; // Proportional constant for angle correction

    // Constructor
    public UnTItileAuto(HardwareMap hardwareMap) {
        // Motor initialization
        leftFront = hardwareMap.get(DcMotorEx.class, "leftFront");
        leftBack = hardwareMap.get(DcMotorEx.class, "leftBack");
        rightFront = hardwareMap.get(DcMotorEx.class, "rightFront");
        rightBack = hardwareMap.get(DcMotorEx.class, "rightBack");

        // Correct motor directions
        leftFront.setDirection(DcMotorSimple.Direction.FORWARD);
        leftBack.setDirection(DcMotorSimple.Direction.FORWARD);
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);

        // IMU initialization
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
    }

    /**
     * Move to a specific position with precise encoder and IMU control
     * @param frontRightPos Front Right Motor Target Position
     * @param frontLeftPos Front Left Motor Target Position
     * @param backLeftPos Back Left Motor Target Position
     * @param backRightPos Back Right Motor Target Position
     * @param frontRightSpeed Front Right Motor Speed
     * @param frontLeftSpeed Front Left Motor Speed
     * @param backLeftSpeed Back Left Motor Speed
     * @param backRightSpeed Back Right Motor Speed
     */
    public void MoveToPossition(int frontRightPos, int frontLeftPos,
                                int backLeftPos, int backRightPos,
                                double frontRightSpeed, double frontLeftSpeed,
                                double backLeftSpeed, double backRightSpeed) {
        // Reset encoder positions
        resetEncoders();

        // Set target positions
        rightFront.setTargetPosition(frontRightPos);
        leftFront.setTargetPosition(frontLeftPos);
        leftBack.setTargetPosition(backLeftPos);
        rightBack.setTargetPosition(backRightPos);

        // Set motor modes to RUN_TO_POSITION
        setMotorRunToPosition();

        // Set motor powers
        rightFront.setPower(frontRightSpeed);
        leftFront.setPower(frontLeftSpeed);
        leftBack.setPower(backLeftSpeed);
        rightBack.setPower(backRightSpeed);

        // Wait and correct angle
        ElapsedTime timer = new ElapsedTime();
        while (isMotorsBusy() && timer.seconds() < 5) {
            // Angle correction
            correctAngle();
        }

        // Stop motors
        stopMotors();
    }

    // Movement Presets
    public void MoveForward(int encoderTicks, double speed) {
        MoveToPossition(encoderTicks, encoderTicks, encoderTicks, encoderTicks,
                speed, speed, speed, speed);
    }

    public void MoveBackward(int encoderTicks, double speed) {
        MoveToPossition(-encoderTicks, -encoderTicks, -encoderTicks, -encoderTicks,
                speed, speed, speed, speed);
    }

    public void StrafeRight(int encoderTicks, double speed) {
        MoveToPossition(-encoderTicks, encoderTicks, -encoderTicks, encoderTicks,
                speed, speed, speed, speed);
    }

    public void StrafeLeft(int encoderTicks, double speed) {
        MoveToPossition(encoderTicks, -encoderTicks, encoderTicks, -encoderTicks,
                speed, speed, speed, speed);
    }

    public void SpinRight(int encoderTicks, double speed) {
        MoveToPossition(-encoderTicks, encoderTicks, -encoderTicks, encoderTicks,
                speed, speed, speed, speed);
    }

    public void SpinLeft(int encoderTicks, double speed) {
        MoveToPossition(encoderTicks, -encoderTicks, encoderTicks, -encoderTicks,
                speed, speed, speed, speed);
    }

    // Utility Methods
    private void resetEncoders() {
        leftFront.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
    }

    private void setMotorRunToPosition() {
        leftFront.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        leftBack.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        rightBack.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
    }

    private boolean isMotorsBusy() {
        return leftFront.isBusy() ||
                rightFront.isBusy() ||
                leftBack.isBusy() ||
                rightBack.isBusy();
    }

    private void stopMotors() {
        leftFront.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);
    }

    // Angle Correction Method
    private void correctAngle() {
        // Get current orientation
        Orientation angles = imu.getAngularOrientation(
                AxesReference.INTRINSIC,
                AxesOrder.ZYX,
                AngleUnit.DEGREES
        );

        // Calculate angle error
        double angleError = angles.firstAngle;

        // If angle is outside threshold, apply correction
        if (Math.abs(angleError) > ANGLE_THRESHOLD) {
            // Proportional correction
            double correction = angleError * kP;

            // Apply correction to motors
            leftFront.setPower(leftFront.getPower() - correction);
            rightFront.setPower(rightFront.getPower() + correction);
            leftBack.setPower(leftBack.getPower() - correction);
            rightBack.setPower(rightBack.getPower() + correction);
        }
    }
}