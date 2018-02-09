package org.iraiders.robot2018.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.iraiders.robot2018.robot.OI;
import org.iraiders.robot2018.robot.RobotMap;
import org.iraiders.robot2018.robot.commands.grabber.TwoLimitSwitchCommand;

public class GrabberSubsystem extends Subsystem {
  private WPI_TalonSRX winchMotor = new WPI_TalonSRX(RobotMap.grabberTalonPort);
  protected final DigitalInput startTrigger = new DigitalInput(3);
  protected final DigitalInput endTrigger = new DigitalInput(4);
  private Joystick arcade = OI.getArcadeController();
  
  private JoystickButton open = new JoystickButton(arcade, 6);
  private JoystickButton close = new JoystickButton(arcade, 2);
  
  public void startTeleop() {
    open.whenActive(new TwoLimitSwitchCommand(this, winchMotor, startTrigger, false));
    close.whileActive(new TwoLimitSwitchCommand(this, winchMotor, endTrigger, true));
  }
  
  
  @Override
  protected void initDefaultCommand() {
  
  }
}
