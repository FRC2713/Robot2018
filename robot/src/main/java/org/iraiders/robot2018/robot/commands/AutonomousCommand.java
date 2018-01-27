package org.iraiders.robot2018.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.modifiers.TankModifier;
import openrio.powerup.MatchData;
import org.iraiders.robot2018.robot.RobotMap;
import org.iraiders.robot2018.robot.Trajectories;
import org.iraiders.robot2018.robot.subsystems.DriveSubsystem;

public class AutonomousCommand extends CommandGroup {
  private DriveSubsystem driveSubsystem;
  
  public AutonomousCommand(DriveSubsystem driveSubsystem) {
    requires(driveSubsystem);
    this.driveSubsystem = driveSubsystem;
    doAuto((int) SmartDashboard.getNumber("robotPosition", 0), MatchData.getOwnedSide(MatchData.GameFeature.SWITCH_NEAR));
  }
  
  @Override
  protected void initialize() {
    RobotMap.imu.reset();
    if (!RobotMap.USE_MINIMUM_VIABLE_AUTO) {
      doAuto(3, MatchData.OwnedSide.RIGHT);
    }
  }
  
  public void doAuto(int robotLocation, MatchData.OwnedSide side) {
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
          TankModifier trajectory = Trajectories.getTankModifierOfPoints(Trajectories.rightStartToSwitchSameSide);
          addParallel(new MotionProfileFollowCommand(driveSubsystem.getFrontRightTalon(), trajectory.getRightTrajectory()));
          addParallel(new MotionProfileFollowCommand(driveSubsystem.getFrontLeftTalon(), trajectory.getLeftTrajectory()));
        } else {
          // Scale on other side
        }
        break;
    }
  }
}
