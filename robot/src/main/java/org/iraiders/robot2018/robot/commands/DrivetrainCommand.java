package org.iraiders.robot2018.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import lombok.RequiredArgsConstructor;
import org.iraiders.robot2018.robot.subsystems.DriveSubsystem;

@RequiredArgsConstructor
public class DrivetrainCommand extends Command {
  private final DriveSubsystem driveSubsystem;
  private final double leftSpeed;
  private final double rightSpeed;
  
  @Override
  protected void execute() {
    driveSubsystem.setDriveSpeed(leftSpeed, rightSpeed);
  }
  
  @Override
  protected void end() {
    driveSubsystem.setDriveSpeed(0);
  }
  
  @Override
  protected boolean isFinished() {
    return false;
  }
}
