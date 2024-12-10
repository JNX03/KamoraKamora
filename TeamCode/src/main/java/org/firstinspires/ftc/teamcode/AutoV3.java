package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name="IMU and Encoder Auto", group="Linear Opmode")
public class AutoV3 extends LinearOpMode {

    private DcMotorEx leftFront, leftBack, rightFront, rightBack;
    private BNO055IMU imu;

    @Override
    public void runOpMode() {
        // Hardware Mapping
        leftFront = hardwareMap.get(DcMotorEx.class, "leftFront");
        leftBack = hardwareMap.get(DcMotorEx.class, "leftBack");
        rightFront = hardwareMap.get(DcMotorEx.class, "rightFront");
        rightBack = hardwareMap.get(DcMotorEx.class, "rightBack");

        // Motor Direction Setup
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);

        // IMU Initialization
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        BNO055IMU.Parameters imuParams = new BNO055IMU.Parameters();
        imuParams.mode = BNO055IMU.SensorMode.IMU;
        imuParams.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        imuParams.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        imu.initialize(imuParams);

        // Reset Encoders
        resetEncoders();

        waitForStart();

        // Example Moves
        MoveToPosition(1000, 1000, 1000, 1000, 0.5, 0.5, 0.5, 0.5); // Forward
        MoveToPosition(-1000, -1000, -1000, -1000, 0.5, 0.5, 0.5, 0.5); // Backward
        MoveToPosition(-1000, 1000, 1000, -1000, 0.5, 0.5, 0.5, 0.5); // Strafe Left
        MoveToPosition(1000, -1000, -1000, 1000, 0.5, 0.5, 0.5, 0.5); // Strafe Right
    }

    // Reset Motor Encoders
    private void resetEncoders() {
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    // Movement Function
    private void MoveToPosition(int frp, int flp, int blp, int brp, double frs, double fls, double bls, double brs) {
        leftFront.setTargetPosition(flp);
        leftBack.setTargetPosition(blp);
        rightFront.setTargetPosition(frp);
        rightBack.setTargetPosition(brp);

        leftFront.setPower(fls);
        leftBack.setPower(bls);
        rightFront.setPower(frs);
        rightBack.setPower(brs);

        while (opModeIsActive() &&
                (leftFront.isBusy() && leftBack.isBusy() && rightFront.isBusy() && rightBack.isBusy())) {
            telemetry.addData("Status", "Motors Moving");
            telemetry.update();
        }

        leftFront.setPower(0);
        leftBack.setPower(0);
        rightFront.setPower(0);
        rightBack.setPower(0);

        sleep(100);  // Allow for complete stop
    }
}
