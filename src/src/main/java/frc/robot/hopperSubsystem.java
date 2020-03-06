package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import frc.robot.ID;

public class hopperSubsystem { 
    private static WPI_TalonSRX hopperMotor = new WPI_TalonSRX(ID.QUEING);
    private static WPI_TalonSRX feederMotorTop = new WPI_TalonSRX(ID.FIRST_FEEDER);
    private static WPI_TalonSRX feederMotorBottom = new WPI_TalonSRX(ID.SECOND_FEEDER);
    public static void hopperOn() {
        hopperMotor.set(-0.75);

    }

    public static void hopperOff() {
        hopperMotor.stopMotor();
    }
    public static void feederBottomOn(){
        feederMotorBottom.set(0.5);
    }

    public static void feederBottomOff(){
        feederMotorBottom.stopMotor();
    }
    public static void feederTopOn(){
        feederMotorTop.set(0.5);
    }
    public static void feederTopOff(){
        feederMotorTop.stopMotor();
    }
}