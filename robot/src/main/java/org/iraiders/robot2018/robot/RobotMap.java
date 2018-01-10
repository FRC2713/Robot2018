package org.iraiders.robot2018.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// Motors
	public static final int frontLeftTalonPort = 1;
	public static final int frontRightTalonPort = 2;
	public static final int backLeftTalonPort = 3;
	public static final int backRightTalonPort = 4;
	public static final int climbMotor = 5;
	
	// Controllers
  public static final int BACKUP_XBOX_PORT = 0;
  public static final int BACKUP_ARCADE_PORT = 1;
  public static final String XBOX_NAME = "Controller (XBOX 360 For Windows)";
  public static final String ARCADE_NAME = "Mayflash Arcade Stick";
	
	// Sensors
  
  // SmartDash Settings
  public static SendableChooser<Integer> autonomousMode = new SendableChooser<>();
  
  // Measurements (metric / meters, unless specified)
  public static double WHEEL_DIAMETER = .5; // TODO Diameter of PART_NUMBER
  
  // Misc
  
}
