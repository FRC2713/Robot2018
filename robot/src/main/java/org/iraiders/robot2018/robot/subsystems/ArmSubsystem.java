package org.iraiders.robot2018.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Subsystem;
import lombok.Getter;
import org.iraiders.robot2018.robot.OI;
import org.iraiders.robot2018.robot.RobotMap;
import org.iraiders.robot2018.robot.commands.SimpleMotorCommand;
import org.iraiders.robot2018.robot.commands.arm.ArmCommand;
import org.iraiders.robot2018.robot.commands.feedback.EncoderReporter;


public class ArmSubsystem extends Subsystem {
  @Getter private final WPI_TalonSRX lowerJoint = new WPI_TalonSRX(RobotMap.lowerJointTalonPort);
  @Getter private final WPI_TalonSRX upperJoint = new WPI_TalonSRX(RobotMap.upperJointTalonPort);
  
  public ArmSubsystem() {
    upperJoint.setInverted(true); // Positive speed is up, negative is down
    initControls();
  }
  
  private void initControls() {
    Joystick arcade = OI.getArcadeController();
    GenericHID xbox = OI.getXBoxController();
    
    JoystickButton condense = new JoystickButton(arcade, 8);
    JoystickButton reachBlock = new JoystickButton(arcade, 4);
    JoystickButton reachUp = new JoystickButton(arcade, 7);
  
    condense.whenPressed(new ArmCommand(this, ArmPosition.CONDENSE));
    reachBlock.whenPressed(new ArmCommand(this, ArmPosition.REACH_BLOCK));
    reachUp.whenPressed(new ArmCommand(this, ArmPosition.REACH_UP));
  
    if (RobotMap.DEBUG) {
      double maxSpeed = .8;
      JoystickButton goUp = new JoystickButton(xbox, 4);
      goUp.whileHeld(new SimpleMotorCommand(this, upperJoint, maxSpeed));
    
      JoystickButton goDown = new JoystickButton(xbox, 1);
      goDown.whileHeld(new SimpleMotorCommand(this, upperJoint, -maxSpeed));
    
    
      JoystickButton goUpShoulder = new JoystickButton(xbox, 2);
      goUpShoulder.whileHeld(new SimpleMotorCommand(this, lowerJoint, maxSpeed));
    
      JoystickButton goDownShoulder = new JoystickButton(xbox, 3);
      goDownShoulder.whileHeld(new SimpleMotorCommand(this, lowerJoint, -maxSpeed));
    }
  }
  
  public void startTeleop() {
    new EncoderReporter(FeedbackDevice.Analog, upperJoint, lowerJoint).start();
  }
  
  @Override
  protected void initDefaultCommand() {
  
  }
  
  public enum ArmPosition {
    CONDENSE, REACH_BLOCK, REACH_UP
  }
}
