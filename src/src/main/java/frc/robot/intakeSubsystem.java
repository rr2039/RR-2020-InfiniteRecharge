package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Solenoid;

public class intakeSubsystem {
    private static WPI_TalonSRX motorIntake = new WPI_TalonSRX(1);
    private static Solenoid leftIntakeSolenoid = new Solenoid(0);
    private static Solenoid rightIntakeSolenoid = new Solenoid(1);

    public static void intakeOn() {
        motorIntake.set(-1);
    }

    public static void intakeOff() {
        motorIntake.stopMotor();
    }

    public static void intakeExtend() {
    /* separate system */
        leftIntakeSolenoid.set(true);
        rightIntakeSolenoid.set(true);

    }

    public static void intakeRetract() {
    /* separate system */
        leftIntakeSolenoid.set(false);
        rightIntakeSolenoid.set(false);
    }
}