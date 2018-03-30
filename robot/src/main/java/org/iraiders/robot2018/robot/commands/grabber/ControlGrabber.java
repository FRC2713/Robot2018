package org.iraiders.robot2018.robot.commands.grabber;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.InstantCommand;
import lombok.RequiredArgsConstructor;
import org.iraiders.robot2018.robot.subsystems.GrabberSubsystem;

@RequiredArgsConstructor
public class ControlGrabber extends InstantCommand {
  private final GrabberSubsystem subsystem;
  private final GrabberSubsystem.GrabberPosition position;
  
  @Override
  protected void initialize() {
    if (position.equals(GrabberSubsystem.GrabberPosition.OPEN)) {
      subsystem.grabberSolenoid.set(DoubleSolenoid.Value.kForward);
    } else if (position.equals(GrabberSubsystem.GrabberPosition.CLOSE)) {
      subsystem.grabberSolenoid.set(DoubleSolenoid.Value.kReverse);
    }
  }
}
