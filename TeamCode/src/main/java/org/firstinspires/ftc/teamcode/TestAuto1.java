package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="TestAuto1", group="Autonomous")
public class TestAuto1 extends LinearOpMode {

    private MecanumDrive drive;

    @Override
    public void runOpMode() {
        Pose2d initialPose = new Pose2d(14.57, 62.61, Math.toRadians(90)); // Start facing 180 degrees
        drive = new MecanumDrive(hardwareMap, initialPose);

        TrajectoryActionBuilder loopActions;

        waitForStart();
        if (isStopRequested()) return;

        // Initial movement sequence
        Actions.runBlocking(
                drive.actionBuilder(initialPose)
                        .strafeTo(new Vector2d(8, 40)) // Move to (8, 40)
                        .strafeTo(new Vector2d(-47, 45)) // Strafe to (47, 45)
                        .turn(Math.toRadians(180)) // Spin 180 degrees
                        .turn(Math.toRadians(-45)) // Rotate right 45 degrees
                        .turn(Math.toRadians(45)) // Rotate back left 45 degrees
                        .strafeTo(new Vector2d(60, 45)) // Strafe to (60, 45)
                        .strafeTo(new Vector2d(47, 45)) // Go back to (47, 45)                        .turn(Math.toRadians(-45)) // Rotate right 45 degrees
                        .build()
        );

        // Loops
//        for (int i = 0; i < 4; i++) {
//            loopActions = drive.actionBuilder(new Pose2d(47, 45, Math.toRadians(45)))
//                    .splineTo(new Vector2d(30, 8), Math.toRadians(180)) // Backward slightly to avoid wall #ใช้ได้
//                    .splineTo(new Vector2d(24, 8), Math.toRadians(180)) // Forward to (24, 8) #ใช้ได้
//                    for mission
//
//                    .strafeTo(new Vector2d(30, 8))
//                    .splineTo(new Vector2d(47, 45), Math.toRadians(45)); // Return to (47, 45) //back did't work
//
//            Actions.runBlocking(loopActions.build());
//            sleep(500); // Wait 0.5 seconds for the basket
//        }

        // Final movements Working!
        Actions.runBlocking(
                drive.actionBuilder(new Pose2d(47, 45, Math.toRadians(45)))
                        .splineTo(new Vector2d(30, 8), Math.toRadians(180)) // Backward slightly to avoid wall
                        .splineTo(new Vector2d(24, 8), Math.toRadians(180)) // Final move to (24, 8)
                        .build()
        );
    }
}
