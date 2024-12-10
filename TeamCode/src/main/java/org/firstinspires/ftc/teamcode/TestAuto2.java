package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="TestAuto2", group="Autonomous")
public class TestAuto2 extends LinearOpMode {

    private MecanumDrive drive;

    @Override
    public void runOpMode() {
        Pose2d initialPose = new Pose2d(14.57, 62.61, Math.toRadians(90)); // Start facing 90 degrees
        drive = new MecanumDrive(hardwareMap, initialPose);

        TrajectoryActionBuilder loopActions;

        waitForStart();
        if (isStopRequested()) return;

        // Initial movement sequence
        Actions.runBlocking(
                drive.actionBuilder(initialPose)
                        .strafeTo(new Vector2d(8, 45)) // Move to precise position
                        .lineToX(45)
                        .turn(Math.toRadians(180)) // Turn 180 degrees
                        .turn(Math.toRadians(-45)) // Turn -45 degrees
                        .strafeTo(new Vector2d(60, 45)) // Move to target (60, 45)
                        .strafeTo(new Vector2d(47, 45)) // Adjust to (47, 45)
                        .turn(Math.toRadians(-45)) // Final heading adjustment
                        .build()
        );

        // Loops for repetitive task
        for (int i = 0; i < 4; i++) {
            loopActions = drive.actionBuilder(new Pose2d(47, 45, Math.toRadians(45)))
                    .splineTo(new Vector2d(30, 8), Math.toRadians(180)) // Move backward to (30, 8) with heading 180
                    .splineTo(new Vector2d(24, 8), Math.toRadians(180)) // Move forward to (24, 8)
                    .strafeTo(new Vector2d(30, 8)) // Adjust position
                    .splineTo(new Vector2d(47, 45), Math.toRadians(45)); // Return to (47, 45) with corrected heading

            Actions.runBlocking(loopActions.build());
            telemetry.addData("Loop", "Completed %d", i + 1);
            telemetry.update();
            sleep(500); // Pause for the mechanism
        }

        // Final movements
        Actions.runBlocking(
                drive.actionBuilder(new Pose2d(47, 45, Math.toRadians(45)))
                        .splineTo(new Vector2d(30, 8), Math.toRadians(180)) // Final backward move
                        .splineTo(new Vector2d(24, 8), Math.toRadians(180)) // Final forward move
                        .build()
        );

        telemetry.addData("Status", "Autonomous Complete");
        telemetry.update();
    }
}
