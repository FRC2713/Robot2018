package org.iraiders.robot2018.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;
import org.iraiders.robot2018.robot.RobotMap;

import javax.measure.unit.SI;

public class MotionProfileFollowCommand extends Command {
  private final WPI_TalonSRX talon;
  private final Trajectory trajectory;
  private final EncoderFollower encoderFollower;
  private int testEncoderStartValue = 1000;
  
  public MotionProfileFollowCommand(WPI_TalonSRX talon, Trajectory trajectory) {
    this.talon = talon;
    this.trajectory = trajectory;
    this.encoderFollower = new EncoderFollower(trajectory);
  }
  
  @Override
  protected void initialize() {
    talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
    encoderFollower.configureEncoder(talon.getSelectedSensorPosition(0), RobotMap.TICKS_PER_REVOLUTION, RobotMap.WHEEL_DIAMETER.to(SI.METER).getValue());
  }
  
  @Override
  protected void execute() {
    double speed = encoderFollower.calculate(talon.getSelectedSensorPosition(0));
    double gyro_heading = RobotMap.imu.getAngleX(); // The RoboRIO is mounted vertically for testing, we will need to change X to something else when it is actually mounted
    double desired_heading = Pathfinder.r2d(encoderFollower.getHeading());
    double angleDifference = Pathfinder.boundHalfDegrees(desired_heading - gyro_heading);
    double turn = 0.8 * (-1.0/80.0) * angleDifference;
    talon.set(ControlMode.PercentOutput, speed + turn);
  }
  
  @Override
  protected boolean isFinished() {
    return encoderFollower.isFinished();
  }
}
