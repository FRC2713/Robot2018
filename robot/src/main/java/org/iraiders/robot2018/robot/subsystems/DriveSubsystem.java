package org.iraiders.robot2018.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import org.iraiders.robot2018.robot.RobotMap;
import org.iraiders.robot2018.robot.commands.OIDrive;

public class DriveSubsystem extends Subsystem {
  public DifferentialDrive roboDrive;
  private WPI_TalonSRX frontLeft = new WPI_TalonSRX(RobotMap.frontLeftTalonPort);
  private WPI_TalonSRX frontRight = new WPI_TalonSRX(RobotMap.frontRightTalonPort);
  private WPI_TalonSRX backLeft = new WPI_TalonSRX(RobotMap.backLeftTalonPort);
  private WPI_TalonSRX backRight = new WPI_TalonSRX(RobotMap.backRightTalonPort);
  
  public DriveSubsystem() {
  
  }
  
  @Override
  protected void initDefaultCommand() {
  
  }
  
  public void startTeleop() {
    setTalonFollowers();
    roboDrive = new DifferentialDrive(frontLeft, frontRight);
    new OIDrive(this).start();
  }
  
  private void setTalonFollowers() {
    backLeft.set(ControlMode.Follower, frontLeft.getBaseID());
    backRight.set(ControlMode.Follower, frontRight.getBaseID());
    
    // Depending on motor alignment, use setInverted(true) here for each following talon
  }
  
  /**
   * Returns the speed, corrected for the deadband. This is used usually when getting
   * speed inputs from a Joystick, as joysticks usually report values slightly
   * different then what is intended
   * @param speed The current desired speed (usually from the joystick)
   * @param deadbandTolerance The amount of deadband to remove from speed
   * @return The corrected speed
   */
  public double getDeadband(double speed, double deadbandTolerance) {
    return Math.max(0, // If deadband is greater than abs(speed), do nothing
      Math.abs(speed) - Math.max(deadbandTolerance, 0) // Subtract abs(speed) from larger of deadbandTolerance and 0
    ) * Math.signum(speed); // Restore original sign sign of speed
  }
  
  /**
   * Gets an adjusted speed, with the goal to have more precise movements at slower speeds
   * @param speed Input speed, between -1 and 1
   * @return Adjusted speed
   */
  public double getCurvedSpeed(double speed) {
    if (speed > 1 || speed < -1) throw new NumberFormatException("Number must be between -1 and 1");
    return Math.copySign(Math.pow(speed, 2), speed);
  }
}
