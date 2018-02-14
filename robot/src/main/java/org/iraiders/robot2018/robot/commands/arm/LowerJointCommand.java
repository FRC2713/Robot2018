package org.iraiders.robot2018.robot.commands.arm;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.PIDCommand;
import org.iraiders.robot2018.robot.Robot;

class JointControlCommand extends PIDCommand {
  private final WPI_TalonSRX motor;
  private final double setDeg;
  private final float maxSpeed = Robot.prefs.getFloat("JointMaxSpeed", 0.1f);
  
  JointControlCommand(WPI_TalonSRX jointMotor, double angle){
    super(0.025, 0, 0); //TODO: Tune PID
    this.motor = jointMotor;
    this.setDeg = angle;
  }
  
  @Override
  protected void initialize() {
    motor.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, 0);
    this.setSetpoint(setDeg);
    this.getPIDController().setPercentTolerance(5); // TODO tune
  }
  
  @Override
  protected double returnPIDInput() {
    return motor.getSelectedSensorPosition(0);
  }
  
  @Override
  protected void usePIDOutput(double output) {
    motor.set(output * maxSpeed);
  }
  
  @Override
  protected boolean isFinished() {
    return getPIDController().onTarget();
  }
}
