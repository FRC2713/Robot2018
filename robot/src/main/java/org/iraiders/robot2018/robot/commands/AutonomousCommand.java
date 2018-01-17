package org.iraiders.robot2018.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import openrio.powerup.MatchData;
import org.iraiders.robot2018.robot.subsystems.DriveSubsystem;

public class AutonomousCommand extends CommandGroup {
  public AutonomousCommand(DriveSubsystem driveSubsystem) {
    requires(driveSubsystem);
    doAuto((int) SmartDashboard.getNumber("robotPosition", 0), MatchData.getOwnedSide(MatchData.GameFeature.SWITCH_NEAR));
  }
  
  private void doAuto(int robotLocation, MatchData.OwnedSide side) {
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
        } else {
          // Scale on other side
        }
        break;
    }
  }
}
