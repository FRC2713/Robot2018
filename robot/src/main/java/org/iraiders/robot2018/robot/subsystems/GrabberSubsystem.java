package org.iraiders.robot2018.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.iraiders.robot2018.robot.OI;
import org.iraiders.robot2018.robot.RobotMap;
import org.iraiders.robot2018.robot.commands.grabber.ControlGrabber;

public class GrabberSubsystem extends Subsystem {
  public final DoubleSolenoid grabberSolenoid = new DoubleSolenoid(RobotMap.grabberOpenNodeId, RobotMap.grabberCloseNodeId);
  private Joystick arcade = OI.getArcadeController();
  
  private JoystickButton open = new JoystickButton(arcade, 6);
  private JoystickButton close = new JoystickButton(arcade, 2);
  
  public GrabberSubsystem() {
    open.whenPressed(new ControlGrabber(this, GrabberPosition.OPEN));
    close.whenPressed(new ControlGrabber(this, GrabberPosition.CLOSE));
    
    grabberSolenoid.setName(this.getName(), "Grabber Solenoid");
  }
  
  @Override
  protected void initDefaultCommand() {
  
  }
  
  public enum GrabberPosition {
    OPEN, CLOSE
  }
}
