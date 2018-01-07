package org.usfirst.frc.team2713.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2713.robot.subsystems.DriveSubsystem;

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
