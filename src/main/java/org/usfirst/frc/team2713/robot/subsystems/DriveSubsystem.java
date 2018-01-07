package org.usfirst.frc.team2713.robot.subsystems;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team2713.robot.RobotMap;
import org.usfirst.frc.team2713.robot.commands.OIDrive;

public class DriveSubsystem extends Subsystem {
  public RobotDrive roboDrive;
  private CANTalon frontLeft = new CANTalon(RobotMap.frontLeftTalonPort);
  private CANTalon frontRight = new CANTalon(RobotMap.frontRightTalonPort);
  private CANTalon backLeft = new CANTalon(RobotMap.backLeftTalonPort);
  private CANTalon backRight = new CANTalon(RobotMap.backRightTalonPort);
  
  public DriveSubsystem() {
  
  }
  
  @Override
  protected void initDefaultCommand() {
  
  }
  
  public void startTeleop() {
    roboDrive = new RobotDrive(frontLeft, backLeft, frontRight, backRight);
    new OIDrive(this).start();
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
  
  public double getCurvedSpeed(double speed) {
    // See https://www.wolframalpha.com/input/?i=(abs(x)%2F100)%5E2+*+100+where+x%3D45
    return Math.pow(Math.abs(speed) / 100, 2) * 100;
  }
}
