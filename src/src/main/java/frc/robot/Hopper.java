package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import frc.robot.Subsystem;

public class Hopper extends Subsystem { 
    public WPI_TalonSRX feederMotorTop;
    public WPI_TalonSRX feederMotorBottom;

    public Hopper(int motorID, int button, Joystick stick, int firstFeederID, int secondFeederID) {
        super(motorID, button, stick);
        feederMotorTop = new WPI_TalonSRX(firstFeederID);
        feederMotorBottom = new WPI_TalonSRX(secondFeederID);
    }
    @Override
    public void activate() {
        motor.set(-0.75);
        SmartDashboard.putBoolean("Hopper On", true);
    }
    @Override
    public void deactivate() {
        motor.stopMotor();
        SmartDashboard.putBoolean("Hopper On", false);
    }

    public void feederBottomOn(){
        feederMotorBottom.set(0.5);
    }

    public void feederBottomOff(){
        feederMotorBottom.stopMotor();
    }
    public void feederTopOn(){
        feederMotorTop.set(0.5);
    }
    public void feederTopOff(){
        feederMotorTop.stopMotor();
    }
}