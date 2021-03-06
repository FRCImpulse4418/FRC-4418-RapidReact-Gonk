package frc.robot.displays.general;


import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

import frc.robot.displays.Display;


public abstract class GeneralDisplay extends Display {
    protected static final ShuffleboardTab tab = Shuffleboard.getTab("General");

    public GeneralDisplay(int width, int height) {
        super(width, height);
    }
}