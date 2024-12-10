package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="Servo Test", group="Test")
public class ServoTest extends OpMode {

    private Servo wrist, wrist2, claw, upwrist1, upwrist2;
    private double position = 0.5;

    @Override
    public void init() {
        wrist = hardwareMap.get(Servo.class, "wrist");
        wrist2 = hardwareMap.get(Servo.class, "wrist2");  // Reverse in code
        claw = hardwareMap.get(Servo.class, "claw");
        upwrist1 = hardwareMap.get(Servo.class, "upwrist1");
        upwrist2 = hardwareMap.get(Servo.class, "upwrist2");  // Reverse in code

        telemetry.addData("Status", "Initialized");
    }

    @Override
    public void loop() {
        // Adjust servo position with gamepad buttons
        if (gamepad1.dpad_up) position += 0.01;
        if (gamepad1.dpad_down) position -= 0.01;

        // Clamping the position to prevent out-of-range values
        position = Math.max(0.0, Math.min(1.0, position));

        // Set servo positions
        wrist.setPosition(position);
        wrist2.setPosition(1.0 - position);  // Reversed
        claw.setPosition(position);
        upwrist1.setPosition(position);
        upwrist2.setPosition(1.0 - position);  // Reversed

        // Telemetry feedback
        telemetry.addData("Position", position);
        telemetry.addData("Wrist", wrist.getPosition());
        telemetry.addData("Wrist2 (Reversed)", wrist2.getPosition());
        telemetry.addData("Claw", claw.getPosition());
        telemetry.addData("upwrist1", upwrist1.getPosition());
        telemetry.addData("upwrist2 (Reversed)", upwrist2.getPosition());
        telemetry.update();
    }
}
