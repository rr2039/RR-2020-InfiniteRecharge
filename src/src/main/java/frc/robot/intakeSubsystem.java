package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.ID;

public class intakeSubsystem {
    private static WPI_TalonSRX motorIntake = new WPI_TalonSRX(ID.INTAKE);
    private static Solenoid extendIntakeSolenoid = new Solenoid(0);
    private static Solenoid retractIntakeSolenoid = new Solenoid(1);

    public static void intakeOn() {
        motorIntake.set(-0.50);
    }

    public static void intakeOff() {
        motorIntake.stopMotor();
    }

    public static void intakeExtend() {
    /* separate system */
        retractIntakeSolenoid.set(false);
        extendIntakeSolenoid.set(true);

    }

    public static void intakeRetract() {
    /* separate system */
        extendIntakeSolenoid.set(false);
        retractIntakeSolenoid.set(true);
    }
}