package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

public class Button {
    private int controller_id;
    private int axis_id;
    private Joystick axisController;

    public Button(int controller_id, int axis_id) {
        this.controller_id = controller_id;
        this.axis_id = axis_id;
        this.axisController = new Joystick(this.controller_id);
    }
   public boolean isPressed() {
    return axisController.getRawButton(axis_id);
   }
}