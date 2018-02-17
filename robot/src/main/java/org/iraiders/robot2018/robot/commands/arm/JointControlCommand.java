package org.iraiders.robot2018.robot.commands.arm;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.iraiders.robot2018.robot.Robot;

abstract class JointControlCommand extends PIDCommand {
  private final WPI_TalonSRX motor;
  private final int setDeg;
  private final float maxSpeed = Robot.prefs.getFloat("JointMaxSpeed", 0.1f);
  
  JointControlCommand(WPI_TalonSRX jointMotor, int desiredDegrees){
    super(0.025, 0, 0); //TODO: Tune PID
    this.motor = jointMotor;
    this.setDeg = this.degreesToPotUnits(desiredDegrees);
  }
  
  @Override
  protected void initialize() {
    motor.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, 0);
    this.setSetpoint(setDeg);
    this.getPIDController().setPercentTolerance(5); // TODO tune
  }
  
  @Override
  protected void execute() {
    SmartDashboard.putNumber(getClass().getSimpleName(), potUnitsToDegrees(motor.getSelectedSensorPosition(0)));
  }
  
  // These should be static, but due to poor language design this isn't possible
  // https://stackoverflow.com/questions/370962/why-cant-static-methods-be-abstract-in-java#comment3059554_370967
  public abstract int degreesToPotUnits(int degrees);
  public abstract int potUnitsToDegrees(int potUnits);
  
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

class UpperJoint extends JointControlCommand {
  
  UpperJoint(WPI_TalonSRX jointMotor, int angle) {
    super(jointMotor, angle);
  }
  
  @Override
  public int degreesToPotUnits(int degrees) {
    /*
    Based on https://www.desmos.com/calculator/oh2kdwdk6u, but the inverse
    degrees = (sensorUnits - 60)/(2+(2/3))
    sensorUnits = degrees * (2 + (2/3)) - 60
     */
    double sensorUnits = degrees * (2d + (2/3)) - 60;
    return (int) sensorUnits;
  }
  
  @Override
  public int potUnitsToDegrees(int potUnits) {
    double degrees = (potUnits - 60) * (2d + (2/3));
    return (int) degrees;
  }
}

class LowerJoint extends JointControlCommand {
  
  LowerJoint(WPI_TalonSRX jointMotor, int angle) {
    super(jointMotor, angle);
  }
  
  @Override
  public int degreesToPotUnits(int degrees) {
    return 0; // TODO tune this
  }
  
  @Override
  public int potUnitsToDegrees(int potUnits) {
    return 0; // TODO tune this
  }
  
}
