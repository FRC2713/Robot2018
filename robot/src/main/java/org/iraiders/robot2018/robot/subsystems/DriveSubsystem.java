package org.iraiders.robot2018.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import lombok.Getter;
import org.iraiders.robot2018.robot.OI;
import org.iraiders.robot2018.robot.Robot;
import org.iraiders.robot2018.robot.RobotMap;
import org.iraiders.robot2018.robot.commands.OIDrive;
import org.iraiders.robot2018.robot.commands.auto.AutonomousCommand;
import org.iraiders.robot2018.robot.commands.feedback.EncoderReporter;
import org.iraiders.robot2018.robot.commands.feedback.RumbleListener;

public class DriveSubsystem extends Subsystem {
  public DifferentialDrive roboDrive;
  public static SendableChooser<OIDrive.OIDriveMode> driveMode = new SendableChooser<>();
  AutonomousCommand a;
  
  @Getter private WPI_TalonSRX frontLeftTalon = new WPI_TalonSRX(RobotMap.frontLeftTalonPort);
  @Getter private WPI_TalonSRX frontRightTalon = new WPI_TalonSRX(RobotMap.frontRightTalonPort);
  private WPI_TalonSRX backLeftTalon = new WPI_TalonSRX(RobotMap.backLeftTalonPort);
  private WPI_TalonSRX backRightTalon = new WPI_TalonSRX(RobotMap.backRightTalonPort);
  
  public DriveSubsystem() {
    setupTalons();
    initSmartDash();
  
    if (RobotMap.DEBUG) {
      // For debugging pathfinding in auto
      if (a == null) a = new AutonomousCommand(this, Robot.getArmSubsystem(), Robot.getGrabberSubsystem());
      a.cancel();
      JoystickButton testPathfinding = new JoystickButton(OI.getXBoxController(), 5); // LB
      testPathfinding.whenPressed(a);
    }
  }
  
  @Override
  protected void initDefaultCommand() {
  
  }
  
  private void initSmartDash() {
    driveMode.setName(this.getName(), "Drive Mode");
    driveMode.addDefault("Bradford", OIDrive.OIDriveMode.BRADFORD);
    driveMode.addObject("Tank", OIDrive.OIDriveMode.TANK);
    driveMode.addObject("Arcade", OIDrive.OIDriveMode.ARCADE);
    
    SmartDashboard.putData(driveMode);
  }
  
  public void startTeleop() {
    roboDrive = new DifferentialDrive(frontLeftTalon, frontRightTalon);
    new EncoderReporter(frontLeftTalon, frontRightTalon).start();
    new OIDrive(this).start();
    if (Robot.prefs.getBoolean("EnableSonicRumble", true)) new RumbleListener().start();
  }
  
  private void setupTalons() {
    // Add to SmartDash
    frontLeftTalon.setName(this.getName(), "Front Left #" + frontLeftTalon.getDeviceID());
    frontRightTalon.setName(this.getName(), "Front Right #" + frontRightTalon.getDeviceID());
    
    backLeftTalon.set(ControlMode.Follower, RobotMap.frontLeftTalonPort);
    backRightTalon.set(ControlMode.Follower, RobotMap.frontRightTalonPort);
    
    // Depending on motor alignment, use setInverted(true) here for each following talon
    frontLeftTalon.setSensorPhase(true);
  }
  
  public void setDriveSpeed(double value) {
    setDriveSpeed(value, value);
  }
  
  public void setDriveSpeed(double leftSpeed, double rightSpeed) {
    frontLeftTalon.set(leftSpeed);
    frontRightTalon.set(-rightSpeed);
  }
  
  /**
   * Returns the speed, corrected for the deadband. This is used usually when getting
   * speed inputs from a Joystick, as joysticks usually report values slightly
   * different then what is intended
   * @param speed The current desired speed (usually from the joystick)
   * @param deadbandTolerance The amount of deadband to remove from speed
   * @return The corrected speed
   * @deprecated {@link edu.wpi.first.wpilibj.drive.RobotDriveBase#applyDeadband(double, double)} has this implemented already
   */
  public double getDeadband(double speed, double deadbandTolerance) {
    return Math.max(0, // If deadband is greater than abs(speed), do nothing
      Math.abs(speed) - Math.max(deadbandTolerance, 0) // Subtract abs(speed) from larger of deadbandTolerance and 0
    ) * Math.signum(speed); // Restore original sign sign of speed
  }
  
  /**
   * {@inheritDoc}
   * @deprecated
   */
  public double getDeadband(double speed) {
    return getDeadband(speed, 0.1);
  }
  
  /**
   * Gets an adjusted speed, with the goal to have more precise movements at slower speeds
   * @param speed Input speed, between -1 and 1
   * @return Adjusted speed
   * @deprecated {@link edu.wpi.first.wpilibj.drive.RobotDriveBase} has this implemented in tank drive & arcade drive
   */
  public double getCurvedSpeed(double speed) {
    if (speed > 1 || speed < -1) throw new NumberFormatException("Number must be between -1 and 1");
    return Math.copySign(Math.pow(speed, 2), speed);
  }
}
