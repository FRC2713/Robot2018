
package org.iraiders.robot2018.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import lombok.Getter;
import org.iraiders.robot2018.robot.commands.auto.PathfindingAuto;
import org.iraiders.robot2018.robot.commands.auto.VisionAuto;
import org.iraiders.robot2018.robot.subsystems.ArmSubsystem;
import org.iraiders.robot2018.robot.subsystems.DriveSubsystem;
import org.iraiders.robot2018.robot.subsystems.GrabberSubsystem;
import org.iraiders.robot2018.robot.subsystems.WinchSubsystem;

public class Robot extends IterativeRobot {
  @Getter private static Robot robotInstance;
  @Getter private static OI oi;
  public static Preferences prefs = Preferences.getInstance();
  
  @Getter private static DriveSubsystem driveSubsystem;
  @Getter private static ArmSubsystem armSubsystem;
  @Getter private static WinchSubsystem winchSubsystem;
  @Getter private static GrabberSubsystem grabberSubsystem;
  
  private PathfindingAuto pathfindingAuto;
	
  private long autoStart = 0;
 
  @Override
  public void robotInit() {
	  DriverStation.reportWarning("System coming alive, captain!", false);
	  robotInstance = this;
	  oi = new OI();
	
	  initDash();
	  //initCamera();
	  initSubsystems();
  }
	
	/**
   * Initialize all subsystems here
   */
  private void initSubsystems() {
    winchSubsystem = new WinchSubsystem();
    grabberSubsystem = new GrabberSubsystem();
    armSubsystem = new ArmSubsystem();
    driveSubsystem = new DriveSubsystem();
  }
  
  /**
   * Automatically find all cameras attached to the RoboRIO
   * and start streaming video
   */
  private void initCamera() {
    CameraServer cs = CameraServer.getInstance();
    cs.startAutomaticCapture();
  }
  
  /**
   * A place to get and set values from SmartDash
   */
  private void initDash() {
    // We need to set the name for every chooser or SmartDash won't pick it up
    RobotMap.startPosition.setName("Auto Position");
    RobotMap.startPosition.addDefault("Guess", PathfindingAuto.MatchStartPosition.GUESS);
    RobotMap.startPosition.addObject("Left", PathfindingAuto.MatchStartPosition.LEFT);
    RobotMap.startPosition.addObject("Middle", PathfindingAuto.MatchStartPosition.MIDDLE);
    RobotMap.startPosition.addObject("Right", PathfindingAuto.MatchStartPosition.RIGHT);
    SmartDashboard.putData(RobotMap.startPosition);
    
    RobotMap.whichAuto.setName("Which Auto");
    RobotMap.whichAuto.addDefault("Pathfinding", "PATHFINDING");
    RobotMap.whichAuto.addObject("Vision", "VISION");
    RobotMap.whichAuto.addObject("Emergency", "MVA");
    RobotMap.whichAuto.addObject("None", "NONE");
    SmartDashboard.putData(RobotMap.whichAuto);
  }
  
  @Override
  public void disabledInit() {
    Scheduler.getInstance().removeAll();
    
    OI.rumbleController(OI.getXBoxController(), 0, 0, GenericHID.RumbleType.kRightRumble);
    OI.rumbleController(OI.getXBoxController(), 0, 0, GenericHID.RumbleType.kLeftRumble);
  }
  
  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }
  
  @Override
  public void autonomousInit() {
    autoStart = System.currentTimeMillis();
    
    switch(RobotMap.whichAuto.getSelected()) {
      case "PATHFINDING":
        pathfindingAuto = new PathfindingAuto(driveSubsystem, armSubsystem, grabberSubsystem);
        pathfindingAuto.start();
        break;
        
      case "VISION":
        new VisionAuto(driveSubsystem, armSubsystem).start();
        break;
        
      case "MVA":
        RobotMap.USE_MINIMUM_VIABLE_AUTO = true;
        break;
        
      case "NONE":
        break;
    }
  }
  
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
    
    if (RobotMap.USE_MINIMUM_VIABLE_AUTO) {
      double speed = .6, timeout = 5;
      if ((System.currentTimeMillis() - autoStart) < (timeout * 1000)) driveSubsystem.setDriveSpeed(speed);
    }
  }

  @Override
  public void teleopInit() {
	  if (pathfindingAuto != null) pathfindingAuto.cancel();
   
	  driveSubsystem.startTeleop();
	  armSubsystem.startTeleop();
	  grabberSubsystem.startTeleop();
  }
 
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void testPeriodic() {
  
  }
  
  public static double normalize(double value, int min, int max) {
    return (value - min) / (max - min);
  }
}
