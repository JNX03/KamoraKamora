package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="IntoTheDeep", group="TeleOp")
public class FinalAuto extends LinearOpMode {

    // Declare OpMode members
    private MecanumDrive drive;
    private DcMotorEx slideMotor = null;
    private DcMotorEx slideMotor2 = null;

    private DcMotorEx UpSlide1 = null;
    private DcMotorEx UpSlide2 = null;

    //    private DcMotor armMotor = null;
    private Servo wrist = null;
    private Servo wrist2 = null;

    private Servo claw = null;

    // Constants for arm and lift movements
    final double ARM_TICKS_PER_DEGREE = 28 * 250047.0 / 4913.0 * 100.0 / 20.0 * 1/360.0;
    final double LIFT_TICKS_PER_MM = (111132.0 / 289.0) / 120.0;

    // Variables for tracking positions
    private double armPositionDegrees = 5; // Using degrees for arm position
    private double slidePositionMM = 0.75;    // Using millimeters for slide position
    private double UpslidePositionMM = 0.75;    // Using millimeters for slide position

    private double wristPosition = 0.66;      // Wrist servo position
    private final double joystickThreshold = 0.05; // Threshold to ignore minor joystick inputs

    @Override
    public void runOpMode() {
        // Initialize hardware and MecanumDrive
        drive = new MecanumDrive(hardwareMap, new Pose2d(-24, -72, Math.toRadians(180)));
        slideMotor = hardwareMap.get(DcMotorEx.class, "slideMotor");
        slideMotor2 = hardwareMap.get(DcMotorEx.class, "slideMotor2");
        UpSlide1 = hardwareMap.get(DcMotorEx.class, "UpSlide1");
        UpSlide2 = hardwareMap.get(DcMotorEx.class, "UpSlide2");
//        armMotor = hardwareMap.get(DcMotor.class, "armMotor");
        wrist = hardwareMap.get(Servo.class, "wrist");
        wrist2 = hardwareMap.get(Servo.class, "wrist2");
        claw = hardwareMap.get(Servo.class, "claw"); // Initialize the claw servo

        // Set the motors to brake when zero power is applied
        UpSlide1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        UpSlide2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        slideMotor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        slideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Set initial positions for arm and slide
//        slideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        slideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slideMotor.setDirection(DcMotor.Direction.FORWARD);
//        slideMotor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        slideMotor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slideMotor2.setDirection(DcMotor.Direction.REVERSE);

//        UpSlide1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        UpSlide1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        UpSlide1.setDirection(DcMotor.Direction.FORWARD);
//        UpSlide2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        UpSlide2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        UpSlide2.setDirection(DcMotor.Direction.REVERSE);

//        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Wait for the game driver to press play
        waitForStart();

        // Main OpMode loop
        while (opModeIsActive()) {
            // Drivetrain controls using MecanumDrive
            double y = - gamepad1.left_stick_y; // Forward/backward
            double x =  gamepad1.left_stick_x;  // Left/right strafing
            double rx = - gamepad1.right_stick_x; // Rotation

            // Only move if joystick input exceeds the threshold
            if (Math.abs(y) > joystickThreshold || Math.abs(x) > joystickThreshold || Math.abs(rx) > joystickThreshold) {
                PoseVelocity2d drivePowers = new PoseVelocity2d(new Vector2d(y, x), rx);
                drive.setDrivePowers(drivePowers);
            } else {
                // Stop the drivetrain if joystick is within the deadzone
                drive.setDrivePowers(new PoseVelocity2d(new Vector2d(0, 0), 0));
            }

//            // Slide control using bumpers
//            if (gamepad1.right_bumper) {
//                slidePositionMM += 5; // Raise slide
//            } else if (gamepad1.left_bumper) {
//                slidePositionMM -= 5; // Lower slide
//            }
//            slidePositionMM = Range.clip(slidePositionMM, 0, 300); // Limit slide position
//            int slideTargetPosition = (int) (slidePositionMM * LIFT_TICKS_PER_MM); // Convert mm to ticks
//            slideMotor.setTargetPosition(slideTargetPosition);
//            slideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            slideMotor.setPower(0.5);
//            slideMotor2.setTargetPosition(slideTargetPosition);
//            slideMotor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            slideMotor2.setPower(0.5);

//            if (gamepad2.right_bumper) {
//                UpslidePositionMM += 5; // Raise slide
//            } else if (gamepad2.left_bumper) {
//                UpslidePositionMM -= 5; // Lower slide
//            }
//            UpslidePositionMM = Range.clip(UpslidePositionMM, 0, 300); // Limit slide position
//            int UpslideTargetPosition = (int) (UpslidePositionMM * LIFT_TICKS_PER_MM); // Convert mm to ticks
//            UpSlide1.setTargetPosition(slideTargetPosition);
//            UpSlide1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            UpSlide1.setPower(0.1);
//            UpSlide2.setTargetPosition(slideTargetPosition);
//            UpSlide2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            UpSlide2.setPower(0.1);

            // Arm control using D-pad up/down
//            if (gamepad1.dpad_up) {
//                armPositionDegrees += 2; // Raise arm
//            } else if (gamepad1.dpad_down) {
//                armPositionDegrees -= 2; // Lower arm
//            }
//            armPositionDegrees = Range.clip(armPositionDegrees, 0, 145); // Limit arm position
//            int armTargetPosition = (int) (armPositionDegrees * ARM_TICKS_PER_DEGREE); // Convert degrees to ticks
//            armMotor.setTargetPosition(armTargetPosition); //hello world a
//            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            armMotor.setPower(0.5);

            // Wrist control using D-pad left/right
            if (gamepad1.dpad_right) {
                wrist.setPosition(90);
                wrist2.setPosition(-90);
                //wristPosition = Math.max(0.32, wristPosition - 0.01); // Move wrist in
            } else if (gamepad1.dpad_left) {
                wrist.setPosition(-90);
                wrist2.setPosition(90);
                //wristPosition = Math.min(1, wristPosition + 0.01); // Move wrist out
            } else if (gamepad1.dpad_up) {
                wrist.setPosition(30);
                wrist2.setPosition(-30);
            }
            //wrist.setPosition(wristPosition);

            // Claw control using A (close), X (neutral), and B (open)
            if (gamepad1.b) {
                claw.setPosition(90);  // Open the claw
            } else if (gamepad1.a) {
                claw.setPosition(0);  // Close the claw
            } else if (gamepad1.x) {
                claw.setPosition(0.5);  // Neutral position
            }
            if (gamepad2.b) {
                UpSlide1.setPower(0.3);
                UpSlide2.setPower(0.3);
            } else if (gamepad2.a) {
                UpSlide1.setPower(-0.3);
                UpSlide2.setPower(-0.3);
            } else {
                UpSlide1.setPower(0);
                UpSlide2.setPower(0);
            }

            if (gamepad2.dpad_down) {
                slideMotor.setPower(0.7);
                slideMotor2.setPower(0.7);
            } else if (gamepad2.dpad_left) {
                slideMotor.setPower(-0.7);
                slideMotor2.setPower(-0.7);
            } else {
                slideMotor.setPower(0);
                slideMotor2.setPower(0);
            }
            // Telemetry for debugging and calibration
//            telemetry.addData("Slide Position (mm):", slidePositionMM);
//            telemetry.addData("Slide Target Position (ticks):", slideMotor.getTargetPosition());
//            telemetry.addData("Slide Current Position (ticks):", slideMotor.getCurrentPosition());

//            telemetry.addData("UpSlide Position (mm):", UpslidePositionMM);
//            telemetry.addData("Up Slide Target Position (ticks):", UpSlide1.getTargetPosition());
//            telemetry.addData("Up Slide Current Position (ticks):", UpSlide1.getCurrentPosition());
//            telemetry.addData("Arm Position (degrees):", armPositionDegrees);
//            telemetry.addData("Arm Target Position (ticks):", armMotor.getTargetPosition());
//            telemetry.addData("Arm Current Position (ticks):", armMotor.getCurrentPosition());

            telemetry.addData("Wrist Position", wrist.getPosition());
            telemetry.addData("Claw Position", claw.getPosition());  // Added claw telemetry
            telemetry.update();
        }
    }
}