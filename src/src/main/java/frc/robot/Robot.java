/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.SpeedControllerGroup;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Timer;

import frc.robot.Intake;
import frc.robot.Hopper;
import frc.robot.Button;
import frc.robot.RobotState;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  private CANSparkMax leftFrontMotor;
  private CANSparkMax leftBackMotor;

  private CANSparkMax rightFrontMotor;
  private CANSparkMax rightBackMotor;
  private final Joystick driveStick = new Joystick(0);
  private final Joystick operatorJoy = new Joystick(1);
  DifferentialDrive driveTrain;
  private final Button button10 = new Button();
  private final Button buttonA = new Button();
  Turret turret = new Turret(0.25);
  private RobotState state = RobotState.INIT;
  private AnalogInput sensorIntake = new AnalogInput(0);
  private AnalogInput sensorOuttake = new AnalogInput(1);
  private boolean sensorIntakeBool = false;
  private boolean sensorIntakeShadow = sensorIntakeBool;
  private boolean sensorOuttakeBool = false;
  private boolean sensorOuttakeShadow = sensorOuttakeBool;
  private int ballCount = 3;
  private Timer timer = new Timer();
  private Timer autoTimer = new Timer();
  private boolean shoot = false;
  public Hopper hopperSystem;
  public Intake intakeSystem;
  public StateMachine stateMachine;

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   * 
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    leftFrontMotor = new CANSparkMax(1, MotorType.kBrushless);
    leftBackMotor = new CANSparkMax(2, MotorType.kBrushless);

    rightFrontMotor = new CANSparkMax(3, MotorType.kBrushless);
    rightBackMotor = new CANSparkMax(4, MotorType.kBrushless);

    SpeedControllerGroup leftDriveTrainGroup = new SpeedControllerGroup(leftFrontMotor, leftBackMotor);
    SpeedControllerGroup rightDriveTrainGroup = new SpeedControllerGroup(rightFrontMotor, rightBackMotor);

    driveTrain = new DifferentialDrive(leftDriveTrainGroup, rightDriveTrainGroup);
    hopperSystem = new Hopper(ID.QUEING, 2, operatorJoy, ID.FIRST_FEEDER, ID.SECOND_FEEDER);
    intakeSystem = new Intake(ID.INTAKE, 3, operatorJoy, 0, 1);
    stateMachine = new StateMachine(hopperSystem, intakeSystem, turret, operatorJoy);

    UsbCamera camera1 = CameraServer.getInstance().startAutomaticCapture(0);
    camera1.setResolution(360, 240);
    camera1.setFPS(15);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable chooser
   * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
   * remove all of the chooser code and uncomment the getString line to get the
   * auto name from the text box below the Gyro
   *
   * <p>
   * You can add additional auto modes by adding additional comparisons to the
   * switch structure below with additional strings. If using the SendableChooser
   * make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
    autoTimer.start();
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
    case kCustomAuto:
     if (autoTimer.get() < 0.7){
        driveTrain.arcadeDrive(-0.5, 0);
      }
      else {
        driveTrain.arcadeDrive(0, 0);
        autoTimer.stop();
        if (sensorIntake.getAverageVoltage() > 0.8) {
          sensorIntakeBool = true;
        }
        else {
          sensorIntakeBool = false;
        }
        if (sensorOuttake.getAverageVoltage() > 0.8) {
          sensorOuttakeBool = true;
        }
        else {
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
            state = RobotState.INIT;
          }
          else if (!sensorOuttakeBool) {
            state = RobotState.HOT;
          }
          else { 
            state = RobotState.ARMED;
            state = RobotState.SHOOT;
            shoot = true;
            timer.start();  
          }
        }
        if (state == RobotState.INIT) {
          SmartDashboard.putString("RobotState", "Init");
          hopperSystem.activate();
          ballCount = 0;
          break;
        }
        else if (state == RobotState.HOT) {
          SmartDashboard.putString("RobotState", "Hot");
          hopperSystem.activate();
          hopperSystem.feederBottomOn();
        }
        else if (state == RobotState.ARMED) {
          SmartDashboard.putString("RobotState", "Armed");
          if (ballCount < 2) {
            hopperSystem.activate();
            hopperSystem.feederBottomOff();
          } 
          else {
            hopperSystem.deactivate();
            intakeSystem.deactivate();
            hopperSystem.feederBottomOff();
          }
        }
        else if (state == RobotState.SHOOT) {
          SmartDashboard.putString("RobotState", "Shoot");
          hopperSystem.deactivate();
          intakeSystem.deactivate();
          hopperSystem.feederBottomOff();
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
        }
      }
      break;
    case kDefaultAuto:
    default:
      if (autoTimer.get() < 7.0 && autoTimer.get() > 5.0){
        driveTrain.arcadeDrive(0.5, 0);
      }
      else {
        driveTrain.arcadeDrive(0, 0);
        autoTimer.stop();
        break;
      }
      break;
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {

    /* Drive by Joystick; Toggle Reverse or Inverted Drive */
    if (driveStick.getRawButtonPressed(Button.A)) {
      buttonA.state = !buttonA.state;
    }
    if (buttonA.state){
      driveTrain.arcadeDrive(driveStick.getRawAxis(1) * -1, driveStick.getRawAxis(4));
    } else if (!buttonA.state) {
      driveTrain.arcadeDrive(driveStick.getRawAxis(1), driveStick.getRawAxis(4));
    }

    intakeSystem.update();
    hopperSystem.update();

    if (Math.abs(operatorJoy.getRawAxis(0)) > 0.5) {
      turret.rotateByJoystick(operatorJoy.getRawAxis(0));
    } else {
      turret.rotateByJoystick(0);
    }

    /* Toggle Raise & Lower Turret */
    if (operatorJoy.getRawButtonPressed(10)) {
      button10.state = !button10.state;
    }
    if (button10.state) {
      turret.raise();
    } else if(!button10.state) {
      turret.lower();
    }
    
    /* Increment & Decrement Turret Speed */
    if (operatorJoy.getRawButtonPressed(9)) {
      turret.setShooterSpeed(turret.shooterSpeed + 0.1);
    } else if (operatorJoy.getRawButtonPressed(8)) {
      turret.setShooterSpeed(turret.shooterSpeed - 0.1);
    }

    /* Manual Shoot Override */
    if (operatorJoy.getRawButtonPressed(1)) { 
      stateMachine.currentState = RobotState.SHOOT;
      stateMachine.shoot = true;
      stateMachine.timer.start();
    }
    
    stateMachine.update();

    SmartDashboard.putNumber("Ball Count", ballCount);
    SmartDashboard.putNumber("Left Shooter Speed", turret.getLeftShooterSpeed());
    SmartDashboard.putNumber("Right Shooter Speed", turret.getRightShooterSpeed());
    SmartDashboard.putNumber("Speed Dial", operatorJoy.getRawAxis(2)*50000);
    SmartDashboard.putBoolean("Reverse", buttonA.state);
  }

  
  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }

}