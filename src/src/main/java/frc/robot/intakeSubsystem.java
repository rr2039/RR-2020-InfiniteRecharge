package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class intakeSubsystem {
    private static WPI_TalonSRX motorIntake = new WPI_TalonSRX(0);
    private static WPI_TalonSRX motorExtend = new WPI_TalonSRX(0);

    public static void intakeOn() {
        motorIntake.set(0.1);

    }

    public static void intakeOff() {
        motorIntake.stopMotor();
    }

    public static void intakeExtend() {
    /* separate system */
        motorExtend.set(0.1);

    }

    public static void intakeRetract() {
    /* separate system */
        motorExtend.set(-0.1);
    }
}