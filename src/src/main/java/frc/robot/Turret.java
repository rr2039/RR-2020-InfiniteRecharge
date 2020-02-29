package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Solenoid;

public class Turret {
    private double rotationMultiplier;
    private static final double gearRatio = 1;

    private static WPI_TalonSRX rotationMotor = new WPI_TalonSRX(24);
    private static WPI_TalonSRX elevationMotor = new WPI_TalonSRX(0);

    Solenoid pitchSolenoid = new Solenoid(1);

    public Turret(double rotationMultiplier) {
        this.rotationMultiplier = rotationMultiplier;
        rotationMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
        elevationMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
    }

    public void raise() {
        pitchSolenoid.set(true);
    }

    public void lower() {
        pitchSolenoid.set(false);
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

    public void resetRotation() {
        rotationMotor.set(ControlMode.Position, degreesToQuadrature(0));
    }

    public double degreesToQuadrature(double degrees) {
        return (degrees/360)*4096*gearRatio;
    }
}