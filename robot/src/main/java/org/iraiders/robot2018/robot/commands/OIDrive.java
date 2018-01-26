package org.iraiders.robot2018.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Command;
import org.iraiders.robot2018.robot.OI;
import org.iraiders.robot2018.robot.RobotMap;
import org.iraiders.robot2018.robot.subsystems.DriveSubsystem;

public class OIDrive extends Command {
  DriveSubsystem drive;
  private OIDriveMode mode = RobotMap.driveMode.getSelected();
  private XboxController xbox = OI.getXBoxController();
  
  public OIDrive(DriveSubsystem drive) {
    this.drive = drive;
    requires(drive);
  }
  
  @Override
  protected void initialize() {
  
  }
  
  @Override
  protected void execute() {
    // Invert directions, on an XBox controller the forward direction is negative
    switch (mode) {
      case BRADFORD:
        drive.roboDrive.arcadeDrive(-xbox.getY(Hand.kLeft), xbox.getX(Hand.kRight), true);
        break;
        
      case ARCADE:
        drive.roboDrive.arcadeDrive(-xbox.getY(Hand.kLeft), xbox.getX(Hand.kLeft), true);
        break;
        
      default:
      case TANK:
        drive.roboDrive.tankDrive(-xbox.getY(Hand.kLeft), -xbox.getY(Hand.kRight), true);
        break;
    }
  }
  
  @Override
  protected boolean isFinished() {
    return !DriverStation.getInstance().isOperatorControl();
  }
  
  public enum OIDriveMode {
    TANK, BRADFORD, ARCADE
  }
}
