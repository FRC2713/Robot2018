package org.iraiders.robot2018.robot.commands.auto;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import openrio.powerup.MatchData;
import org.iraiders.robot2018.robot.RobotMap;
import org.iraiders.robot2018.robot.commands.SimpleMotorCommand;
import org.iraiders.robot2018.robot.commands.arm.ArmCommand;
import org.iraiders.robot2018.robot.commands.grabber.ControlGrabber;
import org.iraiders.robot2018.robot.subsystems.ArmSubsystem;
import org.iraiders.robot2018.robot.subsystems.DriveSubsystem;
import org.iraiders.robot2018.robot.subsystems.GrabberSubsystem;

public class TimebasedAuto extends CommandGroup {
  private DriveSubsystem driveSubsystem;
  private ArmSubsystem armSubsystem;
  private GrabberSubsystem grabberSubsystem;
  
  public TimebasedAuto(DriveSubsystem driveSubsystem, ArmSubsystem armSubsystem, GrabberSubsystem grabberSubsystem) {
    this.driveSubsystem = driveSubsystem;
    this.armSubsystem = armSubsystem;
    this.grabberSubsystem = grabberSubsystem;
    
    doAuto(RobotMap.startPosition.getSelected(), MatchData.getOwnedSide(MatchData.GameFeature.SCALE));
  }
  
  private void doAuto(PathfindingAuto.MatchStartPosition robotLocation, MatchData.OwnedSide ownedScaleSide) {
    // Scale only for now
    if (robotLocation == PathfindingAuto.MatchStartPosition.GUESS) robotLocation = PathfindingAuto.MatchStartPosition.get(DriverStation.getInstance().getLocation());
    double speed = .6, timeout = 5.2;
    addParallel(new SimpleMotorCommand(driveSubsystem.getFrontLeftTalon(), speed + 0.035), timeout); // Add because of the weird drift
    addParallel(new SimpleMotorCommand(driveSubsystem.getFrontRightTalon(), -speed), timeout);
    if ((robotLocation == PathfindingAuto.MatchStartPosition.LEFT && ownedScaleSide == MatchData.OwnedSide.LEFT)
      || (robotLocation == PathfindingAuto.MatchStartPosition.RIGHT && ownedScaleSide == MatchData.OwnedSide.RIGHT)) {
      addSequential(new ArmCommand(armSubsystem, ArmSubsystem.ArmPosition.SCALE_DELIVER_HIGH));
      addSequential(new ControlGrabber(grabberSubsystem, GrabberSubsystem.GrabberPosition.OPEN));
    }
  }
}
