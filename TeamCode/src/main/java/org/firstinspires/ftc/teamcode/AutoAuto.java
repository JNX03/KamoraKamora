package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "AUTOAUTO")
public class AutoAuto extends LinearOpMode {

    private DcMotorEx leftFront;
    private DcMotorEx leftBack;
    private DcMotorEx rightFront;
    private DcMotorEx rightBack;

    private BNO055IMU imu;

    @Override
    public void runOpMode() {
        // Initialize Motors
        leftFront = hardwareMap.get(DcMotorEx.class, "leftFront");
        leftBack = hardwareMap.get(DcMotorEx.class, "leftBack");
        rightBack = hardwareMap.get(DcMotorEx.class, "rightBack");
        rightFront = hardwareMap.get(DcMotorEx.class, "rightFront");

        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);

        // Initialize IMU
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        if (opModeIsActive()) {
            // Sample Movements
            MoveToPosition(1000, 1000, 1000, 1000, 0.3, 0.3, 0.3, 0.3); // Forward
            MoveToPosition(-1000, -1000, -1000, -1000, 0.3, 0.3, 0.3, 0.3); // Backward
            MoveToPosition(-1000, 1000, 1000, -1000, 0.3, 0.3, 0.3, 0.3); // Strafe Right
            MoveToPosition(1000, -1000, -1000, 1000, 0.3, 0.3, 0.3, 0.3); // Strafe Left
            MoveToPosition(-1000, 1000, -1000, 1000, 0.3, 0.3, 0.3, 0.3); // Spin Right
            MoveToPosition(1000, -1000, 1000, -1000, 0.3, 0.3, 0.3, 0.3); // Spin Left
        }
    }

    public void MoveToPosition(int frPos, int flPos, int blPos, int brPos, double frSpeed, double flSpeed, double blSpeed, double brSpeed) {
        // Reset Encoders
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Set Target Positions
        leftFront.setTargetPosition(flPos);
        leftBack.setTargetPosition(blPos);
        rightFront.setTargetPosition(frPos);
        rightBack.setTargetPosition(brPos);

        // Set to Run to Position
        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Set Motor Powers
        leftFront.setPower(flSpeed);
        leftBack.setPower(blSpeed);
        rightFront.setPower(frSpeed);
        rightBack.setPower(brSpeed);

        // Wait Until Motors Reach Positions
        while (opModeIsActive() &&
                (leftFront.isBusy() || leftBack.isBusy() || rightFront.isBusy() || rightBack.isBusy())) {
            telemetry.addData("Status", "Moving...");
            telemetry.addData("Target", "LF:%d, LB:%d, RF:%d, RB:%d", flPos, blPos, frPos, brPos);
            telemetry.addData("Current", "LF:%d, LB:%d, RF:%d, RB:%d",
                    leftFront.getCurrentPosition(),
                    leftBack.getCurrentPosition(),
                    rightFront.getCurrentPosition(),
                    rightBack.getCurrentPosition());
            telemetry.update();
        }

        // Stop All Motors
        leftFront.setPower(0);
        leftBack.setPower(0);
        rightFront.setPower(0);
        rightBack.setPower(0);
    }
}
