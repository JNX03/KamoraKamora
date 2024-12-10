package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="IntoTheDeepUpdated", group="TeleOp")
public class IntoTheDeep extends LinearOpMode {

    private MecanumDrive drive;
    private DcMotorEx slideMotor, slideMotor2, UpSlide1, UpSlide2;
    private Servo wrist, wrist2, claw, upWrist1, upWrist2;

    @Override
    public void runOpMode() {
        drive = new MecanumDrive(hardwareMap, new Pose2d(-24, -72, Math.toRadians(180)));

        // Motor initialization
        slideMotor = hardwareMap.get(DcMotorEx.class, "slideMotor");
        slideMotor2 = hardwareMap.get(DcMotorEx.class, "slideMotor2");
        UpSlide1 = hardwareMap.get(DcMotorEx.class, "UpSlide1");
        UpSlide2 = hardwareMap.get(DcMotorEx.class, "UpSlide2");

        // Servo initialization
        wrist = hardwareMap.get(Servo.class, "wrist");
        wrist2 = hardwareMap.get(Servo.class, "wrist2");
        claw = hardwareMap.get(Servo.class, "claw");
        upWrist1 = hardwareMap.get(Servo.class, "upWrist1");
        upWrist2 = hardwareMap.get(Servo.class, "upWrist2");

        // Set motor properties
        setMotorProperties(slideMotor, DcMotor.Direction.FORWARD);
        setMotorProperties(slideMotor2, DcMotor.Direction.REVERSE);
        setMotorProperties(UpSlide1, DcMotor.Direction.FORWARD);
        setMotorProperties(UpSlide2, DcMotor.Direction.REVERSE);

        waitForStart();

        while (opModeIsActive()) {
            controlDrivetrain();

            // Wrist Control
            if (gamepad1.dpad_up) {
                wrist.setPosition(0.5);
                wrist2.setPosition(0.5);
            } else if (gamepad1.dpad_left) {
                wrist.setPosition(0.25);
                wrist2.setPosition(0.75);
            }

            // Claw Control
            if (gamepad1.b) claw.setPosition(0.9);
            else if (gamepad1.a) claw.setPosition(0);
            else if (gamepad1.x) claw.setPosition(0.5);

            // UpSlides Control
            controlUpSlides();

            // UpWrist Control
            if (gamepad2.left_bumper) {
                upWrist1.setPosition(1);
                upWrist2.setPosition(0);
            } else if (gamepad2.right_bumper) {
                upWrist1.setPosition(0);
                upWrist2.setPosition(1);
            }

            // Telemetry Display
            telemetry.addData("Wrist Position", wrist.getPosition());
            telemetry.addData("Claw Position", claw.getPosition());
            telemetry.addData("UpSlide1 Power", UpSlide1.getPower());
            telemetry.addData("UpSlide2 Power", UpSlide2.getPower());
            telemetry.addData("UpWrist1 Position", upWrist1.getPosition());
            telemetry.addData("UpWrist2 Position", upWrist2.getPosition());
            telemetry.update();
        }
    }

    private void setMotorProperties(DcMotorEx motor, DcMotor.Direction direction) {
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor.setDirection(direction);
    }

    private void controlDrivetrain() {
        double y = -gamepad1.left_stick_y;
        double x = gamepad1.left_stick_x;
        double rx = -gamepad1.right_stick_x;

        if (Math.abs(y) > 0.05 || Math.abs(x) > 0.05 || Math.abs(rx) > 0.05) {
            PoseVelocity2d drivePowers = new PoseVelocity2d(new Vector2d(y, x), rx);
            drive.setDrivePowers(drivePowers);
        } else {
            drive.setDrivePowers(new PoseVelocity2d(new Vector2d(0, 0), 0));
        }
    }

    private void controlUpSlides() {
        if (gamepad2.b) {
            UpSlide1.setPower(0.7);
            UpSlide2.setPower(0.7);
        } else if (gamepad2.a) {
            UpSlide1.setPower(-0.7);
            UpSlide2.setPower(-0.7);
        } else {
            UpSlide1.setPower(0);
            UpSlide2.setPower(0);
        }

        if (gamepad2.dpad_down) {
            slideMotor.setPower(0.9);
            slideMotor2.setPower(0.9);
        } else if (gamepad2.dpad_left) {
            slideMotor.setPower(-0.9);
            slideMotor2.setPower(-0.9);
        } else {
            slideMotor.setPower(0);
            slideMotor2.setPower(0);
        }
    }
}
