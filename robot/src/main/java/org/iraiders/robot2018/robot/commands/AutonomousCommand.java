package org.iraiders.robot2018.robot.commands;

import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.modifiers.TankModifier;
import openrio.powerup.MatchData;
import org.iraiders.robot2018.robot.Trajectories;
import org.iraiders.robot2018.robot.subsystems.DriveSubsystem;

import java.util.List;

public class AutonomousCommand extends Command {
  DriveSubsystem driveSubsystem;
  Notifier bb;
  public AutonomousCommand(DriveSubsystem driveSubsystem) {
    requires(driveSubsystem);
    this.driveSubsystem = driveSubsystem;
    bb = new Notifier(driveSubsystem.getFrontLeftTalon()::processMotionProfileBuffer);
    doAuto((int) SmartDashboard.getNumber("robotPosition", 0), MatchData.getOwnedSide(MatchData.GameFeature.SWITCH_NEAR));
  }
  
  @Override
  protected void initialize() {
    doAuto(3, MatchData.OwnedSide.RIGHT);
    bb.startPeriodic(20);
  }
  
  @Override
  protected void execute() {
  
  }
  
  public void doAuto(int robotLocation, MatchData.OwnedSide side) {
    bb.stop();
    if (robotLocation == 0) robotLocation = DriverStation.getInstance().getLocation();
    switch (robotLocation) {
      case 1:
        // Left Starting Point
        if (side == MatchData.OwnedSide.LEFT) {
          // Scale on same side as us
        } else {
          // Scale on other side
        }
        break;
    
      case 2:
        // Middle Starting Point
        if (side == MatchData.OwnedSide.LEFT) {
          // Scale on same side as us
        } else {
          // Scale on other side
        }
        break;
    
      case 3:
        // Right Starting Point
        if (side == MatchData.OwnedSide.RIGHT) {
          // Scale on same side as us
          //driveSubsystem.getFrontLeftTalon().selectProfileSlot(0,0);
          TankModifier trajectory = Trajectories.rightStartToSwitchSameSide();
          List<TrajectoryPoint> rightPoints = Trajectories.trajectoryToTalonPoints(trajectory.getRightTrajectory());
          List<TrajectoryPoint> leftPoints = Trajectories.trajectoryToTalonPoints(trajectory.getLeftTrajectory());
          
          Trajectories.loadPointsToTalon(driveSubsystem.getFrontLeftTalon(), leftPoints);
          Trajectories.loadPointsToTalon(driveSubsystem.getFrontRightTalon(), rightPoints);
  
          driveSubsystem.getFrontLeftTalon().set(ControlMode.MotionProfile, 1);
          driveSubsystem.getFrontRightTalon().set(ControlMode.MotionProfile, 1);
          
        } else {
          // Scale on other side
        }
        break;
    }
    bb.startPeriodic(20);
  }
  
  @Override
  protected boolean isFinished() {
    return false;
  }
}
