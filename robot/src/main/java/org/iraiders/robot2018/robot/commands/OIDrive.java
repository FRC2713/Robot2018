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
  private double lastLeftStickVal = 0;
  private double lastRightStickVal = 0;
  
  public OIDrive(DriveSubsystem drive) {
    this.drive = drive;
    requires(drive);
  }
  
  @Override
  protected void execute() {
    // This is dangerous, but otherwise the bot would be jerky due to (relatively) infrequently updated outputs
    drive.roboDrive.setMaxOutput(Robot.prefs.getFloat("OIMaxSpeed", 1));
    drive.roboDrive.setSafetyEnabled(false);
    
    double joystickChangeLimit = Robot.prefs.getDouble("JoystickChangeLimit", 0.25);
    
    double measuredLeft;
    double measuredRight;
    
    // Invert directions, on a joystick controller the forward direction is negative
    switch (drive.driveMode.getSelected()) {
      default:
      case XBOX:
        if (xbox.getRawButtonPressed(3)) { // X
          useTankInsteadOfBradford = !useTankInsteadOfBradford;
          lastRightStickVal = 0;
          lastRightStickVal = 0;
          OI.rumbleController(xbox, .5, 500);
        }
        
        if (useTankInsteadOfBradford) {
          measuredLeft = DriveSubsystem.slewLimit(-xbox.getY(Hand.kLeft), lastLeftStickVal, joystickChangeLimit);
          measuredRight = DriveSubsystem.slewLimit(-xbox.getY(Hand.kRight), lastRightStickVal, joystickChangeLimit);
          drive.roboDrive.tankDrive(measuredLeft, measuredRight, true);
        } else {
          measuredLeft = DriveSubsystem.slewLimit(-xbox.getY(Hand.kLeft), lastLeftStickVal, joystickChangeLimit);
          measuredRight = DriveSubsystem.slewLimit(xbox.getX(Hand.kRight), lastRightStickVal, joystickChangeLimit);
          drive.roboDrive.arcadeDrive(measuredLeft, measuredRight, true);
        }
        break;
        
      case DUALATTACK:
        if (rightAttack.getRawButtonPressed(11)) {
          useTankInsteadOfBradford = !useTankInsteadOfBradford;
          lastLeftStickVal = 0;
          lastRightStickVal = 0;
          OI.rumbleController(rightAttack, .5, 500);
        }
        
        if (useTankInsteadOfBradford) {
          measuredLeft = DriveSubsystem.slewLimit(-leftAttack.getY(), lastLeftStickVal, joystickChangeLimit);
          measuredRight = DriveSubsystem.slewLimit(-rightAttack.getY(), lastRightStickVal, joystickChangeLimit);
          drive.roboDrive.tankDrive(measuredLeft, measuredRight, true);
        } else {
          measuredLeft = DriveSubsystem.slewLimit(-leftAttack.getY(), lastLeftStickVal, joystickChangeLimit);
          measuredRight = DriveSubsystem.slewLimit(rightAttack.getX(), lastRightStickVal, joystickChangeLimit);
          drive.roboDrive.arcadeDrive(measuredLeft, measuredRight, true);
        }
        break;
    }
    lastLeftStickVal = measuredLeft;
    lastRightStickVal = measuredRight;
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
