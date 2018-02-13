package org.iraiders.robot2018.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.iraiders.robot2018.robot.OI;
import org.iraiders.robot2018.robot.RobotMap;
import org.iraiders.robot2018.robot.commands.grabber.ControlGrabber;

public class GrabberSubsystem extends Subsystem {
  public final WPI_TalonSRX winchMotor = new WPI_TalonSRX(RobotMap.grabberTalonPort);
  public final DigitalInput startTrigger = new DigitalInput(3);
  public final DigitalInput endTrigger = new DigitalInput(4);
  private Joystick arcade = OI.getArcadeController();
  
  private JoystickButton open = new JoystickButton(arcade, 6);
  private JoystickButton close = new JoystickButton(arcade, 2);
  
  public void startTeleop() {
    open.whenActive(new ControlGrabber(this, GrabberPosition.OPEN));
    close.whileActive(new ControlGrabber(this, GrabberPosition.CLOSE));
  }
  
  @Override
  protected void initDefaultCommand() {
  
  }
  
  public enum GrabberPosition {
    OPEN, CLOSE
  }
}
