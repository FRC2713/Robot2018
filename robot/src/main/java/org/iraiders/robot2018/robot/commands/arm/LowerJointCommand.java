package org.iraiders.robot2018.robot.commands.arm;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import org.iraiders.robot2018.robot.subsystems.ArmSubsystem;

public class LowerJointCommand extends Command {
  private WPI_TalonSRX motor;
  private Potentiometer motorPot;
  private double setDeg;
  
  private ArmSubsystem armSubsystem;
  private PIDController pid;
  
  public LowerJointCommand(ArmSubsystem armSubsystem, double angle){
    requires(armSubsystem);
    this.armSubsystem = armSubsystem;
    this.motor = armSubsystem.getLowerJoint();
    this.motorPot = armSubsystem.getLowerPot();
    this.setDeg = angle;
  }
  
  @Override
  protected void initialize() {
    pid = armSubsystem.createPIDController(1, motorPot, motor); //TODO: Get an actually meaningful tolerance value
    pid.setSetpoint(setDeg);
    pid.enable();
  }
  
  @Override
  protected void end(){
    pid.disable();
  }
  
  @Override
  protected boolean isFinished() {
    return pid.onTarget();
  }
}
