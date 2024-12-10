package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import org.rowlandhall.meepmeep.MeepMeep;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                .setConstraints(150, 150, Math.toRadians(540), Math.toRadians(540), 15)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(35, 60, Math.toRadians(90)))
                                .lineToSplineHeading(
                                        new Pose2d(10, 35, Math.toRadians(-90))
                                )
                                .strafeLeft(38)
                                .waitSeconds(1)
                                .strafeTo(new Vector2d(60, 45))
                                .waitSeconds(1)
                                .lineTo(new Vector2d(60, 35))
                                .waitSeconds(1)
                                .lineTo(new Vector2d(60, 50))
                                .splineToSplineHeading(
                                        new Pose2d(30, 7, Math.toRadians(-180)),
                                        Math.toRadians(90)
                                )
                                .waitSeconds(1)
                                .lineToSplineHeading(
                                        new Pose2d(50, 45, Math.toRadians(-135))
                                )
                                .waitSeconds(1)
                                .splineToSplineHeading(
                                        new Pose2d(30, 7, Math.toRadians(-180)),
                                        Math.toRadians(90)
                                )
                                .waitSeconds(1)
                                .lineToSplineHeading(
                                        new Pose2d(50, 45, Math.toRadians(-135))
                                )
                                .waitSeconds(1)
                                .splineToSplineHeading(
                                        new Pose2d(30, 7, Math.toRadians(-180)),
                                        Math.toRadians(90)
                                )
                                .waitSeconds(1)
                                .lineToSplineHeading(
                                        new Pose2d(50, 45, Math.toRadians(-135))
                                )
                                .waitSeconds(1)
                                .splineToSplineHeading(
                                        new Pose2d(30, 7, Math.toRadians(-180)),
                                        Math.toRadians(90)
                                )
                                .waitSeconds(1)
                                .lineToSplineHeading(
                                        new Pose2d(50, 45, Math.toRadians(-135))
                                )
                                .waitSeconds(1)
                                .splineToSplineHeading(
                                        new Pose2d(30, 7, Math.toRadians(-180)),
                                        Math.toRadians(90)
                                )
                                .waitSeconds(1)
                                .lineToSplineHeading(
                                        new Pose2d(50, 45, Math.toRadians(-135))
                                )
                                .waitSeconds(1)
                                .lineToSplineHeading(
                                        new Pose2d(30, 10, Math.toRadians(-180))
                                )
                                .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTOTHEDEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
