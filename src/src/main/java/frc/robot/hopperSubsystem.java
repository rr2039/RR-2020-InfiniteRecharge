package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class hopperSubsystem { 
    private static WPI_TalonSRX hopperMotor = new WPI_TalonSRX(0);
    private static WPI_TalonSRX feederMotor = new WPI_TalonSRX(0);
    public static void hopperOn() {
        hopperMotor.set(0.5);

    }

    public static void hopperOff() {
        hopperMotor.stopMotor();
    }
    public static void feederOn(){
        feederMotor.set(0.5);
    }

    public static void feederOff(){
        feederMotor.stopMotor();
    }
}