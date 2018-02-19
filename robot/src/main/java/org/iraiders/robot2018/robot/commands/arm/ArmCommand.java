package org.iraiders.robot2018.robot.commands.arm;

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
        shoulderPosition = 450;
        elbowPosition = 1405;
        break;
        
      case BOX_PICKUP:
        shoulderPosition = 740;
        elbowPosition = 1380;
        break;
        
      case SWITCH_DELIVER:
        shoulderPosition = 553;
        elbowPosition = 1315;
        break;
        
      case SCALE_DELIVER_LOW:
      case SCALE_DELIVER_MID:
        shoulderPosition = 494;
        elbowPosition = 1162;
        break;
        
      case SCALE_DELIVER_HIGH:
        shoulderPosition = 545;
        elbowPosition = 1067;
        break;
        
      default:
        return;
    }
    addParallel(new UpperJoint(armSubsystem.getUpperJoint(), elbowPosition));
    addParallel(new LowerJoint(armSubsystem.getLowerJoint(), shoulderPosition));
  }
}
