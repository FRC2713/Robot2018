package org.iraiders.robot2018.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.iraiders.robot2018.robot.subsystems.DriveSubsystem;

public class OIDrive extends Command {
  DriveSubsystem drive;
  
  public OIDrive(DriveSubsystem drive) {
    this.drive = drive;
    requires(drive);
  }
  
  @Override
  protected boolean isFinished() {
    return false;
  }
}
