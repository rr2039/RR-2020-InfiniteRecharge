package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;

public class Subsystem {
    public WPI_TalonSRX motor;
    public int buttonID;
    public Button toggle;
    public Joystick controlStick;

    public Subsystem(int motorID, int button, Joystick stick) {
        motor = new WPI_TalonSRX(motorID);
        buttonID = button;
        toggle = new Button();
        controlStick = stick;
    }

    public void activate() {
        motor.set(-0.25);
    }

    public void deactivate() {
        motor.stopMotor();
    }
    public void update() {
        if(controlStick.getRawButtonPressed(buttonID))
        {
            toggle.state = !toggle.state;
        }
        if (toggle.state) {
            this.activate();
        } else if(!toggle.state) {
            this.deactivate();
        }
    }
}