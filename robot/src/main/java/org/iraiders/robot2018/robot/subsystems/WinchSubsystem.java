package org.iraiders.robot2018.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.iraiders.robot2018.robot.OI;
import org.iraiders.robot2018.robot.RobotMap;
import org.iraiders.robot2018.robot.commands.SimpleMotorCommand;

public class WinchSubsystem extends Subsystem {
  private WPI_TalonSRX winchMotor = new WPI_TalonSRX(RobotMap.winchTalonPort);
  private Joystick arcade = OI.getArcadeController();
  
  private JoystickButton upNormal = new JoystickButton(arcade, 1);
  private JoystickButton downNormal = new JoystickButton(arcade, 5);
  
  public WinchSubsystem() {
    double normalSpeed = 1;
    upNormal.whileActive(new SimpleMotorCommand(this, winchMotor, normalSpeed));
    downNormal.whileActive(new SimpleMotorCommand(this, winchMotor, -normalSpeed));
  }
  
  @Override
  protected void initDefaultCommand() {
  
  }
}
