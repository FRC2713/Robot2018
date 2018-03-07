package org.iraiders.robot2018.robot.commands;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Command;
import org.iraiders.robot2018.robot.OI;
import org.iraiders.robot2018.robot.Robot;
import org.iraiders.robot2018.robot.subsystems.DriveSubsystem;

public class OIDrive extends Command {
  private DriveSubsystem drive;
  private XboxController xbox = OI.getXBoxController();
  private Joystick leftAttack = OI.getLeftAttack();
  private Joystick rightAttack = OI.getRightAttack();
  private boolean useTankInsteadOfBradford = false;
  
  public OIDrive(DriveSubsystem drive) {
    this.drive = drive;
    requires(drive);
  }
  
  @Override
  protected void initialize() {
    // This is dangerous, but otherwise the bot would be jerky due to (relatively) infrequently updated outputs
    drive.roboDrive.setMaxOutput(Robot.prefs.getFloat("OIMaxSpeed", 1));
    
  }
  
  @Override
  protected void execute() {
    drive.roboDrive.setSafetyEnabled(false);
    
    // Invert directions, on a joystick controller the forward direction is negative
    switch (drive.driveMode.getSelected()) {
      default:
      case XBOX:
        if (xbox.getRawButtonPressed(3)) { // X
          useTankInsteadOfBradford = !useTankInsteadOfBradford;
          OI.rumbleController(xbox, .5, 500);
        }
        
        if (useTankInsteadOfBradford) {
          drive.roboDrive.tankDrive(-xbox.getY(Hand.kLeft), -xbox.getY(Hand.kRight), true);
        } else {
          drive.roboDrive.arcadeDrive(-xbox.getY(Hand.kLeft), xbox.getX(Hand.kRight), true);
        }
        break;
        
      case DUALATTACK:
        if (rightAttack.getRawButtonPressed(11)) {
          useTankInsteadOfBradford = !useTankInsteadOfBradford;
          OI.rumbleController(rightAttack, .5, 500);
        }
        
        if (useTankInsteadOfBradford) {
          drive.roboDrive.tankDrive(-leftAttack.getY(), -rightAttack.getY(), true);
        } else {
          drive.roboDrive.arcadeDrive(-leftAttack.getY(), rightAttack.getX(), true);
        }
        break;
    }
  }
  
  @Override
  protected void end() {
    drive.roboDrive.arcadeDrive(0, 0, false);
    drive.roboDrive.setSafetyEnabled(true);
  }
  
  @Override
  protected boolean isFinished() {
    return false;
  }
  
  public enum OIDriveMode {
    XBOX, DUALATTACK
  }
}
