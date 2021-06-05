package frc.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.Hopper;
import frc.robot.Intake;
import frc.robot.RobotState;

public class StateMachine {
    public Hopper hopperSystem;
    public Intake intakeSystem;
    public Turret turret;
    public RobotState currentState;
    private AnalogInput sensorIntake;
    private AnalogInput sensorOuttake;
    private boolean sensorIntakeBool;
    private boolean sensorIntakeShadow;
    private boolean sensorOuttakeBool;
    private boolean sensorOuttakeShadow;
    public int ballCount;
    public boolean shoot;
    public Joystick operatorStick;
    public Timer timer;

    public StateMachine(Hopper hopper, Intake intake, Turret turret, Joystick stick) {
        this.hopperSystem = hopper;
        this.intakeSystem = intake;
        this.turret = turret;
        this.operatorStick = stick;
        this.currentState = RobotState.INIT;
        this.sensorIntake = new AnalogInput(0);
        this.sensorOuttake = new AnalogInput(1);
        this.sensorIntakeBool = false;
        this.sensorIntakeShadow = sensorIntakeBool;
        this.sensorOuttakeBool = false;
        this.sensorOuttakeShadow = sensorOuttakeBool;
        this.ballCount = 3;
        this.shoot = false;
        timer = new Timer();
    }

    public void update() {
        if (sensorIntake.getAverageVoltage() > 0.8) {
            sensorIntakeBool = true;
        } else {
            sensorIntakeBool = false;
        }
        if (sensorOuttake.getAverageVoltage() > 0.8) {
            sensorOuttakeBool = true;
        } else {
            sensorOuttakeBool = false;
        }
        
        if (sensorIntakeShadow != sensorIntakeBool) {
            if (sensorIntakeBool) {
              ballCount++;
            }
            sensorIntakeShadow = sensorIntakeBool;
          }
          if (sensorOuttakeShadow != sensorOuttakeBool) {
            if (sensorOuttakeBool) {
            }
            if (!sensorOuttakeBool) {
              ballCount--;
            }
            sensorOuttakeShadow = sensorOuttakeBool;
          }

        if (!shoot) {
          if (ballCount <= 0) {
            ballCount = 0;
            currentState = RobotState.INIT;
          }
          else if (!sensorOuttakeBool) {
            currentState = RobotState.HOT;
          }
          else { 
            currentState = RobotState.ARMED;
            if (operatorStick.getRawButtonPressed(1)) { 
              currentState = RobotState.SHOOT;
              shoot = true;
              timer.start(); 
            } 
          }
        }
        switch(currentState) {
            case INIT:
              SmartDashboard.putString("RobotState", "Init");
              hopperSystem.activate();
              break;
            case HOT:
              SmartDashboard.putString("RobotState", "Hot"); 
              hopperSystem.activate();
              hopperSystem.feederBottomOn();
              break;
            case ARMED:
              SmartDashboard.putString("RobotState", "Armed");
              if (ballCount < 2) {
                hopperSystem.activate();
                hopperSystem.feederBottomOff();
              } 
              else {
                hopperSystem.deactivate();
                hopperSystem.feederBottomOff();
              }
              break;
            case SHOOT:
              SmartDashboard.putString("RobotState", "Shoot");
              hopperSystem.deactivate();
              hopperSystem.feederBottomOff();

              intakeSystem.deactivate();

              turret.activate();
              if (timer.get() >= 1.0) {
                hopperSystem.feederBottomOn();
                hopperSystem.feederTopOn();
              }
              if (timer.get() >= 2.0) {
                turret.deactivate();
                hopperSystem.feederBottomOff();
                hopperSystem.feederTopOff();
                shoot = false;
                timer.stop();
                timer.reset();
              }
              break;
          }

    }
}