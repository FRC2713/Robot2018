package org.iraiders.robot2018.robot.commands.arm;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.PIDCommand;

class JointControlCommand extends PIDCommand {
  private WPI_TalonSRX motor;
  private double setDeg;
  
  JointControlCommand(WPI_TalonSRX jointMotor, double angle){
    super(0.025, 0, 0); //TODO: Tune PID
    this.motor = jointMotor;
    this.setDeg = angle;
  }
  
  @Override
  protected void initialize() {
    motor.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, 0);
    setSetpoint(setDeg);
  }
  
  @Override
  protected double returnPIDInput() {
    return motor.getSelectedSensorPosition(0);
  }
  
  @Override
  protected void usePIDOutput(double output) {
    motor.set(output);
  }
  
  @Override
  protected boolean isFinished() {
    return getPIDController().onTarget();
  }
}
