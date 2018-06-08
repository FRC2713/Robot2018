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
        shoulderPosition = 1490;
        elbowPosition = 177;
        //addSequential(new ArmCommand(armSubsystem, ArmPosition._MIDSTEP_BOX_PICKUP_TO_PROTECT), 4000);
        break;
        
      case _MIDSTEP_BOX_PICKUP_TO_PROTECT:
        if (ArmPosition.BOX_PICKUP.equals(armSubsystem.lastPosition)) {
          DriverStation.reportWarning("DOING THE THING MAYBE", false);
          shoulderPosition = 830;
          elbowPosition = 225 + 20; // TODO tune
        } else return;
        break;
        
      case BOX_PICKUP:
        addParallel(new ControlGrabber(Robot.getGrabberSubsystem(), GrabberSubsystem.GrabberPosition.OPEN));
        shoulderPosition = 1830;
        elbowPosition = 245;
        break;
        
      case SWITCH_DELIVER:
        //shoulderPosition = 610;
        //elbowPosition = 375;
        shoulderPosition = 1500;
        elbowPosition = 375;
        break;
        
        /*
      case SCALE_DELIVER_LOW:
      case SCALE_DELIVER_MID:
        shoulderPosition = 532;
        elbowPosition = 400; //TODO tune
        break;*/
        
      case SCALE_DELIVER_HIGH:
        shoulderPosition = 1467;
        elbowPosition = 638;
        break;
        
      case TEST_DEFAULT:
        shoulderPosition = 345;
        elbowPosition = 387;
        break;
        
      default:
        DriverStation.reportWarning("Arm attempted to travel to an unknown / undefined point " + position, false);
        return;
    }
    
    Command upperJoint = new UpperJoint(armSubsystem, armSubsystem.getUpperJoint(), elbowPosition);
    Command lowerJoint = new LowerJoint(armSubsystem, armSubsystem.getLowerJoint(), shoulderPosition);
    armSubsystem.lastPosition = position;
    addParallel(upperJoint);
    addParallel(lowerJoint);
    this.addChild(upperJoint);
    this.addChild(lowerJoint);
  }
}
