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
    int upperPosition;
    int lowerPosition;
    switch (position){
      case CONDENSE:
        upperPosition = 90;
        lowerPosition = 0;
        break;
      case REACH_BLOCK:
        upperPosition = 45;
        lowerPosition = 45;
        break;
      case REACH_UP:
        upperPosition = 0;
        lowerPosition = 0;
        break;
      default:
        return;
    }
    addParallel(new UpperJoint(armSubsystem.getUpperJoint(), upperPosition));
    addParallel(new LowerJoint(armSubsystem.getLowerJoint(), lowerPosition));
  }
}
