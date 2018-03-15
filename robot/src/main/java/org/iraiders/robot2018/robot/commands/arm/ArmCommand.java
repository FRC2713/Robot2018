package org.iraiders.robot2018.robot.commands.arm;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.iraiders.robot2018.robot.Robot;
import org.iraiders.robot2018.robot.commands.grabber.ControlGrabber;
import org.iraiders.robot2018.robot.subsystems.ArmSubsystem;
import org.iraiders.robot2018.robot.subsystems.GrabberSubsystem;

import static org.iraiders.robot2018.robot.subsystems.ArmSubsystem.ArmPosition;

public class ArmCommand extends CommandGroup {
  private ArmPosition position;
  private ArmSubsystem armSubsystem;
  
  public ArmCommand(ArmSubsystem armSubsystem, ArmPosition position){
    requires(armSubsystem);
    this.setName(armSubsystem.getName(), "ArmCommand");
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
        addParallel(new ControlGrabber(Robot.getGrabberSubsystem(), GrabberSubsystem.GrabberPosition.OPEN));
        shoulderPosition = 800;
        elbowPosition = 225;
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
    
    Command upperJoint = new UpperJoint(armSubsystem, armSubsystem.getUpperJoint(), elbowPosition);
    Command lowerJoint = new LowerJoint(armSubsystem, armSubsystem.getLowerJoint(), shoulderPosition);
    addParallel(upperJoint);
    addParallel(lowerJoint);
    this.addChild(upperJoint);
    this.addChild(lowerJoint);
  }
}
