package org.iraiders.robot2018.robot.commands.arm;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.iraiders.robot2018.robot.Robot;
import org.iraiders.robot2018.robot.subsystems.ArmSubsystem;

abstract class JointControlCommand extends PIDCommand {
  private final WPI_TalonSRX motor;
  private final int setDeg;
  private float maxSpeed = 1;
  
  JointControlCommand(ArmSubsystem armSubsystem, WPI_TalonSRX jointMotor, int desiredDegrees){
    super(.05, 0, 0);
    this.setName(armSubsystem.getName(), "JointControlCommand");
    this.motor = jointMotor;
    this.setDeg = desiredDegrees;
    
    this.setSetpoint(setDeg);
    this.getPIDController().setContinuous(false);
    this.getPIDController().setPercentTolerance(5);
  }
  
  @Override
  protected void initialize() {
    motor.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, 0);
  }
  
  @Override
  protected void execute() {
    super.execute();
    maxSpeed = Robot.prefs.getFloat("JointMaxSpeed", 1f);
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
    motor.set(-output * maxSpeed);
  }
  
  @Override
  protected boolean isFinished() {
    return getPIDController().onTarget();
  }
}

class UpperJoint extends JointControlCommand {
  
  UpperJoint(ArmSubsystem armSubsystem, WPI_TalonSRX jointMotor, int angle) {
    super(armSubsystem, jointMotor, angle);
    this.setInputRange(170, 810);
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
  private final double L1 = 5.0;
  private final double L2 = 19.0;
  private final double L3 = 17.5;
  private final double d = 15.0;
  
  LowerJoint(ArmSubsystem armSubsystem, WPI_TalonSRX jointMotor, int angle) {
    super(armSubsystem, jointMotor, angle);
    this.getPIDController().setP(this.getPIDController().getP() / 100);
    this.setInputRange(271, 880);
  }
  
  @Override
  public int degreesToPotUnits(int degrees) {
    return 0; // TODO tune this
  }
  
  @Override
  public int potUnitsToDegrees(int potUnits) {
    double theta2 = Math.PI*(potUnits - 279)/(2.81111 * 180);
    double c = Math.sqrt(Math.pow(L1,2) + Math.pow(d,2) + 2*L1*d*Math.cos(theta2));
    double theta3 = Math.acos((Math.pow(L2,2) - Math.pow(L3,2))/(-2*L3*c));
    double theta4 = Math.asin(L1*Math.sin(theta2)/c);
    int degrees = (int)(180 * (theta3 + theta4) / Math.PI);
    return degrees;
  }
}
