package org.iraiders.robot2018.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import openrio.powerup.MatchData;
import org.iraiders.robot2018.robot.RobotMap;

public class AutonomousCommand extends CommandGroup {
  public AutonomousCommand() {
    switch(RobotMap.autonomousMode.getSelected()) {
      default:
      case 0:
      
      
    }
  }
  
  private void autoCalculate() {
    MatchData.OwnedSide side = MatchData.getOwnedSide(MatchData.GameFeature.SWITCH_NEAR);
  }
}
