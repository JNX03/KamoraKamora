package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import org.rowlandhall.meepmeep.MeepMeep;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class Yippy {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        // Create the bot entity with the desired paths
        RoadRunnerBotEntity bot = new DefaultBotBuilder(meepMeep)
                .setDimensions(13, 18) // Set robot dimensions (in inches)
                .setConstraints(50, 50, Math.toRadians(180), Math.toRadians(180), 14) // Constraints
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(0, 0, 0)) // Start at origin
                                .strafeTo(new Vector2d(-24, 24)) // Move forward and left diagonally
                                .strafeTo(new Vector2d(24, -24)) // Move right diagonally
                                .turn(Math.toRadians(180)) // Rotate 180 degrees
                                .build()
                );

        // Configure MeepMeep simulation
        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(bot)
                .start();
    }
}
