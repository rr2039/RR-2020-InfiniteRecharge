package frc.robot;

public class Button {

    //private JoystickButton toggleButton;
    public boolean state;

    // Button IDs
    public static final int A = 1;
    public static final int B = 2;
    public static final int X = 3;
    public static final int Y = 4;
    public static final int LEFT_BUMPER = 5;
    public static final int RIGHT_BUMPER = 6;
    public static final int BACK = 7;
    public static final int START = 8;

    public Button() {
        this.state = false;
    }

}