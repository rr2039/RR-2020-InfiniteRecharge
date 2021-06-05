package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.ID;

public class Turret {
    private double rotationMultiplier;
    private static final double gearRatio = 1;

    private static WPI_TalonSRX rotationMotor = new WPI_TalonSRX(ID.TURRET);
    private static WPI_TalonSRX leftShooter = new WPI_TalonSRX(ID.LEFT_SHOOTER);
    private static WPI_TalonSRX rightShooter = new WPI_TalonSRX(ID.RIGHT_SHOOTER);

    private static final int kTimeoutMs = 30;
    private static final double kF = 0.0362;
    private static final double kP = 0.05;
    private static final double kI = 0;
    private static final double kD = 0;
    public double shooterSpeed = 0.5;

    Solenoid raiseSolenoid = new Solenoid(2);
    Solenoid lowerSolenoid = new Solenoid(3);

    public Turret(double rotationMultiplier) {
        this.rotationMultiplier = rotationMultiplier;
        rotationMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);

        leftShooter.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, kTimeoutMs);
        rightShooter.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, kTimeoutMs);
        
        leftShooter.setSensorPhase(false);
        leftShooter.configNominalOutputForward(0, kTimeoutMs);
        leftShooter.configNominalOutputReverse(0, kTimeoutMs);
        leftShooter.configPeakOutputForward(1, kTimeoutMs);
        leftShooter.configPeakOutputReverse(-1, kTimeoutMs);
        leftShooter.config_kF(0, kF);
        leftShooter.config_kP(0, kP);
        leftShooter.config_kI(0, kI);
        leftShooter.config_kD(0, kD);

        leftShooter.setSensorPhase(false);
        rightShooter.configNominalOutputForward(0, kTimeoutMs);
        rightShooter.configNominalOutputReverse(0, kTimeoutMs);
        rightShooter.configPeakOutputForward(1, kTimeoutMs);
        rightShooter.configPeakOutputReverse(-1, kTimeoutMs);
        rightShooter.config_kF(0, kF);
        rightShooter.config_kP(0, kP);
        rightShooter.config_kI(0, kI);
        rightShooter.config_kD(0, kD);

    }

    public void raise() {
        lowerSolenoid.set(false);
        raiseSolenoid.set(true);
        SmartDashboard.putString("Turret", "Raised");
    }

    public void lower() {
        lowerSolenoid.set(true);
        raiseSolenoid.set(false);
        SmartDashboard.putString("Turret", "Lowered");
    }

    public void rotateByJoystick(double input) {
        rotationMotor.set(input*rotationMultiplier);
    }

    public void rotateByDegrees(double degrees) {
       rotationMotor.set(ControlMode.Position, degreesToQuadrature(degrees));
    }

    public int getTurretRotation() {
        return (rotationMotor.getSelectedSensorPosition()/4096)*360;
    }

    public double getLeftShooterSpeed() {
        return leftShooter.getSelectedSensorVelocity();
    }

    public double getRightShooterSpeed() {
        return rightShooter.getSelectedSensorVelocity();
    }

    public void resetRotation() {
        rotationMotor.set(ControlMode.Position, degreesToQuadrature(0));
    }

    public double degreesToQuadrature(double degrees) {
        return (degrees/360)*4096*gearRatio;
    }
    public void setShooterSpeed(double speed) {
        shooterSpeed = speed;
    }

    public void activate() {
        leftShooter.set(shooterSpeed);
        rightShooter.set(shooterSpeed);
        SmartDashboard.putNumber("Shooter Speed", shooterSpeed);
    }
    public void deactivate() {
        leftShooter.stopMotor();
        rightShooter.stopMotor();
    }
}