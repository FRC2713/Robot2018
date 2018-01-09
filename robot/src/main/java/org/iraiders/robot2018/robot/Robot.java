
package org.iraiders.robot2018.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import lombok.Getter;
import org.iraiders.robot2018.robot.commands.AutonomousCommand;
import org.iraiders.robot2018.robot.subsystems.DriveSubsystem;

public class Robot extends IterativeRobot {
  @Getter private static Robot robotInstance;
  @Getter private static OI oi;
  
  private static DriveSubsystem driveSubsystem;
 
	AutonomousCommand autonomousCommand = new AutonomousCommand();
 
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
		if (autonomousCommand != null) autonomousCommand.start();
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		if (autonomousCommand != null) autonomousCommand.cancel();
		
		driveSubsystem.startTeleop();
	}
 
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}
 
	@Override
	public void testPeriodic() {
	
	}
}
