package org.iraiders.robot2018.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Subsystem;
import lombok.Getter;
import org.iraiders.robot2018.robot.OI;
import org.iraiders.robot2018.robot.RobotMap;
import org.iraiders.robot2018.robot.commands.SimpleMotorCommand;

public class ClimbSubsystem extends Subsystem {
  @Getter private WPI_TalonSRX climbMotor = new WPI_TalonSRX(RobotMap.climbMotor);
  
  private final int fastSpeed = 1;
  private final double slowSpeed = .5;
  
  public void startTeleop() {
    Joystick arcade = OI.getArcadeController();
    JoystickButton upFast = new JoystickButton(arcade, 8); // Climb Up Fast
    JoystickButton downFast = new JoystickButton(arcade, 4); // Climb Down Fast
    JoystickButton upSlow = new JoystickButton(arcade, 7);
    JoystickButton downSlow = new JoystickButton(arcade, 3);
    
    upFast.whileActive(new SimpleMotorCommand(this, climbMotor, fastSpeed));
    downFast.whileActive(new SimpleMotorCommand(this, climbMotor, -fastSpeed));
    upSlow.whileActive(new SimpleMotorCommand(this, climbMotor, slowSpeed));
    downSlow.whileActive(new SimpleMotorCommand(this, climbMotor, -slowSpeed));
  }
  
  @Override
  protected void initDefaultCommand() {
  
  }
}
