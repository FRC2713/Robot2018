package org.iraiders.robot2018.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;
import org.iraiders.robot2018.robot.RobotMap;

import javax.measure.unit.SI;

public class MotionProfileFollowCommand extends Command {
  private final WPI_TalonSRX talon;
  private final EncoderFollower encoderFollower;
  
  public MotionProfileFollowCommand(WPI_TalonSRX talon, Trajectory trajectory) {
    this.talon = talon;
    this.encoderFollower = new EncoderFollower(trajectory);
  }
  
  @Override
  protected void initialize() {
    talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
    talon.setSelectedSensorPosition(0, 0, 0);
    encoderFollower.configureEncoder(talon.getSelectedSensorPosition(0), RobotMap.TICKS_PER_REVOLUTION, RobotMap.WHEEL_DIAMETER.to(SI.METER).getValue());
  }
  
  @Override
  protected void execute() {
    SmartDashboard.putNumber(talon.getName(), talon.getSelectedSensorPosition(0));
    double speed = encoderFollower.calculate(talon.getSelectedSensorPosition(0));
    double gyro_heading = RobotMap.imu.getAngleZ();
    double desired_heading = Pathfinder.r2d(encoderFollower.getHeading());
    double angleDifference = Pathfinder.boundHalfDegrees(desired_heading - gyro_heading);
    double turn = 0.8 * (-1.0/80.0) * angleDifference;
    System.out.println("testing DS println" + turn + " " + speed);
    talon.set(ControlMode.PercentOutput, speed + turn);
  }
  
  @Override
  protected boolean isFinished() {
    return encoderFollower.isFinished();
  }
}
