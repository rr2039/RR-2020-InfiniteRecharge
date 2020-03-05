package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import frc.robot.ID;

public class hopperSubsystem { 
    private static WPI_TalonSRX hopperMotor = new WPI_TalonSRX(ID.QUEING);
    private static WPI_TalonSRX feederMotor = new WPI_TalonSRX(ID.FIRST_FEEDER);
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