package org.iraiders.robot2018.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.iraiders.robot2018.robot.OI;
import org.iraiders.robot2018.robot.RobotMap;
import org.iraiders.robot2018.robot.commands.SimpleMotorCommand;

public class WinchSubsystem extends Subsystem {
  private final WPI_TalonSRX winchMotor = new WPI_TalonSRX(RobotMap.winchTalonPort);
  
  JoystickButton upNormal = new JoystickButton(OI.getArcadeController(), 1);
  JoystickButton downNormal = new JoystickButton(OI.getArcadeController(), 5);
  
  public WinchSubsystem() {
    winchMotor.setName(this.getName(), "Winch Motor");
    
    double normalSpeed = 1;
  
    upNormal.whileHeld(new SimpleMotorCommand(this, winchMotor, normalSpeed));
    downNormal.whileHeld(new SimpleMotorCommand(this, winchMotor, -normalSpeed));
  }
  
  @Override
  protected void initDefaultCommand() {
  
  }
}
