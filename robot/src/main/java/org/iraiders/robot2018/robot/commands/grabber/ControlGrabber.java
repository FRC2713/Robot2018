package org.iraiders.robot2018.robot.commands.grabber;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.iraiders.robot2018.robot.commands.SimpleMotorCommand;
import org.iraiders.robot2018.robot.subsystems.GrabberSubsystem;

public class ControlGrabber extends CommandGroup {
  public ControlGrabber(GrabberSubsystem subsystem, GrabberSubsystem.GrabberPosition position) {
    if (position.equals(GrabberSubsystem.GrabberPosition.OPEN)) {
      addParallel(new SimpleMotorCommand(subsystem, subsystem.grabberMotor, 1), 3);
    } else if (position.equals(GrabberSubsystem.GrabberPosition.CLOSE)) {
      addParallel(new SimpleMotorCommand(subsystem, subsystem.grabberMotor, -1), 3);
    }
  }
}
