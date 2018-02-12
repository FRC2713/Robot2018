package org.iraiders.robot2018.robot.commands.auto;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;
import org.iraiders.robot2018.robot.Robot;
import org.iraiders.robot2018.robot.RobotMap;

import javax.measure.unit.SI;

public class MotionProfileFollowCommand extends Command {
  private final WPI_TalonSRX leftTalon;
  private final WPI_TalonSRX rightTalon;
  private final EncoderFollower left;
  private final EncoderFollower right;
  
  private final int maxFollowTime = Robot.prefs.getInt("motionProfileMaxFollowTime", 0);
  private double startTime;
  
  public MotionProfileFollowCommand(WPI_TalonSRX leftTalon, WPI_TalonSRX rightTalon, TankModifier trajectory) {
    this.leftTalon = leftTalon;
    this.rightTalon = rightTalon;
    this.left = new EncoderFollower(trajectory.getLeftTrajectory());
    this.right = new EncoderFollower(trajectory.getRightTrajectory());
  }
  
  @Override
  protected void initialize() {
    prepTalon(leftTalon, left);
    prepTalon(rightTalon, right);
    
    startTime = Timer.getFPGATimestamp();
  }
  
  @Override
  protected void execute() {
    setTargetTalonSpeed(leftTalon, left, false);
    setTargetTalonSpeed(rightTalon, right, true);
  }
  
  private void prepTalon(WPI_TalonSRX talon, EncoderFollower follower) {
    talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
    talon.setSelectedSensorPosition(0, 0, 0);
    //follower.configurePIDVA(); // TODO tune PID
    follower.configureEncoder(talon.getSelectedSensorPosition(0), RobotMap.TICKS_PER_REVOLUTION, RobotMap.WHEEL_DIAMETER.to(SI.METER).getValue());
  }
  
  private void setTargetTalonSpeed(WPI_TalonSRX talon, EncoderFollower follower, boolean subtract) {
    // Use subtract for only one side, usually right
    double gyro_heading = RobotMap.imu.getAngleZ();
    double speed = follower.calculate(talon.getSelectedSensorPosition(0));
    double desired_heading = Pathfinder.r2d(follower.getHeading());
    double angleDifference = Pathfinder.boundHalfDegrees(desired_heading - gyro_heading);
    double turn = 0.8 * (-1.0/80.0) * angleDifference;
    
    if (subtract) {
      talon.set(speed - turn);
    } else {
      talon.set(speed + turn);
    }
  }
  
  @Override
  protected void end() {
    leftTalon.set(0);
    rightTalon.set(0);
  }
  
  @Override
  protected boolean isFinished() {
    return (left.isFinished() && right.isFinished()) ||
      (maxFollowTime != 0 && (Timer.getFPGATimestamp() - startTime) >= maxFollowTime);
  }
}
