package org.iraiders.robot2018.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Subsystem;
import lombok.Getter;
import org.iraiders.robot2018.robot.OI;
import org.iraiders.robot2018.robot.RobotMap;
import org.iraiders.robot2018.robot.commands.arm.ArmCommand;


public class ArmSubsystem extends Subsystem {
  @Getter private WPI_TalonSRX lowerJoint = new WPI_TalonSRX(RobotMap.lowerJointTalonPort);
  @Getter private WPI_TalonSRX upperJoint = new WPI_TalonSRX(RobotMap.upperJointTalonPort);
  
  public void startTeleop() {
    Joystick arcade = OI.getArcadeController();
    JoystickButton condense = new JoystickButton(arcade, 8);
    JoystickButton reachBlock = new JoystickButton(arcade, 4);
    JoystickButton reachUp = new JoystickButton(arcade, 7);
    
    condense.whenPressed(new ArmCommand(this, ArmPosition.CONDENSE));
    reachBlock.whenPressed(new ArmCommand(this, ArmPosition.REACH_BLOCK));
    reachUp.whenPressed(new ArmCommand(this, ArmPosition.REACH_UP));
  }
  
  @Override
  protected void initDefaultCommand() {
  
  }
  
  public enum ArmPosition {
    CONDENSE, REACH_BLOCK, REACH_UP
  }
}
