package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "Move and Rotate", group = "Autonomous")
public class KamoraAutoBlue extends LinearOpMode {

    private MecanumDrive drive;

    @Override
    public void runOpMode() {
        // Initialize the robot's starting pose
        Pose2d startPose = new Pose2d(0, 0, 0);
        drive = new MecanumDrive(hardwareMap, startPose);

        // Define the first action: move forward and strafe left
        Action moveForwardAndStrafeLeft = drive.actionBuilder(startPose)
                .strafeTo(new Vector2d(-24, 24)) // Moves forward and left (diagonally)
                .build();

        // Define the second action: slither right and rotate 180 degrees
        Action slitherRightAndRotate = drive.actionBuilder(new Pose2d(-24, 24, 0))
                .strafeTo(new Vector2d(24, -24)) // Slither right (diagonally opposite)
                .turn(Math.toRadians(180)) // Rotate 180 degrees
                .build();

        waitForStart();
        if (isStopRequested()) return;

        // Execute the actions sequentially
        Actions.runBlocking(moveForwardAndStrafeLeft);
        Actions.runBlocking(slitherRightAndRotate);
    }
}
