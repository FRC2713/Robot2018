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
    initControls();
  
    upperJoint.setName(this.getName(), "Elbow");
    lowerJoint.setName(this.getName(), "Shoulder");
  }
  
  private void initControls() {
    Joystick arcade = OI.getArcadeController();
    GenericHID xbox = OI.getXBoxController();
    
    JoystickButton btn8 = new JoystickButton(arcade, 8);
    JoystickButton btn7 = new JoystickButton(arcade, 7);
    JoystickButton btn4 = new JoystickButton(arcade, 4);
    JoystickButton btn3 = new JoystickButton(arcade, 3);
    
    btn8.whileHeld(new ArmCommand(this, ArmPosition.SCALE_DELIVER_HIGH));
    btn7.whileHeld(new ArmCommand(this, ArmPosition.BOX_PROTECT));
    btn3.whileHeld(new ArmCommand(this, ArmPosition.BOX_PICKUP));
    btn4.whileHeld(new ArmCommand(this, ArmPosition.SWITCH_DELIVER));
  
    if (RobotMap.DEBUG) {
      double maxSpeed = .8;
      JoystickButton goUp = new JoystickButton(xbox, 4);
      goUp.whileHeld(new SimpleMotorCommand(this, upperJoint, -maxSpeed));
    
      JoystickButton goDown = new JoystickButton(xbox, 1);
      goDown.whileHeld(new SimpleMotorCommand(this, upperJoint, maxSpeed));
    
    
      JoystickButton goUpShoulder = new JoystickButton(xbox, 2);
      goUpShoulder.whileHeld(new SimpleMotorCommand(this, lowerJoint, maxSpeed));
    
      JoystickButton goDownShoulder = new JoystickButton(xbox, 3);
      goDownShoulder.whileHeld(new SimpleMotorCommand(this, lowerJoint, -maxSpeed));
    }
  
    new EncoderReporter(FeedbackDevice.Analog, upperJoint, lowerJoint).start();
  }
  
  @Override
  protected void initDefaultCommand() {
  
  }
  
  public enum ArmPosition {
    STARTING_CONFIG, TEST_DEFAULT,
    BOX_PICKUP, BOX_PROTECT,
    SWITCH_DELIVER, SCALE_DELIVER_HIGH, SCALE_DELIVER_MID, SCALE_DELIVER_LOW,
    CLIMB_ATTACH, CLIMB_DETACH
  }
}
