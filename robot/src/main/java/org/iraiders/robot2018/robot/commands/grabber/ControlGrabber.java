package org.iraiders.robot2018.robot.commands.grabber;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.iraiders.robot2018.robot.subsystems.GrabberSubsystem;

public class ControlGrabber extends CommandGroup {
  public ControlGrabber(GrabberSubsystem subsystem, GrabberSubsystem.GrabberPosition position) {
    if (subsystem.grabberSolenoid.isFwdSolenoidBlackListed() || subsystem.grabberSolenoid.isRevSolenoidBlackListed()) {
      // Saftey Rules
      DriverStation.reportError("One or more solenoids in " + subsystem.grabberSolenoid.getName() + " blacklisted, unable to comply with " + position.toString(), false);
      return;
    }
    if (position.equals(GrabberSubsystem.GrabberPosition.OPEN)) {
      subsystem.grabberSolenoid.set(DoubleSolenoid.Value.kForward);
    } else if (position.equals(GrabberSubsystem.GrabberPosition.CLOSE)) {
      subsystem.grabberSolenoid.set(DoubleSolenoid.Value.kReverse);
    }
  }
}
