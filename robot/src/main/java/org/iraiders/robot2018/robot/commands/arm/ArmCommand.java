package org.iraiders.robot2018.robot.commands.arm;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.iraiders.robot2018.robot.subsystems.ArmSubsystem;

public class ArmCommand extends CommandGroup {
  private LowerJointCommand lowerJoint;
  private UpperJointCommand upperJoint;
  private int position;
  private ArmSubsystem armSubsystem;
  
  public ArmCommand(ArmSubsystem armSubsystem, int position){
    requires(armSubsystem);
    this.position = position;
    this.armSubsystem = armSubsystem;
  }
  
  @Override
  protected void initialize() {
    int upperPosition;
    int lowerPosition;
    switch (position){
      case 1:
        upperPosition = 90;
        lowerPosition = 0;
        break;
      case 2:
        upperPosition = 45;
        lowerPosition = 45;
        break;
      case 3:
        upperPosition = 30;
        lowerPosition = 0;
        break;
      default:
        upperPosition = 0;
        lowerPosition = 0;
        break;
    }
    addParallel(new UpperJointCommand(armSubsystem, upperPosition));
    addParallel(new LowerJointCommand(armSubsystem, lowerPosition));
  }
}
