package org.iraiders.robot2018.robot.commands.auto.deadreconing;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import openrio.powerup.MatchData;
import org.iraiders.robot2018.robot.RobotMap;
import org.iraiders.robot2018.robot.commands.DrivetrainCommand;
import org.iraiders.robot2018.robot.commands.arm.ArmCommand;
import org.iraiders.robot2018.robot.commands.auto.PathfindingAuto;
import org.iraiders.robot2018.robot.commands.grabber.ControlGrabber;
import org.iraiders.robot2018.robot.subsystems.ArmSubsystem;
import org.iraiders.robot2018.robot.subsystems.DriveSubsystem;
import org.iraiders.robot2018.robot.subsystems.GrabberSubsystem;

public class TimedSwitchAuto extends CommandGroup {
  private DriveSubsystem driveSubsystem;
  private ArmSubsystem armSubsystem;
  private GrabberSubsystem grabberSubsystem;
  
  public TimedSwitchAuto(DriveSubsystem driveSubsystem, ArmSubsystem armSubsystem, GrabberSubsystem grabberSubsystem) {
    this.driveSubsystem = driveSubsystem;
    this.armSubsystem = armSubsystem;
    this.grabberSubsystem = grabberSubsystem;
    
    doAuto(RobotMap.startPosition.getSelected(), MatchData.getOwnedSide(MatchData.GameFeature.SWITCH_NEAR));
  }
  
  private void doAuto(PathfindingAuto.MatchStartPosition robotLocation, MatchData.OwnedSide ownedScaleSide) {
    // Scale only for now
    if (robotLocation == PathfindingAuto.MatchStartPosition.GUESS) robotLocation = PathfindingAuto.MatchStartPosition.get(DriverStation.getInstance().getLocation());
    double speed = .33, timeout = 2.2;
  
    if ((robotLocation == PathfindingAuto.MatchStartPosition.LEFT && ownedScaleSide == MatchData.OwnedSide.LEFT)
      || (robotLocation == PathfindingAuto.MatchStartPosition.RIGHT && ownedScaleSide == MatchData.OwnedSide.RIGHT)) {
      addSequential(new ArmCommand(armSubsystem, ArmSubsystem.ArmPosition.SWITCH_DELIVER)); //TODO switch to the real preset
    }
    
    addSequential(new DrivetrainCommand(driveSubsystem, speed + 0.03, speed), timeout); // Add because of the weird drift
    if ((robotLocation == PathfindingAuto.MatchStartPosition.LEFT && ownedScaleSide == MatchData.OwnedSide.LEFT)
      || (robotLocation == PathfindingAuto.MatchStartPosition.RIGHT && ownedScaleSide == MatchData.OwnedSide.RIGHT)) {
      addSequential(new ControlGrabber(grabberSubsystem, GrabberSubsystem.GrabberPosition.OPEN));
    }
  }
}
