package frc.robot;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import frc.robot.Subsystem;

public class Intake extends Subsystem {
    public Solenoid extendIntakeSolenoid;
    public Solenoid retractIntakeSolenoid;

    public Intake(int motorID, int button, Joystick stick, int extendSolenoidID, int retractSolenoidID) {
        super(motorID, button, stick);
        extendIntakeSolenoid = new Solenoid(extendSolenoidID);
        retractIntakeSolenoid = new Solenoid(retractSolenoidID);
    }
    @Override
    public void activate() {
        motor.set(-0.50);
        SmartDashboard.putBoolean("Intake On", true);
    }

    public void intakeExtend() {
        retractIntakeSolenoid.set(false);
        extendIntakeSolenoid.set(true);
        SmartDashboard.putBoolean("Intake Extended", true);

    }

    public void intakeRetract() {
        extendIntakeSolenoid.set(false);
        retractIntakeSolenoid.set(true);
        SmartDashboard.putBoolean("Intake Extended", false);
    }
    @Override
    public void update() {
        if(controlStick.getRawButtonPressed(buttonID))
        {
            toggle.state = !toggle.state;
        }
        if (toggle.state) {
            this.activate();
            this.intakeExtend();
        } else if(!toggle.state) {
            this.deactivate();
            this.intakeRetract();
        }
    }
}