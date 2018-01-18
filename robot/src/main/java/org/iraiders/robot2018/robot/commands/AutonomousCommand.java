package org.iraiders.robot2018.robot.commands;

import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.modifiers.TankModifier;
import openrio.powerup.MatchData;
import org.iraiders.robot2018.robot.Trajectories;
import org.iraiders.robot2018.robot.subsystems.DriveSubsystem;

public class AutonomousCommand extends Command {
  DriveSubsystem driveSubsystem;
  public AutonomousCommand(DriveSubsystem driveSubsystem) {
    requires(driveSubsystem);
    this.driveSubsystem = driveSubsystem;
    doAuto((int) SmartDashboard.getNumber("robotPosition", 0), MatchData.getOwnedSide(MatchData.GameFeature.SWITCH_NEAR));
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
          TankModifier trajectory = Trajectories.rightStartToSwitchSameSide();
          
          Trajectories.loadPointsToTalon(driveSubsystem.getFrontLeftTalon(),
            Trajectories.trajectoryToTalonPoints(trajectory.getLeftTrajectory()));
  
          Trajectories.loadPointsToTalon(driveSubsystem.getFrontRightTalon(),
            Trajectories.trajectoryToTalonPoints(trajectory.getRightTrajectory()));
          
          driveSubsystem.getFrontLeftTalon().set(ControlMode.MotionProfile, SetValueMotionProfile.Enable.value);
          driveSubsystem.getFrontRightTalon().set(ControlMode.MotionProfile, SetValueMotionProfile.Enable.value);
        } else {
          // Scale on other side
        }
        break;
    }
  }
  
  @Override
  protected boolean isFinished() {
    return false;
  }
}
