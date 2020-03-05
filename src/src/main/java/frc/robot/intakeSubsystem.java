package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.ID;

public class intakeSubsystem {
    private static WPI_TalonSRX motorIntake = new WPI_TalonSRX(ID.INTAKE);
    private static Solenoid leftIntakeSolenoid = new Solenoid(ID.LEFT_INTAKE_SOLENOID);
    private static Solenoid rightIntakeSolenoid = new Solenoid(ID.RIGHT_INTAKE_SOLENOID);

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