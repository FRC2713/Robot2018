
package org.usfirst.frc.team2713.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import lombok.Getter;
import org.usfirst.frc.team2713.robot.subsystems.DriveSubsystem;

public class Robot extends IterativeRobot {
  @Getter private static Robot robotInstance;
  @Getter private static OI oi;
  
  private static DriveSubsystem driveSubsystem;
 
	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();
 
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
    // Insert SmartDash code here
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
		LiveWindow.run();
	}
}
