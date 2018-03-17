package org.iraiders.robot2018.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.iraiders.robot2018.robot.OI;
import org.iraiders.robot2018.robot.RobotMap;
import org.iraiders.robot2018.robot.commands.grabber.ControlGrabber;

public class GrabberSubsystem extends Subsystem {
  public final WPI_TalonSRX grabberMotor = new WPI_TalonSRX(RobotMap.grabberTalonPort);
  private Joystick arcade = OI.getArcadeController();
  
  private JoystickButton open = new JoystickButton(arcade, 6);
  private JoystickButton close = new JoystickButton(arcade, 2);
  
  public void startTeleop() {
    open.whileHeld(new ControlGrabber(this, GrabberPosition.OPEN));
    close.whileHeld(new ControlGrabber(this, GrabberPosition.CLOSE));
    
    grabberMotor.setName(this.getName(), "Grabber Motor");
  }
  
  @Override
  protected void initDefaultCommand() {
  
  }
  
  public enum GrabberPosition {
    OPEN, CLOSE
  }
}
