package org.iraiders.robot2018.robot.commands.grabber;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TwoLimitSwitchCommand extends Command {
  private final Subsystem subsystem;
  private final WPI_TalonSRX talon;
  private final DigitalInput limitSwitch;
  private final boolean reversed;
  
  private double maxSpeed = .8;
  
  @Override
  protected void initialize() {
    requires(subsystem);
    if (reversed) maxSpeed = -maxSpeed;
  }
  
  @Override
  protected void execute() {
    talon.set(maxSpeed);
  }
  
  @Override
  protected void end() {
    talon.set(0);
  }
  
  @Override
  protected boolean isFinished() {
    return limitSwitch.get();
  }
}
