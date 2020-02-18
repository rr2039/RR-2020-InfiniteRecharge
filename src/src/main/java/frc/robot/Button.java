package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class Button {
    private int controller_id;
    private int axis_id;
    private Joystick axisController;
    private JoystickButton toggleButton;
    public boolean state;

    // Button IDs
    public static final int A = 1;
    public static final int B = 2;
    public static final int X = 3;
    public static final int Y = 4;
    public static final int LEFT_BUMPER = 5;
    public static final int RIGHT_BUMPER = 6;
    public static final int BACK = 7;
    public static final int START = 8;

    public Button(int controller_id, int axis_id) {
        this.controller_id = controller_id;
        this.axis_id = axis_id;
        this.axisController = new Joystick(this.controller_id);
        this.state = false;
        this.toggleButton = new JoystickButton(axisController, axis_id);
        toggleButton.whenPressed((Command) new InstantCommand() {
            @Override
            public void execute() {
                state = !state;
            }
        });
    }

}