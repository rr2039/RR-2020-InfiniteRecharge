package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Solenoid;

public class Turret {
    private double rotationMultiplier;
    private double elevationMultiplier;

    private static WPI_TalonSRX rotationMotor = new WPI_TalonSRX(0);
    private static WPI_TalonSRX elevationMotor = new WPI_TalonSRX(0);

    Solenoid pitchSolenoid = new Solenoid(1);

    public Turret(double rotationMultiplier, double elevationMultiplier) {
        this.rotationMultiplier = rotationMultiplier;
        this.elevationMultiplier = elevationMultiplier;
        rotationMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
        elevationMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
    }

    public void raise(double input) {
        elevationMotor.set(input*elevationMultiplier);
        pitchSolenoid.set(true);
        
    }

    public void rotateByJoystick(double input) {
        rotationMotor.set(input*rotationMultiplier);
    }


    public int getTurretRotation(double gearRatio) {
        return (rotationMotor.getSelectedSensorPosition()/4096)*360;
    }

    public void setRotationDegrees(double degrees) {
        rotationMotor.set(ControlMode.Position, degreesToQuadrature(degrees, 1));
    }

    public void resetRotation() {
        rotationMotor.set(ControlMode.Position, degreesToQuadrature(0, 1));
    }

    public double degreesToQuadrature(double degrees, double gearRatio) {
        return (degrees/360)*4096*gearRatio;
    }
}