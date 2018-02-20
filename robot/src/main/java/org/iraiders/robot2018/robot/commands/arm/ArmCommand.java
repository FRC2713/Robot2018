package org.iraiders.robot2018.robot.commands.arm;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.iraiders.robot2018.robot.subsystems.ArmSubsystem;

import static org.iraiders.robot2018.robot.subsystems.ArmSubsystem.ArmPosition;

public class ArmCommand extends CommandGroup {
  private ArmPosition position;
  private ArmSubsystem armSubsystem;
  
  public ArmCommand(ArmSubsystem armSubsystem, ArmPosition position){
    requires(armSubsystem);
    this.position = position;
    this.armSubsystem = armSubsystem;
    processWantedPosition();
  }
  
  private void processWantedPosition() {
    int shoulderPosition;
    int elbowPosition;
    switch (position){
      case BOX_PROTECT:
      case STARTING_CONFIG:
        shoulderPosition = 430;
        elbowPosition = 180;
        break;
        
      case BOX_PICKUP:
        shoulderPosition = 766;
        elbowPosition = 252;
        break;
        
      case SWITCH_DELIVER:
        shoulderPosition = 510;
        elbowPosition = 325;
        break;
        
      case SCALE_DELIVER_LOW:
      case SCALE_DELIVER_MID:
        shoulderPosition = 532;
        elbowPosition = 500;
        break;
        
      case SCALE_DELIVER_HIGH:
        shoulderPosition = 536;
        elbowPosition = 570;
        break;
        
      case TEST_DEFAULT:
        shoulderPosition = 425;
        elbowPosition = 387;
        break;
        
      default:
        DriverStation.reportWarning("Arm attempted to travel to an unknown / undefined point " + position, false);
        return;
    }
    addParallel(new UpperJoint(armSubsystem.getUpperJoint(), elbowPosition));
    addParallel(new LowerJoint(armSubsystem.getLowerJoint(), shoulderPosition));
  }
}
