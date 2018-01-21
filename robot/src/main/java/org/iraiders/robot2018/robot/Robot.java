
package org.iraiders.robotAutonomoust;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import lombok.Getter;
import org.iraiders.robot2018.robot.commands.AutonomousCommand;
import org.iraiders.robot2018.robot.subsystems.ClimbSubsystem;
import org.iraiders.robot2018.robot.subsystems.DriveSubsystem;

public class Robot extends IterativeRobot {
  @Getter private static Robot robotInstance;
  @Getter private static OI oi;
  
  private static DriveSubsystem driveSubsystem;
  private static ClimbSubsystem climbSubsystem;
 
	private AutonomousCommand autonomousCommand;
	
	private long autoStart = 0;
 
	@Override
	public void robotInit() {
	  DriverStation.reportWarning("System coming alive, captain!", false);
	  robotInstance = this;
		oi = new OI();
		
		initDash();
		initCamera();
		initSubsystems();
	}
	
	/**
   * Initialize all subsystems here, in order of importance
	 */
	private void initSubsystems() {
    driveSubsystem = new DriveSubsystem();
    climbSubsystem = new ClimbSubsystem();
    
    autonomousCommand = new AutonomousCommand(driveSubsystem);
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
    RobotMap.autonomousMode.addDefault("Default", 0);
  }
  
	@Override
	public void disabledInit() {
    Scheduler.getInstance().removeAll();
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void autonomousInit() {
    autoStart = System.currentTimeMillis();
		if (!RobotMap.USE_MINIMUM_VIABLE_AUTO) {
      if (autonomousCommand != null) autonomousCommand.start();
    }
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
    
    if (RobotMap.USE_MINIMUM_VIABLE_AUTO) {
      double speed = 1.0, timeout = 10;
      if ((System.currentTimeMillis() - autoStart) < (timeout * 1000)) driveSubsystem.setDriveSpeed(speed);
    }
	}

	@Override
	public void teleopInit() {
		if (autonomousCommand != null) autonomousCommand.cancel();
		
		driveSubsystem.startTeleop();
		climbSubsystem.startTeleop();
	}
 
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}
 
	@Override
	public void testPeriodic() {
	
	}
}
