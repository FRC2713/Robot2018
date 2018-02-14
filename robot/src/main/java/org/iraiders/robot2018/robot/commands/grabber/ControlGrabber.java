package org.iraiders.robot2018.robot.commands.grabber;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.iraiders.robot2018.robot.subsystems.GrabberSubsystem;

public class ControlGrabber extends CommandGroup {
  public ControlGrabber(GrabberSubsystem subsystem, GrabberSubsystem.GrabberPosition position) {
    if (position.equals(GrabberSubsystem.GrabberPosition.OPEN)) {
      addParallel(new TwoLimitSwitchCommand(subsystem, subsystem.winchMotor, subsystem.startTrigger, false));
    } else if (position.equals(GrabberSubsystem.GrabberPosition.CLOSE)) {
      addParallel(new TwoLimitSwitchCommand(subsystem, subsystem.winchMotor, subsystem.endTrigger, true));
    }
  }
}
