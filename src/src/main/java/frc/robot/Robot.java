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
import frc.robot.intakeSubsystem;
import frc.robot.hopperSubsystem;
import frc.robot.aimSubsystem;
import frc.robot.Button;


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
  //private final DifferentialDrive driveTrain = new DifferentialDrive(leftMotor, rightMotor);
  private final Joystick driveStick = new Joystick(0);
  private final Joystick operatorJoy = new Joystick(1);
  DifferentialDrive driveTrain;
  private final Button buttonA = new Button();
  private final Button buttonB = new Button();
  private final Button buttonX = new Button();
  private final Button povUp = new Button();
  private final Button povDown = new Button();
  Turret Turret;
  boolean someBoolean = false;
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
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
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    driveTrain.arcadeDrive(driveStick.getRawAxis(1), driveStick.getRawAxis(0));

    //Replace these Button Stubs with real code if needed
    if (driveStick.getRawButtonPressed(Button.A)) {
      buttonA.state = !buttonA.state;
    }
    if (driveStick.getRawButtonPressed(Button.B)) {
      buttonB.state = !buttonB.state;
    }
    if (driveStick.getRawButtonPressed(Button.X)) {
      buttonX.state = !buttonX.state;
    }
    if (operatorJoy.getPOV() == 0) {
      povUp.state = !povUp.state;
    }
    if (operatorJoy.getPOV() == 3) {
      povDown.state = !povDown.state;
    }
    if (!buttonA.state){
      intakeSubsystem.intakeOff();
      SmartDashboard.putBoolean("IntakeON", false);
    }
    else if (buttonA.state){
      intakeSubsystem.intakeOn();
      SmartDashboard.putBoolean("IntakeON", true);
    }
    if (driveStick.getRawButton(Button.RIGHT_BUMPER)) {
      intakeSubsystem.intakeExtend();
      SmartDashboard.putBoolean("IntakeEXTEND", true);
      SmartDashboard.putBoolean("IntakeRETRACT", false);
    }
    else if (driveStick.getRawButton(Button.LEFT_BUMPER)) {
      intakeSubsystem.intakeRetract();
      SmartDashboard.putBoolean("IntakeRETRACT", true);
      SmartDashboard.putBoolean("IntakeEXTEND", false);
    }
    if (buttonX.state) {
      hopperSubsystem.hopperOn();
      SmartDashboard.putBoolean("HopperON", true);
    }
    else if (!buttonX.state) {
      hopperSubsystem.hopperOff();
      SmartDashboard.putBoolean("HopperON", false);
    }
    if (buttonB.state) {
      aimSubsystem.autoAimOn();
      SmartDashboard.putBoolean("AutoAimON", true);
    }
    else if (!buttonB.state) {
      aimSubsystem.autoAimOff();
      SmartDashboard.putBoolean("AutoAimON", false);
    }
    if (povUp.state) {
      Turret.raise();
    }
    if (povDown.state) {
      Turret.lower();
    }
    Turret.rotateByJoystick(operatorJoy.getRawAxis(0));
    
     
  }
  
  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
