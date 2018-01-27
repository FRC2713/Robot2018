package org.iraiders.robot2018.robot.commands.arm;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import org.iraiders.robot2018.robot.subsystems.ArmSubsystem;

public class UpperJointCommand extends PIDCommand {
  private WPI_TalonSRX motor;
  private Potentiometer motorPot;
  private double setDeg;
  
  public UpperJointCommand(ArmSubsystem armSubsystem, double angle){
    super(0.025, 0, 0); //TODO: Tune PID
    requires(armSubsystem);
    this.motor = armSubsystem.getUpperJoint();
    this.motorPot = armSubsystem.getUpperPot();
    this.setDeg = angle;
  }
  
  @Override
  protected void initialize(){
    setSetpoint(setDeg);
  }
  
  @Override
  protected double returnPIDInput() {
    return motorPot.get();
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
