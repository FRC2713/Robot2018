package org.iraiders.robot2018.robot.commands;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Command;
import org.iraiders.robot2018.robot.OI;
import org.iraiders.robot2018.robot.subsystems.DriveSubsystem;

public class OIDrive extends Command {
  DriveSubsystem drive;
  private OIDriveMode mode = OIDriveMode.TANK; // TODO: make this a smart dashboard variable
  private XboxController xbox = OI.getXBoxController();
  
  public OIDrive(DriveSubsystem drive) {
    this.drive = drive;
    requires(drive);
  }
  
  @Override
  protected void execute() {
    switch (mode) {
      default:
      case TANK:
        drive.roboDrive.tankDrive(drive.getDeadband(xbox.getY(Hand.kLeft)), drive.getDeadband(xbox.getY(Hand.kRight)));
        break;
    }
  }
  
  @Override
  protected boolean isFinished() {
    return false;
  }
  
  public enum OIDriveMode {
    TANK, BRADFORD
  }
}
