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
      case STARTING_CONFIG:
        shoulderPosition = 427;
        elbowPosition = 155;
        break;
        
      case BOX_PICKUP:
        shoulderPosition = 785;
        elbowPosition = 70;
        break;
        
      case BOX_PROTECT:
        shoulderPosition = 423;
        elbowPosition = 110;
        break;
        
      case SWITCH_DELIVER:
        shoulderPosition = 590;
        elbowPosition = 45;
        break;
        
      case SCALE_DELIVER_LOW:
        shoulderPosition = 505;
        elbowPosition = -45;
        break;
        
      case SCALE_DELIVER_MID:
        shoulderPosition = 550;
        elbowPosition = -123;
        break;
        
      case SCALE_DELIVER_HIGH:
        shoulderPosition = 659;
        elbowPosition = -236;
        break;
        
      default:
        return;
    }
    addParallel(new UpperJoint(armSubsystem.getUpperJoint(), shoulderPosition));
    addParallel(new LowerJoint(armSubsystem.getLowerJoint(), elbowPosition));
  }
}
